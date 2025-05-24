package com.interview.academy.services;

import com.interview.academy.domain.dtos.PostEventDto;

public interface KafkaProducerService {
    void sendNewPostEvent(PostEventDto event);
}
