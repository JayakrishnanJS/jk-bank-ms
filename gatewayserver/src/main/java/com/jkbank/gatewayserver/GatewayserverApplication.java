package com.jkbank.gatewayserver;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;

import org.springframework.cloud.client.circuitbreaker.Customizer;
import java.time.Duration;
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
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                .retry(retryConfig -> retryConfig.setRetries(3)
                                        // only retry for GET methods since other methods may not be idempotent
                                        .setMethods(HttpMethod.GET)
                                        .setBackoff(Duration.ofMillis(100), Duration.ofMillis(1000), 2, true)))
                        // http timeout settings
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

    // Resilience4J Circuit Breaker configuration used to set timeouts for all circuit breakers created in the factory
    // -> this may avoid timeout of CB between retries
    @Bean
    public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
                .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(4)).build()).build());
        // All circuit breakers created by this factory will have a timeout of 4 seconds
    }
}
