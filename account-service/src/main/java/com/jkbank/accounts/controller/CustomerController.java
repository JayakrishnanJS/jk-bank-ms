package com.jkbank.accounts.controller;

import com.jkbank.accounts.dto.CustomerDetailsDto;
import com.jkbank.accounts.dto.ErrorResponseDto;
import com.jkbank.accounts.service.ICustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "REST APIs for Cusomers in JK Bank",
        description = "REST APIs in JK bank to fetch customer details"
)
@RestController
@RequiredArgsConstructor
@RequestMapping(path="/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated // @Valid required only where input is coming from the client
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    private final ICustomerService iCustomerService; // dependency injection of service layer interface

    @Operation(
            summary = "Fetch Customer Details REST API",
            description = "Fetch customer details in JK Bank based on customer mobile number"
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "200",
                            description = "HTTP Status OK"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "HTTP Status INTERNAL SERVER ERROR",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDto.class)
                            )
                    )
            }
    )
    @GetMapping("/fetchCustomerDetails")
    public ResponseEntity<CustomerDetailsDto> fetchCustomerDetails(@RequestHeader("jkbank-correlation-id")
                                                                   String correlationId,
                                                                   @RequestParam
                                                                   @Pattern(regexp = "([0-9]{10})", message = "Mobile number must be 10 digits")
                                                                   String mobileNumber) {
        //logger.debug("jkbank-correlation-id found: {} ", correlationId);
        logger.debug("fetchCustomerDetails method execution started for mobile number: {} ", mobileNumber);
        CustomerDetailsDto customerDetailsDto = iCustomerService.fetchCustomerDetails(mobileNumber, correlationId);
        logger.debug("fetchCustomerDetails method execution completed for mobile number: {} ", mobileNumber);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerDetailsDto);
    }
}
