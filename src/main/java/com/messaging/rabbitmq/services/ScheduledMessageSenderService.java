package com.messaging.rabbitmq.services;

import org.springframework.scheduling.annotation.Scheduled;

public interface ScheduledMessageSenderService {

    @Scheduled(initialDelay = 1000, fixedDelay = 3000)
    void sendStringMessageToApp1Queue();

    @Scheduled(initialDelay = 1000, fixedDelay = 3000)
    void sendPojoToApp2Queue();
}
