package com.interview.academy.services.impl;

import com.interview.academy.domain.dtos.PostEventDto;
import com.interview.academy.domain.entities.User;
import com.interview.academy.services.EmailService;
import com.interview.academy.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.mockito.Mockito.*;

public class EmailNotificationConsumerServiceImplTest {

    @Mock
    private UserService userService;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private EmailNotificationConsumerServiceImpl consumerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testHandleNewPostEventShouldSendEmailToUsers() {
        PostEventDto event = new PostEventDto();
        event.setTitle("New Java Post");

        User user1 = new User();
        user1.setEmail("user1@example.com");

        User user2 = new User();
        user2.setEmail("user2@example.com");

        when(userService.getAllUsers()).thenReturn(List.of(user1, user2));

        consumerService.handleNewPostEvent(event);

        verify(emailService, times(1)).sendEmail(argThat(mail ->
                mail.getSubject().equals("New Post: New Java Post") &&
                        mail.getBody().contains("New Java Post") &&
                        mail.getTo().length == 2 &&
                        List.of(mail.getTo()).containsAll(List.of("user1@example.com", "user2@example.com"))
        ));
    }

    @Test
    void testHandleNewPostEventShouldNotSendEmailIfNoUsers() {
        PostEventDto event = new PostEventDto();
        event.setTitle("Test Post");

        when(userService.getAllUsers()).thenReturn(List.of());

        consumerService.handleNewPostEvent(event);

        verify(emailService, never()).sendEmail(any());
    }
}
