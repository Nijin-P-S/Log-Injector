package com.nijin.dbOps.controller;


import com.nijin.clients.logInjection.LogEntry;
import com.nijin.clients.logInjection.LogSearchRequest;
import com.nijin.dbOps.repository.DbOperationsRepository;
import com.nijin.dbOps.service.DbService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
public class DbController {

    private final DbService dbService;

    @PostMapping
    public String pushToDB(@RequestBody LogEntry logEntry){
        dbService.send(logEntry);
        log.info("Got the LogEntry {}", logEntry);
        return "Successful";
    }


    @PostMapping("/search")
    public List<LogEntry> searchByFilters(@RequestBody LogSearchRequest logSearchRequest){
        List<LogEntry> output = dbService.queryByFilters(logSearchRequest);
        log.info("Got the List of requested data : {} ", output);
        return output;
    }
}
