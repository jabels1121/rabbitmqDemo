package com.messaging.rabbitmq.rabbit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {

    private static final Logger log = LoggerFactory.getLogger(MessageProducer.class);

    public void sendMessage(RabbitTemplate rabbitTemplate, String exchange, String routingKey, Object data) {
        log.info("Sending message to the queue using routingKey {}. Message= {}", routingKey, data);
        rabbitTemplate.convertAndSend(exchange, routingKey, data);
        log.info("The message has been sent to the queue.");
    }

}
