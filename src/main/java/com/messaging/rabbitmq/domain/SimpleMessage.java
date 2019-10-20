package com.messaging.rabbitmq.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleMessage implements Serializable {

    private String title;
    private String body;
    private boolean secret;

}
