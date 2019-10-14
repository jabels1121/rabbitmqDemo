package com.messaging.rabbitmq.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "simple_messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleMessageDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String messageTitle;

    private String messageBody;

    private boolean secret;

}
