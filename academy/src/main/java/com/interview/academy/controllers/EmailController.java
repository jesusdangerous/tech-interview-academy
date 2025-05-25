package com.interview.academy.controllers;

import com.interview.academy.domain.entities.Mail;
import com.interview.academy.services.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/verify")
    public void sendVerificationEmail(@RequestBody Mail mail) {
        emailService.sendEmail(mail);
    }
}
