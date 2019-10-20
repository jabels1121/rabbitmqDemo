package com.messaging.rabbitmq.configurations;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.amqp.core.BindingBuilder.bind;

@Profile("shortCfg")
@Configuration
@EnableRabbit
@EnableConfigurationProperties({MsgApp1Props.class, MsgApp2Props.class})
public class RmqConfiguration {


    private final MsgApp1Props msgApp1Props;
    private final MsgApp2Props msgApp2Props;

    public RmqConfiguration(MsgApp1Props msgApp1Props,
                            MsgApp2Props msgApp2Props) {
        this.msgApp1Props = msgApp1Props;
        this.msgApp2Props = msgApp2Props;
    }

    public String getApp1QueueName() {
        return msgApp1Props.getQueueName();
    }

    public String getApp1RoutingKey() {
        return msgApp1Props.getRoutingKey();
    }

    public String getApp2QueueName() {
        return msgApp2Props.getQueueName();
    }

    public String getApp2RoutingKey() {
        return msgApp2Props.getRoutingKey();
    }

    public String getApp1ExchangeName() {
        return msgApp1Props.getExchangeName();
    }

    public String getApp2ExchangeName() {
        return msgApp2Props.getExchangeName();
    }


    @Bean
    public Declarables declarables() {
        Map<String, Object> queueArgs = new HashMap<>();
        queueArgs.put("Tst args", 500);

        var queueApp1 = QueueBuilder.nonDurable(getApp1QueueName()).autoDelete().withArguments(queueArgs).build();
        var queueApp2 = QueueBuilder.nonDurable(getApp2QueueName()).autoDelete().build();
        var topicExchangeApp1 = (TopicExchange) ExchangeBuilder.topicExchange(getApp1ExchangeName()).durable(false).autoDelete().build();
        var topicExchangeApp2 = (TopicExchange) ExchangeBuilder.topicExchange(getApp2ExchangeName()).durable(false).autoDelete().build();

        return new Declarables(
                queueApp1,
                queueApp2,
                topicExchangeApp1,
                topicExchangeApp2,
                bind(queueApp1).to(topicExchangeApp1).with(getApp1RoutingKey()),
                bind(queueApp2).to(topicExchangeApp2).with(getApp2RoutingKey())
        );
    }

    @Bean
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

}
