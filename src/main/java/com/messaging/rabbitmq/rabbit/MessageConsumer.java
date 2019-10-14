package com.messaging.rabbitmq.rabbit;

import com.messaging.rabbitmq.converters.SimpleMessageToSimpleMessageDAO;
import com.messaging.rabbitmq.domain.SimpleMessage;
import com.messaging.rabbitmq.domain.SimpleMessageDAO;
import com.messaging.rabbitmq.repositories.SimpleMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

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

    @RabbitListener(queues = "${app1.queue-name}")
    public void receiveMessageFromApp1Queue(final String message) {
        log.info("Received message from app1.queue - {}", message);
    }

    @RabbitListener(queues = "${app2.queue-name}")
    public void receiveMessageFromApp2Queue(final SimpleMessage message) {
        log.info("Received message from app1.queue - {}", message);


        SimpleMessageDAO convert = converter.convert(message);
        SimpleMessageDAO save = simpleMessageRepository.save(convert);

        log.info("Saved message to DB - {}", save);
    }


}
