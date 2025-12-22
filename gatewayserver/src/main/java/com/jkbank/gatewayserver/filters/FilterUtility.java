package com.jkbank.gatewayserver.filters;

import org.springframework.stereotype.Component;

import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;

@Component
public class FilterUtility {

    // Constant defining the header name for correlation ID used to track requests across services
    public static final String CORRELATION_ID = "jkbank-correlation-id";

    // Retrieves the correlation ID from the incoming request headers
    public String getCorrelationId(HttpHeaders requestHeaders) {
        // Check if the correlation ID header exists in the request
        if (requestHeaders.get(CORRELATION_ID) != null) {
            // Get the list of correlation ID header values (headers can have multiple values)
            List<String> requestHeaderList = requestHeaders.get(CORRELATION_ID);
            // Return the first correlation ID value from the list
            return requestHeaderList.stream().findFirst().get();
        } else {
            // Return null if correlation ID header is not present
            return null;
        }
    }

    // Public method to add/set correlation ID in the outgoing request
    public ServerWebExchange setCorrelationId(ServerWebExchange exchange, String correlationId) {
        // Delegate to the generic setRequestHeader method with correlation ID header name
        return this.setRequestHeader(exchange, CORRELATION_ID, correlationId);
    }

    // Private helper method to add/modify a header in the ServerWebExchange
    private ServerWebExchange setRequestHeader(ServerWebExchange exchange, String name, String value) {
        // Create a new ServerWebExchange by mutating the existing one
        return exchange.mutate().request(
                // Mutate the request within the exchange
                exchange.getRequest().mutate()
                        // Add the header with specified name and value
                        .header(name, value)
                        // Build the modified request
                        .build()
                // Build the modified exchange
        ).build();
    }
}
