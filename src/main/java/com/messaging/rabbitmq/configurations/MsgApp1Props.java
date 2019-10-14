package com.messaging.rabbitmq.configurations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "app1")
public class MsgApp1Props {

    private String exchangeName;
    private String queueName;
    private String routingKey;

}
