package com.messaging.rabbitmq.repositories;

import com.messaging.rabbitmq.domain.SimpleMessage;
import com.messaging.rabbitmq.domain.SimpleMessageDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SimpleMessageRepository extends JpaRepository<SimpleMessageDAO, Long> {
}
