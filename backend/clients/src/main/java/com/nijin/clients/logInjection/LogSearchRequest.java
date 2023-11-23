package com.nijin.clients.logInjection;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * Represents a request object for querying log entries in the database.
 * This class encapsulates search criteria such as log level, message, resource ID, timestamp,
 * trace ID, span ID, commit, start timestamp, end timestamp, and associated metadata.
 *
 *
 * @see lombok.Data
 * @see lombok.Builder
 * @see lombok.AllArgsConstructor
 * @see lombok.NoArgsConstructor
 * @see com.fasterxml.jackson.annotation.JsonFormat
 **/

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogSearchRequest {
    private String level;
    private String message;
    private String resourceId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private String timestamp;

    private String traceId;
    private String spanId;
    private String commit;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private String startTimestamp;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private String endTimestamp;

    private LogEntry.Metadata metadata;

    @Data
    public static class Metadata {
        private String parentResourceId;
    }
}
