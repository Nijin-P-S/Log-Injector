package com.nijin.dbOps.configs;

import lombok.Getter;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for RabbitMQ operations for databaseOperations service.
 * This class defines beans for configuring RabbitMQ exchanges, queues, and bindings
 * specifically used by the "dbOperation" component.
 *
 * @see org.springframework.context.annotation.Configuration
 * @see org.springframework.beans.factory.annotation.Value
 * @see org.springframework.amqp.core.TopicExchange
 * @see org.springframework.amqp.core.Queue
 * @see org.springframework.amqp.core.Binding
 * @see org.springframework.amqp.core.BindingBuilder
 **/

@Configuration
@Getter
public class DbOperationConfig {

    @Value("${rabbitmq.exchanges.internal}")
    private String internalExchange;

    @Value("${rabbitmq.queues.dbOperation}")
    private String dbOperationQueue;

    @Value("${rabbitmq.routing-keys.internal-dpOperation}")
    private String internalDbOperationRoutingKey;

    @Bean
    public TopicExchange internalTopicExchange(){
        return new TopicExchange(this.internalExchange);
    }

    @Bean
    public Queue dbOperationQueue(){
        return new Queue(this.dbOperationQueue);
    }

    @Bean
    public Binding internalToDBOperationBinding(){
        return BindingBuilder
                .bind(dbOperationQueue())
                .to(internalTopicExchange())
                .with(this.internalDbOperationRoutingKey);
    }

}
