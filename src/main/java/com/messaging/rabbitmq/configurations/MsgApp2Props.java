package com.messaging.rabbitmq.configurations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "app2")
public class MsgApp2Props {

    private String exchangeName;
    private String queueName;
    private String routingKey;

}
