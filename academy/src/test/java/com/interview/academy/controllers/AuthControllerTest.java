package com.interview.academy.controllers;

import com.interview.academy.domain.dtos.AuthResponse;
import com.interview.academy.domain.dtos.LoginRequest;
import com.interview.academy.domain.dtos.RegisterRequest;
import com.interview.academy.services.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.springframework.security.core.userdetails.User;
import java.util.Collections;


public class AuthControllerTest {

    private AuthenticationService authenticationService;
    private AuthController authController;

    @BeforeEach
    void setUp() {
        authenticationService = mock(AuthenticationService.class);
        authController = new AuthController(authenticationService);
    }

    @Test
    void testLoginSuccess() {
        LoginRequest loginRequest = new LoginRequest("test@example.com", "password");
        UserDetails mockUser = new User("test@example.com", "password", Collections.emptyList());

        when(authenticationService.authenticate("test@example.com", "password"))
                .thenReturn(mockUser);
        when(authenticationService.generateToken(mockUser))
                .thenReturn("mocked-token");

        ResponseEntity<AuthResponse> response = authController.login(loginRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("mocked-token", response.getBody().getToken());
        assertEquals(86400, response.getBody().getExpiresIn());
    }

    @Test
    void testLoginFailure() {
        LoginRequest loginRequest = new LoginRequest("wrong@example.com", "wrongpass");

        when(authenticationService.authenticate(anyString(), anyString()))
                .thenThrow(new RuntimeException("Invalid credentials"));

        ResponseEntity<AuthResponse> response = authController.login(loginRequest);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void testRegisterSuccess() {
        RegisterRequest registerRequest = new RegisterRequest("test@example.com", "password", "Test User");
        UserDetails mockUser = new User("test@example.com", "password", Collections.emptyList());

        when(authenticationService.register("Test User", "test@example.com", "password"))
                .thenReturn(mockUser);
        when(authenticationService.generateToken(mockUser))
                .thenReturn("mocked-token");

        ResponseEntity<AuthResponse> response = authController.register(registerRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("mocked-token", response.getBody().getToken());
        assertEquals(86400, response.getBody().getExpiresIn());
    }

    @Test
    void testRegisterFailure() {
        RegisterRequest registerRequest = new RegisterRequest("bad@example.com", "123", "Bad User");

        when(authenticationService.register(anyString(), anyString(), anyString()))
                .thenThrow(new RuntimeException("Registration error"));

        ResponseEntity<AuthResponse> response = authController.register(registerRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}