package com.messaging.rabbitmq.configurations;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

@Profile("longCfg")
@EnableConfigurationProperties({MsgApp1Props.class, MsgApp2Props.class})
@Configuration
@EnableRabbit
public class RabbitMsgConfiguration implements RabbitListenerConfigurer {

    private final MsgApp1Props msgApp1Props;
    private final MsgApp2Props msgApp2Props;

    public RabbitMsgConfiguration(MsgApp1Props msgApp1Props,
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
    public TopicExchange getApp1Exchange() {
        return new TopicExchange(msgApp1Props.getExchangeName());
    }

    @Bean
    public TopicExchange getApp2Exchange() {
        return new TopicExchange(msgApp2Props.getExchangeName());
    }

    @Bean
    public Queue getApp1Queue() {
        String queueName = msgApp1Props.getQueueName();
        return new Queue(queueName);
    }

    @Bean
    public Queue getApp2Queue() {
        return new Queue(msgApp2Props.getQueueName());
    }

    @Bean
    public Binding declareBindingApp1() {
        return BindingBuilder.bind(getApp1Queue()).to(getApp1Exchange()).with(msgApp1Props.getRoutingKey());
    }

    @Bean
    public Binding declareBindingApp2() {
        return BindingBuilder.bind(getApp2Queue()).to(getApp2Exchange()).with(msgApp2Props.getRoutingKey());
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {
        rabbitListenerEndpointRegistrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }

    /* Bean for rabbitTemplate */
    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(consumerJackson2MessageConverter());
        return factory;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }

}
