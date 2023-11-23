package com.nijin.dbOps.configs;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.IndexDefinition;

import javax.annotation.PostConstruct;

/**
 * Configuration class for setting up MongoDB collections and indexes.
 * This class ensures that the required MongoDB collection "logEntry" is created
 * and appropriate indexes are applied to improve query performance.
 *
 *
 *   @see lombok.AllArgsConstructor
 *   @see org.springframework.context.annotation.Configuration
 *   @see org.springframework.data.mongodb.core.MongoTemplate
 *   @see org.springframework.data.mongodb.core.index.Index
 *   @see org.springframework.data.mongodb.core.index.IndexDefinition
 *   @see javax.annotation.PostConstruct
 **/


@Configuration
@AllArgsConstructor
public class MongoIndexConfig {

    private final MongoTemplate mongoTemplate;

    /* Ensures that the "logEntry" collection is created and indexes are applied to relevant fields.*/
    @PostConstruct
    public void createCollectionAndIndexes() {
        String collectionName = "logEntry";

        if (!collectionExists(collectionName)) {
            createCollection(collectionName);
            createIndex("level", collectionName);
            createIndex("message", collectionName);
            createIndex("resourceId", collectionName);
            createIndex("timestamp", collectionName);
            createIndex("traceId", collectionName);
            createIndex("spanId", collectionName);
            createIndex("commit", collectionName);
            createIndex("metadata.parentResourceId", collectionName);
        }
    }

    /**
     * Checks if a MongoDB collection exists.
     *
     * @param collectionName The name of the collection to check.
     */
    private boolean collectionExists(String collectionName) {
        return mongoTemplate.collectionExists(collectionName);
    }

    /**
     * Creates a MongoDB collection with the specified name.
     *
     * @param collectionName The name of the collection to create.
     */
    private void createCollection(String collectionName) {
        mongoTemplate.createCollection(collectionName);
    }


    /**
     * Creates an index on a specified field of a MongoDB collection if the index does not exist.
     *
     * @param fieldName      The name of the field on which to create the index.
     * @param collectionName The name of the collection on which to create the index.
     */
    private void createIndex(String fieldName, String collectionName) {
        if (!indexExists(collectionName, fieldName)) {
            IndexDefinition indexDefinition = new Index().on(fieldName, org.springframework.data.domain.Sort.Direction.ASC);
            mongoTemplate.indexOps(collectionName).ensureIndex(indexDefinition);
        }
    }


    /**
     * Checks if an index exists on a specified field of a MongoDB collection.
     *
     * @param collectionName The name of the collection to check for the index.
     * @param indexName      The name of the index to check.
     */
    private boolean indexExists(String collectionName, String indexName) {
        return mongoTemplate.indexOps(collectionName).getIndexInfo().stream()
                .anyMatch(indexInfo -> indexInfo.getName().equals(indexName));
    }
}
