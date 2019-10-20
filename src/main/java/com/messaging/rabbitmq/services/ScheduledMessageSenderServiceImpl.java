package com.messaging.rabbitmq.services;

import com.messaging.rabbitmq.configurations.RmqConfiguration;
import com.messaging.rabbitmq.domain.SimpleMessage;
import com.messaging.rabbitmq.rabbit.MessageProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Profile("shortCfg")
@Service
public class ScheduledMessageSenderServiceImpl implements ScheduledMessageSenderService {

    private static final Logger log = LoggerFactory.getLogger(ScheduledMessageSenderServiceImpl.class);

    private final RmqConfiguration rmqConfiguration;
    private final MessageProducer messageProducer;
    private final RabbitTemplate rabbitTemplate;
    private AtomicInteger app1MessageCounter;
    private final AmqpAdmin amqpAdmin;


    public ScheduledMessageSenderServiceImpl(RmqConfiguration rmqConfiguration,
                                             MessageProducer messageProducer,
                                             RabbitTemplate rabbitTemplate,
                                             AmqpAdmin amqpAdmin) {
        this.rmqConfiguration = rmqConfiguration;
        this.messageProducer = messageProducer;
        this.rabbitTemplate = rabbitTemplate;
        this.amqpAdmin = amqpAdmin;
        app1MessageCounter = new AtomicInteger();
    }

    //@Scheduled(initialDelay = 1000, fixedDelay = 3000)
    @Override
    public void sendStringMessageToApp1Queue() {
        final var message = "Simple string message with id - " + app1MessageCounter.incrementAndGet();
        log.info("Sending message... to - {}", rmqConfiguration.getApp1QueueName());
        messageProducer.sendMessage(rabbitTemplate,
                rmqConfiguration.getApp1ExchangeName(),
                rmqConfiguration.getApp1RoutingKey(), message);

    }

    //@Scheduled(initialDelay = 1000, fixedDelay = 3000)
    @Override
    public void sendPojoToApp2Queue() {
        final var simpleMessage = new SimpleMessage("Title", "Message body", true);

        log.info("Sending message... to - {}", rmqConfiguration.getApp2QueueName());

        messageProducer.sendMessage(rabbitTemplate,
                rmqConfiguration.getApp2ExchangeName(),
                rmqConfiguration.getApp2RoutingKey(),
                simpleMessage);
    }
}
