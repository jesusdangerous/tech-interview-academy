package com.interview.academy.events;


import java.util.UUID;

public record PostUpdatedEvent(UUID postId, String title) {
}
