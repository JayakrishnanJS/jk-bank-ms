package com.jkbank.cards.controller;

import com.jkbank.cards.constants.CardsConstants;
import com.jkbank.cards.dto.ResponseDto;
import com.jkbank.cards.service.ICardsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class CardsController {

    private ICardsService icardsService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createCard(@RequestParam String mobileNumber) {
        icardsService.createCard(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(CardsConstants.STATUS_201, CardsConstants.MESSAGE_201));
    }
}
