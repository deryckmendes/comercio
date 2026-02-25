package com.comercio.comercio_catalogo.rabbitmq;

import com.comercio.comercio_catalogo.category.CategoryService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class RabbitConsumer {

    private final CategoryService categoryService;

    RabbitConsumer(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RabbitListener(queues = "auth-queue")
    private void listenAuthQueue(@Payload RabbitDTO authMessage) {
        categoryService.createInitialCategory(authMessage.userId());
    }
}
