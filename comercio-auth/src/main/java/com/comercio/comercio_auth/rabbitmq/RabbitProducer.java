package com.comercio.comercio_auth.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.comercio.comercio_auth.auth.dto.RabbitDTO;

import tools.jackson.databind.ObjectMapper;

@Service
public class RabbitProducer {

    private final RabbitTemplate rabbitTemplate;

    public RabbitProducer(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendUserId(RabbitDTO rabbitDTO) {
        try {
            rabbitTemplate.convertAndSend("auth-queue", rabbitDTO);
            
        } catch (Exception e) {
            throw new RuntimeException("Fail to sent message", e);
        }
    }
}
