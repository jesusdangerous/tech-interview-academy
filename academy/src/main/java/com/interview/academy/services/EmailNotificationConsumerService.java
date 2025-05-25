package com.interview.academy.services;

import com.interview.academy.domain.dtos.PostEventDto;

public interface EmailNotificationConsumerService {
    void handleNewPostEvent(PostEventDto event);
}
