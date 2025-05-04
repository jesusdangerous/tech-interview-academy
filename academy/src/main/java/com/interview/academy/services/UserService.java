package com.interview.academy.services;

import com.interview.academy.domain.entities.User;

import java.util.UUID;

public interface UserService {

    User getUserById(UUID id);
}
