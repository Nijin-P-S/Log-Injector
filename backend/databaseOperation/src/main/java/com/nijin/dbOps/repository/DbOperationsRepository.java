package com.nijin.dbOps.repository;

import com.nijin.clients.logInjection.LogEntry;

import com.nijin.clients.logInjection.LogSearchRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class DbOperationsRepository{
    private final MongoTemplate mongoTemplate;

    public DbOperationsRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<LogEntry> findByFilters(Criteria criteria) {

        Query query = new Query(criteria);

        return mongoTemplate.find(query, LogEntry.class);
    }


    public void saveToDB(LogEntry logEntry){
        mongoTemplate.save(logEntry);
        log.info("Log Entry {} saved to Database", logEntry);

    }
}
