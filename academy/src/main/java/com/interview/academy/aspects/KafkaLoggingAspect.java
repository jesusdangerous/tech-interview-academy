package com.interview.academy.aspects;

import com.interview.academy.domain.dtos.PostEventDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class KafkaLoggingAspect {

    @Around("execution(* com.interview.academy.services.impl.KafkaProducerServiceImpl.sendNewPostEvent(..)) && args(event)")
    public Object logKafkaMessageSending(ProceedingJoinPoint joinPoint, PostEventDto event) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        log.info("[Kafka Producer] Attempting to send message to topic 'new-post-topic'. Payload: {}", event);

        try {
            Object result = joinPoint.proceed();

            log.info("[Kafka Producer] Message sent successfully to topic 'new-post-topic'");
            return result;

        } catch (Exception ex) {
            log.error("[Kafka Producer] Failed to send message to topic 'new-post-topic'. Error: {}", ex.getMessage(), ex);
            throw ex;
        }
    }
}