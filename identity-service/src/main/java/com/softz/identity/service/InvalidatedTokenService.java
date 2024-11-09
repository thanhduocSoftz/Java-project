package com.softz.identity.service;

import org.springframework.stereotype.Service;

import com.softz.identity.entity.InvalidatedToken;
import com.softz.identity.repository.InvalidatedTokenRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InvalidatedTokenService {
    InvalidatedTokenRepository invalidatedRepository;

    public InvalidatedToken create(InvalidatedToken invalidatedToken) {
        return invalidatedRepository.save(invalidatedToken);
    }
}
