package com.jkbank.accounts.service.client;

import com.jkbank.accounts.dto.CardsDto;
import com.jkbank.accounts.dto.LoansDto;
import jakarta.validation.constraints.Pattern;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("cards") // same name as in cards service -> application.properties -> spring.application.name
public interface CardsFeignClient {

    @GetMapping(value = "/api/fetch", consumes = "application/json") // same as in CardsController.java -> @RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE}) + @GetMapping("/fetch")
    public ResponseEntity<CardsDto> fetchCardDetails(@RequestParam String mobileNumber);
    // declaration must match with CardsController.java -> fetchCardDetails method
}
