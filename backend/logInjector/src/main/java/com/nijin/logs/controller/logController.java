package com.nijin.logs.controller;

import com.nijin.clients.logInjection.LogEntry;
import com.nijin.clients.logInjection.LogSearchRequest;
import com.nijin.logs.entity.ErrorResponse;
import com.nijin.logs.entity.FullTextRequest;
import com.nijin.logs.entity.Response;
import com.nijin.logs.entity.SucessResponse;
import com.nijin.logs.service.LogService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/logIngestor")
@CrossOrigin("*")
@Slf4j
public class logController {
    @Autowired
    LogService logService;

    @PostMapping("/insertLogs")
    public Response logEntryToDatabase(
            @Valid @RequestBody LogEntry logEntry
            ){
        try {
            logService.passToDBService(logEntry);
            log.info("Log entry {} successfully processed.", logEntry);
            SucessResponse response = new SucessResponse();
            response.setMessage("Successfully added the log to DB");
            response.setData(logEntry);
            return response;
        } catch (Exception e) {
            // Handle the exception here, you can log it or return an error response
            log.error("Error processing log entry: {}", e);
            String errorMessage = "Error processing log entry: " + e.getMessage();
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage(errorMessage);
            return errorResponse;
        }
    }

    @PostMapping("/search")
    public List<LogEntry> searchByFilters(@RequestBody LogSearchRequest logSearchRequest){
        try {
            return logService.getLogsByFilters(logSearchRequest);
        } catch (Exception e) {
            log.error("Error occured while searching by filters {}", e);
            String errorMessage = "Error searching logs: " + e.getMessage();
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage(errorMessage);
            return Collections.emptyList();
        }
    }


    @PostMapping("/searchWithFullText")
    public List<LogEntry> searchWithText(@RequestBody FullTextRequest textRequest){
        try {
            return logService.getAllLogsBasedOnText(textRequest);
        } catch (Exception e) {
            log.error("Error occured while searching by text {}", e);
            String errorMessage = "Error searching logs: " + e.getMessage();
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage(errorMessage);
            return Collections.emptyList();
        }  
    }
}
