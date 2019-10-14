package com.messaging.rabbitmq.services;

import com.messaging.rabbitmq.configurations.RabbitMsgConfiguration;
import com.messaging.rabbitmq.domain.SimpleMessage;
import com.messaging.rabbitmq.rabbit.MessageProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ScheduledMessageSenderService {

    private static final Logger log = LoggerFactory.getLogger(ScheduledMessageSenderService.class);

    private AtomicInteger app1MessageCounter;

    private final MessageProducer messageProducer;
    private final RabbitTemplate rabbitTemplate;
    private final RabbitMsgConfiguration rabbitMsgConfiguration;

    public ScheduledMessageSenderService( MessageProducer messageProducer,
                                         RabbitTemplate rabbitTemplate,
                                         RabbitMsgConfiguration rabbitMsgConfiguration) {
        this.messageProducer = messageProducer;
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitMsgConfiguration = rabbitMsgConfiguration;
        this.app1MessageCounter = new AtomicInteger();
    }

    @Scheduled(initialDelay = 1000, fixedDelay = 3000)
    public void sendStringMessageToApp1Queue() {

        final var message = "Simple string message with id - " + app1MessageCounter.incrementAndGet();
        log.info("Sending message... to - {}", rabbitMsgConfiguration.getApp1QueueName());
        messageProducer.sendMessage(rabbitTemplate,
                rabbitMsgConfiguration.getApp1ExchangeName(),
                rabbitMsgConfiguration.getApp1RoutingKey(), message);
    }

    @Scheduled(initialDelay = 1000, fixedDelay = 3000)
    public void sendPojoToApp2Queue() {

        final var simpleMessage = new SimpleMessage("Title", "Message body", true);

        log.info("Sending message... to - {}", rabbitMsgConfiguration.getApp2QueueName());

        messageProducer.sendMessage(rabbitTemplate,
                rabbitMsgConfiguration.getApp2ExchangeName(),
                rabbitMsgConfiguration.getApp2RoutingKey(),
                simpleMessage);
    }

}
