package com.interview.academy.events;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

public record PostCreatedEvent(UUID postId, String title) { }