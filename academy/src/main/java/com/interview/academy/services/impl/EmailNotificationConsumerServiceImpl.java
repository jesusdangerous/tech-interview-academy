package com.interview.academy.services.impl;

import com.interview.academy.domain.dtos.PostEventDto;
import com.interview.academy.domain.entities.Mail;
import com.interview.academy.domain.entities.User;
import com.interview.academy.services.EmailNotificationConsumerService;
import com.interview.academy.services.EmailService;
import com.interview.academy.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailNotificationConsumerServiceImpl implements EmailNotificationConsumerService {

    private final UserService userService;
    private final EmailService emailService;

    @KafkaListener(topics = "new-post-topic", groupId = "email-group")
    public void handleNewPostEvent(PostEventDto event) {
        List<User> users = userService.getAllUsers();
        String[] emails = users.stream()
                .map(User::getEmail)
                .filter(e -> e != null && !e.isBlank())
                .toArray(String[]::new);

        if (emails.length == 0) return;

        Mail mail = Mail.builder()
                .to(emails)
                .subject("New Post: " + event.getTitle())
                .body("A new post '" + event.getTitle() + "' has been published. Check it out!")
                .build();

        emailService.sendVerificationEmail(mail);
    }
}
