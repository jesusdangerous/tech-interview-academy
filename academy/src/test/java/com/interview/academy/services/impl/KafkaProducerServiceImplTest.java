package com.interview.academy.services.impl;

import com.interview.academy.domain.dtos.PostEventDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.UUID;

import static org.mockito.Mockito.*;

class KafkaProducerServiceImplTest {

    @Mock
    private KafkaTemplate<String, PostEventDto> kafkaTemplate;

    @InjectMocks
    private KafkaProducerServiceImpl kafkaProducerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendNewPostEventShouldSendMessageToKafka() {
        PostEventDto event = PostEventDto.builder()
                .postId(UUID.randomUUID())
                .title("Test Post")
                .build();

        kafkaProducerService.sendNewPostEvent(event);

        verify(kafkaTemplate, times(1)).send("new-post-topic", event);
    }
}

