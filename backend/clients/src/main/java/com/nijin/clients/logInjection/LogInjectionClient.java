package com.nijin.clients.logInjection;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Feign client interface for interacting with a service responsible for logging operations.
 * This client communicates with a service named "dbOperation" and provides methods for pushing
 * log entries to the database and searching for log entries based on specified filters.
 *
 *
 *  @see org.springframework.cloud.openfeign.FeignClient
 *  @see org.springframework.web.bind.annotation.PostMapping
 *  @see org.springframework.web.bind.annotation.RequestBody
 **/

@FeignClient(name = "dbOperation")
public interface LogInjectionClient {

    @PostMapping("/")
    String pushToDB(@RequestBody LogEntry logEntry);

    @PostMapping("/search")
    List<LogEntry> searchByFilters(@RequestBody LogSearchRequest logSearchRequest);

}
