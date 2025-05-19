package org.example.rabbitmqtutorial.rabbitMQ;

import lombok.RequiredArgsConstructor;
import org.example.rabbitmqtutorial.rabbitMQ.dto.EmailPayload;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmailProducer {
    private final RabbitTemplate rabbitTemplate;

    public void sendWelcomeEmail(EmailPayload payload)
    {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EMAIL_EXCHANGE,
                RabbitMQConfig.EMAIL_ROUTING_KEY,
                payload);
    }

}
