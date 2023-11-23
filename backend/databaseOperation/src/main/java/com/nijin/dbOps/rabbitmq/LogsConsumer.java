package com.nijin.dbOps.rabbitmq;

import com.nijin.clients.logInjection.LogEntry;
import com.nijin.dbOps.service.DbService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


/**
 * RabbitMQ message consumer for log entries.
 * This class is responsible for consuming log entries from the RabbitMQ queue and
 * forwarding them to the  DbService for further processing and storage.
 *
 *   @see lombok.AllArgsConstructor
 *   @see lombok.extern.slf4j.Slf4j
 *   @see org.springframework.amqp.rabbit.annotation.RabbitListener
 *   @see org.springframework.stereotype.Component
 **/

@Component
@AllArgsConstructor
@Slf4j
public class LogsConsumer {

    /** The service for handling database operations. */
    private final DbService dbService;

    /**RabbitMQ message consumer for log entries.*/
    @RabbitListener(queues = "${rabbitmq.queues.dbOperation}")
    public void consumer(LogEntry logEntry){
        log.info("Consumed {} from queue", logEntry);
        dbService.send(logEntry);
    }
}
