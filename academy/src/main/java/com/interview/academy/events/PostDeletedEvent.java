package com.interview.academy.events;

import java.util.UUID;

public record PostDeletedEvent(UUID postId, String title) {
}
