package com.interview.academy.services;

import com.interview.academy.domain.entities.Mail;

import java.util.concurrent.CompletableFuture;

public interface EmailService {

    void sendEmail(Mail mail);
    CompletableFuture<Void> sendEmailAsync(Mail mail);
}
