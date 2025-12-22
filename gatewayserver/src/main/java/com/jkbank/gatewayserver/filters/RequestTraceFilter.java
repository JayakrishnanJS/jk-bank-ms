package com.jkbank.gatewayserver.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;


// @Order(1) ensures this filter executes first among multiple filters (lower number = higher priority)
@Order(1)
// @Component registers this class as a Spring-managed bean for dependency injection
@Component
// GlobalFilter interface means this filter applies to ALL incoming requests through the gateway
public class RequestTraceFilter implements GlobalFilter {

    // Logger instance for logging filter activities
    private static final Logger logger = LoggerFactory.getLogger(RequestTraceFilter.class);

    // Injecting FilterUtility helper class to manage correlation ID operations
    @Autowired
    FilterUtility filterUtility;

    // Main filter method - executed for every request passing through the gateway
    @Override
    public Mono<Void>
    filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // Mono<Void> = Reactive type representing asynchronous processing with no return value
        // ServerWebExchange = Contains both request and response objects
        // GatewayFilterChain = Allows passing the request to the next filter in the chain

        // Step 1: Extract HTTP headers from the incoming request
        HttpHeaders requestHeaders = exchange.getRequest().getHeaders();

        // Step 2: Check if correlation ID already exists in the request headers
        if(isCorrelationIdPresent(requestHeaders)){
            // If present, log it for tracing purposes
            logger.debug("jkBank-correlation-id found in RequestTraceFilter : {}",
                    filterUtility.getCorrelationId(requestHeaders));
        }else{
            // Step 3: If not present, generate a new unique correlation ID
            String correlationID = generateCorrelationId();

            // Step 4: Add the generated correlation ID to the request headers
            exchange = filterUtility.setCorrelationId(exchange, correlationID);

            // Step 5: Log the newly generated correlation ID
            logger.debug("jkBank-correlation-id generated in RequestTraceFilter : {}", correlationID);
        }

        // Step 6: Pass the modified request to the next filter in the chain
        return chain.filter(exchange);
    }

    // Helper method to check if correlation ID exists in request headers
    private boolean isCorrelationIdPresent(HttpHeaders requestHeaders) {
        // Returns true if correlation ID is found, false otherwise
        if(filterUtility.getCorrelationId(requestHeaders) != null){
            return true;
        }else {
            return false;
        }
    }

    // Helper method to generate a unique correlation ID using UUID
    private String generateCorrelationId() {
        // UUID.randomUUID() creates a universally unique identifier for request tracking
        return UUID.randomUUID().toString();
    }
}
