package com.nijin.dbOps.service;

import com.nijin.clients.logInjection.LogEntry;
import com.nijin.clients.logInjection.LogSearchRequest;
import com.nijin.dbOps.repository.DbOperationsRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@AllArgsConstructor
public class DbService {
    private final DbOperationsRepository dbOperationsRepository;

    public void send(LogEntry logEntry){
        dbOperationsRepository.saveToDB(logEntry);
    }


    public List<LogEntry> queryByFilters(@RequestBody LogSearchRequest logSearchRequest){
        Criteria criteria = new Criteria();

        if (logSearchRequest.getLevel() != null) {
            criteria.and("level").is(logSearchRequest.getLevel());
        }

        if (logSearchRequest.getStartTimestamp() != null && logSearchRequest.getEndTimestamp() != null) {
            criteria.andOperator(
                    Criteria.where("timestamp").gte(logSearchRequest.getStartTimestamp()),
                    Criteria.where("timestamp").lte(logSearchRequest.getEndTimestamp())
            );
        } else if (logSearchRequest.getTimestamp() != null) {
            criteria.and("timestamp").is(logSearchRequest.getTimestamp());
        }

        if (logSearchRequest.getMessage() != null) {
            criteria.and("message").is(logSearchRequest.getMessage());
        }

        if (logSearchRequest.getResourceId() != null) {
            criteria.and("resourceId").is(logSearchRequest.getResourceId());
        }

        if (logSearchRequest.getTraceId() != null) {
            criteria.and("traceId").is(logSearchRequest.getTraceId());
        }

        if (logSearchRequest.getSpanId() != null) {
            criteria.and("spanId").is(logSearchRequest.getSpanId());
        }

        if (logSearchRequest.getCommit() != null) {
            criteria.and("commit").is(logSearchRequest.getCommit());
        }

        if (logSearchRequest.getMetadata() != null && logSearchRequest.getMetadata().getParentResourceId() != null) {
            criteria.and("metadata.parentResourceId").is(logSearchRequest.getMetadata().getParentResourceId());
        }



        return dbOperationsRepository.findByFilters(criteria);
    }
}
