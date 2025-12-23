package com.jkbank.gatewayserver.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

import java.util.logging.Filter;

@Configuration
public class ResponseTraceFilter {

    // Logger instance for debugging and tracking filter operations
    private static final Logger logger = LoggerFactory.getLogger(ResponseTraceFilter.class);

    // Inject utility class for handling correlation ID operations
    @Autowired
    FilterUtility filterUtility;

    // Define a global post-filter bean that executes after the request is processed
    @Bean
    public GlobalFilter postGlobalFilter() {
        return (exchange, chain) -> {
            // Continue the filter chain and execute post-processing logic after response
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                // Extract request headers from the incoming request
                HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
                // Retrieve the correlation ID from request headers using utility
                String correlationID = filterUtility.getCorrelationId(requestHeaders);
                // Check if the correlation ID is already present in the response headers, otherwise retries may add multiple IDs
                if(exchange.getResponse().getHeaders().get(filterUtility.CORRELATION_ID) == null) {
                    // Log the correlation ID for debugging purposes
                    logger.debug("Updated the correlation id to the outbound headers : {}", correlationID);
                    // Add correlation ID to response headers for tracing across services
                    exchange.getResponse().getHeaders().add(filterUtility.CORRELATION_ID, correlationID);
                }
            }));
        };
    }
}
