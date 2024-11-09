package com.softz.identity.controller;

import java.text.ParseException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.softz.identity.dto.AccessTokenDto;
import com.softz.identity.dto.ApiResponse;
import com.softz.identity.dto.request.AuthenticationRequest;
import com.softz.identity.dto.request.InvalidateTokenRequest;
import com.softz.identity.service.coordinator.AuthenticationCoordinatorService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationCoordinatorService authenticationService;

    @PostMapping("/auth/token")
    public ApiResponse<AccessTokenDto> authenticate(@RequestBody AuthenticationRequest request) {
        AccessTokenDto accessToken = authenticationService.authenticate(request);

        return ApiResponse.<AccessTokenDto>builder().result(accessToken).build();
    }

    @PostMapping("/auth/logout")
    public ApiResponse<Void> invalidateToken(@RequestBody InvalidateTokenRequest request) throws ParseException {
        authenticationService.invalidate(request);
        return ApiResponse.<Void>builder().build();
    }
}
