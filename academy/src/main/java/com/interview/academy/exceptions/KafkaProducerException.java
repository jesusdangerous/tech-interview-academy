package com.interview.academy.exceptions;

public class KafkaProducerException extends RuntimeException {
    public KafkaProducerException(String message, Throwable cause) {
        super(message, cause);
    }
}
