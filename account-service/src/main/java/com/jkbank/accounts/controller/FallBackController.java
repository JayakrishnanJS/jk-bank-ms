package com.jkbank.accounts.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallBackController {

    @RequestMapping("/contactSupport")
    public Mono<String> contactSupport() { // Fallback method for handling service failures
        return Mono.just("An error occurred. Please try after some time or contact support team!"); // returns a reactive Mono with a support message
    }
}
