package com.interview.academy.services;

import com.interview.academy.domain.entities.Mail;

public interface EmailService {

    void sendVerificationEmail(Mail mail);
}
