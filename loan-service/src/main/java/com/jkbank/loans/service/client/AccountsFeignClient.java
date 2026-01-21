package com.jkbank.loans.service.client;

import com.jkbank.loans.dto.CustomerAccountDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "accounts", fallback = AccountsFallBack.class) // same name as in accounts service -> application.properties -> spring.application.name
public interface AccountsFeignClient {

    @GetMapping(value = "/api/fetchCustomerAccountDetails", consumes = "application/json")
    public ResponseEntity<CustomerAccountDto> fetchCustomerAccountDetails(@RequestHeader("jkbank-correlation-id")
                                                         String correlationId,
                                                                   @RequestParam String mobileNumber);
}
