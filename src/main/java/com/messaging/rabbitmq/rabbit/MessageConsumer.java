package com.messaging.rabbitmq.rabbit;

import com.messaging.rabbitmq.converters.SimpleMessageToSimpleMessageDAO;
import com.messaging.rabbitmq.domain.SimpleMessage;
import com.messaging.rabbitmq.domain.SimpleMessageDAO;
import com.messaging.rabbitmq.repositories.SimpleMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class MessageConsumer {

    private static final Logger log = LoggerFactory.getLogger(MessageConsumer.class);

    private final SimpleMessageRepository simpleMessageRepository;
    private final SimpleMessageToSimpleMessageDAO converter;

    public MessageConsumer(SimpleMessageRepository simpleMessageRepository,
                           SimpleMessageToSimpleMessageDAO converter) {
        this.simpleMessageRepository = simpleMessageRepository;
        this.converter = converter;
    }

    @RabbitListener(queues = "${app1.queue-name}") //getApp1Queue   ${app1.queue-name}
    public void receiveMessageFromApp1Queue(final String message, final Message amqpMessage,
                                            final @Headers Map<String, Object> messageHeaders) {
        log.info("Received message from app1.queue - {}", message);
        log.info("Received amqpMessage body is - {}", amqpMessage.getBody());
        messageHeaders.forEach((key, value) -> log.info("Message header key = {}, message header value = {}", key, value));
        final ArrayList listHead = (ArrayList)messageHeaders.get("listHead");
        var messageProperties = amqpMessage.getMessageProperties();
        final var appId = messageProperties.getAppId();
    }

    @RabbitListener(queues = "${app2.queue-name}")
    public void receiveMessageFromApp2Queue(final SimpleMessage message) {
        log.info("Received message from app2.queue - {}", message);


        SimpleMessageDAO convert = converter.convert(message);
        SimpleMessageDAO save = simpleMessageRepository.save(convert);

        log.info("Saved message to DB - {}", save);
    }


}
