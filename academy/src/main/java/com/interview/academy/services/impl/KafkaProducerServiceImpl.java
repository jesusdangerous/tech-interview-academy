package com.interview.academy.services.impl;

import com.interview.academy.domain.dtos.PostEventDto;
import com.interview.academy.services.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerServiceImpl implements KafkaProducerService {

    private final KafkaTemplate<String, PostEventDto> kafkaTemplate;

    @Override
    public void sendNewPostEvent(PostEventDto event) {
        kafkaTemplate.send("new-post-topic", event);
    }
}
