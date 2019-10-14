package com.messaging.rabbitmq.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleMessage {

    private String title;
    private String body;
    private boolean secret;

}
