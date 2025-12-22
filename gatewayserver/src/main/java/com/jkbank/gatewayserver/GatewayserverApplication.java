package com.jkbank.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

import static org.springframework.cloud.gateway.support.RouteMetadataUtils.CONNECT_TIMEOUT_ATTR;
import static org.springframework.cloud.gateway.support.RouteMetadataUtils.RESPONSE_TIMEOUT_ATTR;

@SpringBootApplication
public class GatewayserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayserverApplication.class, args);
    }

    @Bean
    public RouteLocator jkBankRouteConfig(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(p -> p
                        .path("/jkbank/accounts/**")
                        .filters(f -> f.rewritePath("/jkbank/accounts/(?<segment>.*)", "/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                .circuitBreaker(config -> config.setName("accountsCircuitBreaker")
                                        .setFallbackUri("forward:/contactSupport"))

                        )
                        .uri("lb://ACCOUNTS"))// Logical name of the service registered with Eureka
                .route(p -> p
                        .path("/jkbank/loans/**")
                        .filters(f -> f.rewritePath("/jkbank/loans/(?<segment>.*)", "/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
                        .metadata(RESPONSE_TIMEOUT_ATTR, 5000) // per-route response timeout
                        .metadata(CONNECT_TIMEOUT_ATTR, 1000) // per-route connection timeout
                        // similar to httpclient in application.yml
                        .uri("lb://LOANS")
                )
                .route(p -> p
                        .path("/jkbank/cards/**")
                        .filters(f -> f.rewritePath("/jkbank/cards/(?<segment>.*)", "/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
                        .uri("lb://CARDS"))
                .build();
    }
}
