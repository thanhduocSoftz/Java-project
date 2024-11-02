package com.softz.identity.service.coordinator;

import com.nimbusds.jwt.SignedJWT;
import com.softz.identity.dto.AccessTokenDto;
import com.softz.identity.dto.request.AuthenticationRequest;
import com.softz.identity.dto.request.InvalidateTokenRequest;
import com.softz.identity.entity.InvalidatedToken;
import com.softz.identity.entity.User;
import com.softz.identity.exception.AppException;
import com.softz.identity.exception.ErrorCode;
import com.softz.identity.service.InvalidatedTokenService;
import com.softz.identity.service.UserService;
import com.softz.identity.util.JwtService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationCoordinatorService {
    UserService userService;
    JwtService jwtService;
    InvalidatedTokenService invalidatedService;

    public AccessTokenDto authenticate(AuthenticationRequest request) {
        BCryptPasswordEncoder bCryptPasswordEncoder =
                new BCryptPasswordEncoder(10);

        User user;
        try {
            user = userService.getUserByUsername(request.getUsername());
        } catch (AppException e) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        // Verify password
        if (!bCryptPasswordEncoder.matches(request.getPassword(),
                user.getPassword())){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        // Issue token
        String token = jwtService.generateToken(user);

        return AccessTokenDto.builder()
                .token(token)
                .build();
    }

    public void invalidate(InvalidateTokenRequest request) throws ParseException {
        JwtService.TokenInfor tokenInfor = jwtService.extractToken(request.getToken());

        if(Objects.nonNull(tokenInfor)){
            //Build InvalidatedToken infor
            InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(tokenInfor.jti())
                .expiryTime(tokenInfor.expiryTime())
                .build();

        //invoke InvalidateTokenService to persits
        invalidatedService.create(invalidatedToken);
        }
    }
}
