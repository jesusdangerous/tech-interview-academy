package com.interview.academy.services;

import com.interview.academy.domain.dtos.RegisterRequest;
import com.interview.academy.domain.entities.User;

import java.util.UUID;

public interface UserService {

    User getUserById(UUID id);
    User registerUser(RegisterRequest request);
}
