package org.example.rabbitmqtutorial.rabbitMQ;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

    @Bean
    public MessageConverter jsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    // Email 전용 큐
    public static final String EMAIL_QUEUE = "email.queue";
    public static final String EMAIL_EXCHANGE = "email.exchange";
    public static final String EMAIL_ROUTING_KEY = "email.routing.key";

    @Bean
    public Queue emailQueue(){
        return new Queue(EMAIL_QUEUE);
    }

    @Bean
    public DirectExchange emailExchange(){
        return new DirectExchange(EMAIL_EXCHANGE);
    }

    @Bean
    public Binding emailBinding(Queue emailQueue, DirectExchange emailExchange){
        return BindingBuilder
                .bind(emailQueue)
                .to(emailExchange)
                .with(EMAIL_ROUTING_KEY);
    }











    // === [1] Direct Exchange ===
    // 정확한 Routing Key로만 메시지를 전달하는 방식

    public static final String DIRECT_QUEUE = "queue.direct";
    public static final String DIRECT_EXCHANGE = "exchange.direct";
    public static final String DIRECT_KEY = "routing.direct";

    @Bean
    public Queue directQueue(){                 // amqp.core.Queue
        return new Queue(DIRECT_QUEUE);
    }

    // 메시지를 전달할 Exchange
    @Bean
    public DirectExchange directExchange()                 // amqp.core.DirectExchange
    {
        return new DirectExchange(DIRECT_EXCHANGE);
    }

    // Exchange와 Queue를 Routing Key로 연결
    @Bean
    public Binding directBinding(DirectExchange directExchange){
        return BindingBuilder
                .bind(directQueue())             // Queue에
                .to(directExchange())            // Exchange를 연결하고
                .with(DIRECT_KEY);               // Routing Key가 일치할 때만 메시지 전달
    }

    // === [2] Topic Exchange 예시 ===
    // Routing Key 패턴 매칭으로 여러 유형을 구분 가능
    // 예: "user.created", "user.deleted" → "user.*" 로 수신 가능
    public static final String TOPIC_QUEUE = "queue.topic";
    public static final String TOPIC_EXCHANGE = "exchange.topic";
    public static final String TOPIC_KEY = "user.*"; // *는 단어 1개, #는 여러 개

}
