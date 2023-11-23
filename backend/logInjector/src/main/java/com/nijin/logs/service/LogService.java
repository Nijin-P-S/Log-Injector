package com.nijin.logs.service;

import com.nijin.amqp.RabbitMQMessageProducer;
import com.nijin.clients.logInjection.LogEntry;
import com.nijin.clients.logInjection.LogInjectionClient;
import com.nijin.clients.logInjection.LogSearchRequest;
import com.nijin.logs.configs.LogEntryForDBOperationConfig;
import com.nijin.logs.entity.FullTextRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Service class for handling log-related operations.
 * This class provides methods for processing log entries, interacting with RabbitMQ,
 * and performing creation of searchRequest object to be passed to the dbOperationsComponent
 *
 *
 * @see org.springframework.stereotype.Service
 * @see lombok.AllArgsConstructor
 * @see lombok.extern.slf4j.Slf4j
 * @see com.nijin.amqp.RabbitMQMessageProducer
 * @see com.nijin.clients.logInjection.LogEntry
 * @see com.nijin.clients.logInjection.LogInjectionClient
 * @see com.nijin.clients.logInjection.LogSearchRequest
 * @see com.nijin.logs.configs.LogEntryForDBOperationConfig
 * @see com.nijin.logs.entity.FullTextRequest
 **/

@Service
@Slf4j
@AllArgsConstructor
public class LogService {

    /** The client for interacting with the log injection openFeign. */
    private final LogInjectionClient logInjectionClient;

    /** The producer for sending log entries to RabbitMQ. */
    private final RabbitMQMessageProducer rabbitMQMessageProducer;

    /** The configuration for log entries to the correct RabbitMQ queue */
    private final LogEntryForDBOperationConfig logEntryForDBOperationConfig;



    /* Publishes a log entry to the database service via RabbitMQ.*/
    public void passToDBService(LogEntry logEntry){
        rabbitMQMessageProducer.publish(
                logEntry,
                logEntryForDBOperationConfig.getInternalExchange(),
                logEntryForDBOperationConfig.getInternalDbOperationRoutingKey()
        );
        log.info("Succesfully pushed the log Entry {} to Message Queue", logEntry);
    }

    /** Builds the LogSearchRequest object based on the textRequest
     * Uses the fieldList and valueList to get the respective values for building the request object
     * Sends the request using logInjectionClient and the result is returned.
    **/
    public List<LogEntry> getAllLogsBasedOnText(FullTextRequest fullTextRequest){
        String textReceived = fullTextRequest.getFullText();

        List<String> fieldList = new ArrayList<>();
        List<String> valueList = new ArrayList<>();

        extractFields(textReceived, fieldList);
        extractValues(textReceived, valueList);

        LogSearchRequest request;
        if(fieldList.size() <= valueList.size()){
            request = buildLogSearchRequest(fieldList, valueList);
            System.out.println(request);
            return logInjectionClient.searchByFilters(request);
        }
        return null;
    }

    /** Function for building the LogSearchRequest object based on fieldList and valueList **/
    public LogSearchRequest buildLogSearchRequest(List<String> fieldList, List<String> valueList) {
        LogSearchRequest.LogSearchRequestBuilder builder = LogSearchRequest.builder();
        int valueIndex = 0;
        for (int fieldIndex = 0; fieldIndex < fieldList.size(); fieldIndex++) {
            String field = fieldList.get(fieldIndex);
            String value = valueList.get(valueIndex);

            switch (field) {
                case "level":
                    builder.level(value);
                    break;
                case "message":
                    builder.message(value);
                    break;
                case "resourceId":
                    builder.resourceId(value);
                    break;
                case "timestamp":
                    if(fieldList.size() < valueList.size()){
                        builder.startTimestamp(valueList.get(valueIndex++));
                        builder.endTimestamp(valueList.get(valueIndex));
                    }
                    else{
                        builder.timestamp(value);
                    }
                    break;
                case "traceId":
                    builder.traceId(value);
                    break;
                case "spanId":
                    builder.spanId(value);
                    break;
                case "commit":
                    builder.commit(value);
                    break;
                case "metadata.parentResourceId":
                    if (value != null) {
                        LogEntry.Metadata metadata = new LogEntry.Metadata();
                        metadata.setParentResourceId((String) value);
                        builder.metadata(metadata);
                    }
                    break;
            }
            valueIndex++;
        }

        return builder.build();
    }

    /** Function to fetch only the field values and add it to the list **/
    private static void extractFields(String inputText, List<String> fieldList) {
        List<String> fieldsToCheck = Arrays.asList("level", "message", "resourceId", "timestamp", "traceId", "spanId", "commit", "metadata.parentResourceId");

        String fieldPattern = String.join("|", fieldsToCheck);
        Pattern pattern = Pattern.compile("\\b(" + fieldPattern + ")\\b");
        Matcher matcher = pattern.matcher(inputText);

        while (matcher.find()) {
            String matchedField = matcher.group(1);
            fieldList.add(matchedField);
        }
    }

    /** Function to fetch only the values and add it to the list **/
    private static void extractValues(String inputText, List<String> valueList) {
        Pattern pattern = Pattern.compile("\"([^\"]+)\"");
        Matcher matcher = pattern.matcher(inputText);

        while (matcher.find()) {
            String matchedValue = matcher.group(1);
            valueList.add(matchedValue);
        }
    }

    /** Calls the searchByFilters() by passing the request object using logInjectionClient, and result is returned **/
    public List<LogEntry> getLogsByFilters(LogSearchRequest logSearchRequest) {
        return logInjectionClient.searchByFilters(logSearchRequest);
    }
}
