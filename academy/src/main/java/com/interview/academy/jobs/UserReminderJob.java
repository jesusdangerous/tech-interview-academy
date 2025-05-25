package com.interview.academy.jobs;

import com.interview.academy.domain.entities.Mail;
import com.interview.academy.domain.entities.User;
import com.interview.academy.services.EmailService;
import com.interview.academy.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserReminderJob implements Job {

    private final UserService userService;
    private final EmailService emailService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("Launching reminders for users: {}", LocalDateTime.now());

        List<User> users = userService.getAllUsers();
        String[] emails = users.stream()
                .map(User::getEmail)
                .filter(email -> email != null && !email.isBlank())
                .toArray(String[]::new);

        if (emails.length == 0) {
            log.warn("There are no emails to send");
            return;
        }

        Mail mail = Mail.builder()
                .to(emails)
                .subject("We miss you!")
                .body("Come to Interview Academy — new content and challenges are waiting for you!")
                .build();

        emailService.sendEmail(mail);
        log.info("Notifications sent to {} users", emails.length);
    }
}

