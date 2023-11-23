package com.nijin.amqp;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;


/**
 * RabbitMQMessageProducer is responsible for publishing messages to a RabbitMQ message broker.
 * It encapsulates the logic of converting and sending messages to specified exchanges and routing keys.
 *
 * @see org.springframework.amqp.core.AmqpTemplate
 * @see org.springframework.amqp.rabbit.core.RabbitTemplate
 **/

@Component
@Slf4j
@AllArgsConstructor
public class RabbitMQMessageProducer {

    private final AmqpTemplate amqpTemplate;

    public void publish(Object payload, String exchange, String routingKey){
        log.info("Publishing to {} using routingKey {}, Payload {}", exchange, routingKey, payload);
        amqpTemplate.convertAndSend(exchange, routingKey, payload);
        log.info("Published to {} using routingKey {}, Payload {}", exchange, routingKey, payload);
    }
}
