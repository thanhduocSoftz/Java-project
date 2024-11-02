package com.softz.identity.util;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.softz.identity.entity.User;
import com.softz.identity.exception.AppException;
import com.softz.identity.exception.ErrorCode;

import com.softz.identity.repository.InvalidatedTokenRepository;
import org.checkerframework.checker.signedness.qual.Signed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Component
public class JwtService {
    @Value("${jwt.key}")
    private String tokenKey;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.ttl}")
    private long ttl;

    @Autowired
    InvalidatedTokenRepository invalidatedTokenRepository;

    public TokenInfor extractToken(String token){
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);

        JWSVerifier jwsVerifier = new MACVerifier(tokenKey.getBytes());
        
        if (signedJWT.getJWTClaimsSet()
                .getExpirationTime().before(new Date())) {
            return null;
        }

        if(!signedJWT.verify(jwsVerifier)){
            return null;
        }
        
        return new TokenInfor(signedJWT.getJWTClaimsSet().getJWTID(), 
        signedJWT.getJWTClaimsSet().getExpirationTime().toInstant());
        } catch (ParseException | JOSEException e) {
            return null;
        }
    } 

    public record TokenInfor(String jti, Instant expiryTime) {
    }

    public SignedJWT introspect(String token) {
        try {
            // Parse token
            SignedJWT signedJWT = SignedJWT.parse(token);

            // Init JWS Verifier
            JWSVerifier jwsVerifier = new MACVerifier(tokenKey.getBytes());

            if (signedJWT.getJWTClaimsSet()
                    .getExpirationTime().before(new Date())) {
                throw new AppException(ErrorCode.UNAUTHENTICATED);
            }

            // Verify if jwt has been invalidated
            String jti = signedJWT.getJWTClaimsSet().getJWTID();
            if (invalidatedTokenRepository.existsById(jti)) {
                throw new AppException(ErrorCode.UNAUTHENTICATED);
            }

            if (!signedJWT.verify(jwsVerifier)) {
                throw new AppException(ErrorCode.UNAUTHENTICATED);
            }

            return signedJWT;
        } catch (ParseException | JOSEException e) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
    }

    public boolean verifyToken(String token) {
        try {
            // Parse token
            SignedJWT signedJWT = SignedJWT.parse(token);

            // Init JWS Verifier
            JWSVerifier jwsVerifier = new MACVerifier(tokenKey.getBytes());

            if (signedJWT.getJWTClaimsSet()
                    .getExpirationTime().before(new Date())) {
                throw new AppException(ErrorCode.UNAUTHENTICATED);
            }

            return signedJWT.verify(jwsVerifier);
        } catch (ParseException | JOSEException e) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
    }

    public String generateToken(User user) {
        // Build header
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        // Build payload
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .issuer(issuer)
                .subject(user.getUsername())
                .claim("userId", user.getId())
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now()
                                .plus(ttl, ChronoUnit.SECONDS)
                                .toEpochMilli()))
                .claim("scope", buildScope(user))
                .jwtID(UUID.randomUUID().toString())
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        // Build token with header and payload
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        // Sign token
        try {
            JWSSigner jwsSigner =
                    new MACSigner(tokenKey.getBytes());

            jwsObject.sign(jwsSigner);

            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
    }

    private String buildScope(User user) {
        if (CollectionUtils.isEmpty(user.getRoles()))
            return "";

        StringJoiner stringJoiner = new StringJoiner(" ");

        user.getRoles().forEach(role -> {
            stringJoiner.add(String.format("ROLE_%s", role.getName()));
            if (!CollectionUtils.isEmpty(role.getPermissions())) {
                role.getPermissions()
                        .forEach(permission ->
                                stringJoiner.add(permission.getName()));
            }
        });

        return stringJoiner.toString();
    }
}
