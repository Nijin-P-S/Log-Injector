package com.nijin.clients.logInjection;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Represents a log entry entity to be saved in the database.
 * This class encapsulates information about a log, including its level, message, resource ID,
 * timestamp, trace ID, span ID, commit, and associated metadata.
 *
 *
 * @see lombok.Getter
 * @see lombok.Builder
 * @see lombok.AllArgsConstructor
 * @see lombok.NoArgsConstructor
 * @see com.fasterxml.jackson.annotation.JsonIgnore
 * @see com.fasterxml.jackson.annotation.JsonProperty
 * @see javax.validation.constraints.NotNull
 * @see com.fasterxml.jackson.annotation.JsonFormat
 * */

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogEntry {
    @JsonIgnore
    String id;

    @JsonProperty("level")
    @NotNull
    private String level;

    @JsonProperty("message")
    @NotNull
    private String message;

    @JsonProperty("resourceId")
    @NotNull
    private String resourceId;

    @JsonProperty("timestamp")
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private String timestamp;


    @JsonProperty("traceId")
    @NotNull
    private String traceId;


    @JsonProperty("spanId")
    @NotNull
    private String spanId;

    @JsonProperty("commit")
    @NotNull
    private String commit;

    @JsonProperty("metadata")
    @NotNull
    private Metadata metadata;

    @Data
    public static class Metadata {
        @JsonProperty("parentResourceId")
        @NotNull
        private String parentResourceId;

    }
    @Override
    public String toString() {
        return "LogEntry{" +
                "level='" + level + '\'' +
                ", message='" + message + '\'' +
                ", resourceId='" + resourceId + '\'' +
                ", timestamp=" + timestamp +
                ", traceId='" + traceId + '\'' +
                ", spanId='" + spanId + '\'' +
                ", commit='" + commit + '\'' +
                ", metadata=" + metadata +
                '}';
    }
}
