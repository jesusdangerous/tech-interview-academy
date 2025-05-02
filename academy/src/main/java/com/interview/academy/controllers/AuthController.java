package com.interview.academy.controllers;

import com.interview.academy.domain.dtos.AuthResponse;
import com.interview.academy.domain.dtos.LoginRequest;
import com.interview.academy.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/login")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        UserDetails userDetails = authenticationService.authenticate(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        );

        String token = authenticationService.generateToken(userDetails);
        AuthResponse authResponse = AuthResponse.builder()
                .token(token)
                .expiresIn(86400)
                .build();

        return ResponseEntity.ok(authResponse);
    }
}
