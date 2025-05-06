package com.interview.academy.controllers;

import com.interview.academy.domain.dtos.AuthResponse;
import com.interview.academy.domain.dtos.LoginRequest;
import com.interview.academy.domain.dtos.RegisterRequest;
import com.interview.academy.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        log.info("Attempting to authenticate user with email: {}", loginRequest.getEmail());
        try {
            UserDetails userDetails = authenticationService.authenticate(
                    loginRequest.getEmail(),
                    loginRequest.getPassword()
            );
            String token = authenticationService.generateToken(userDetails);
            AuthResponse authResponse = AuthResponse.builder()
                    .token(token)
                    .expiresIn(86400)
                    .build();

            log.info("User {} successfully authenticated", loginRequest.getEmail());
            return ResponseEntity.ok(authResponse);
        } catch (Exception e) {
            log.error("Authentication failed for user with email: {}", loginRequest.getEmail(), e);
            return ResponseEntity.status(401).build();
        }
    }


    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest) {
        log.info("Attempting to register user with email: {}", registerRequest.getEmail());
        try {
            UserDetails userDetails = authenticationService.register(
                    registerRequest.getName(),
                    registerRequest.getEmail(),
                    registerRequest.getPassword()
            );

            String token = authenticationService.generateToken(userDetails);
            AuthResponse authResponse = AuthResponse.builder()
                    .token(token)
                    .expiresIn(86400)
                    .build();

            log.info("User {} successfully registered", registerRequest.getEmail());
            return ResponseEntity.ok(authResponse);
        } catch (Exception e) {
            log.error("Registration failed for user with email: {}", registerRequest.getEmail(), e);
            return ResponseEntity.status(400).build();
        }
    }
}
