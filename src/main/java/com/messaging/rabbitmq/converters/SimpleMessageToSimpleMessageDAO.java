package com.messaging.rabbitmq.converters;

import com.messaging.rabbitmq.domain.SimpleMessage;
import com.messaging.rabbitmq.domain.SimpleMessageDAO;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SimpleMessageToSimpleMessageDAO implements Converter<SimpleMessage, SimpleMessageDAO> {

    @Synchronized
    @Override
    public SimpleMessageDAO convert(SimpleMessage simpleMessage) {
        final var simpleMessageDao = new SimpleMessageDAO();
        simpleMessageDao.setMessageBody(simpleMessage.getBody());
        simpleMessageDao.setMessageTitle(simpleMessage.getTitle());
        simpleMessageDao.setSecret(simpleMessage.isSecret());
        return simpleMessageDao;
    }
}
