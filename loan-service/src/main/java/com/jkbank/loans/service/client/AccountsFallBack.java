package com.jkbank.loans.service.client;

import com.jkbank.loans.dto.CustomerAccountDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class AccountsFallBack implements AccountsFeignClient {

    @Override
    public ResponseEntity<CustomerAccountDto> fetchCustomerAccountDetails(String correlationId, String mobileNumber) { // opposite
                                                                                                                       // order
                                                                                                                       // of
                                                                                                                       // method
                                                                                                                       // parameters
                                                                                                                       // in
                                                                                                                       // fetchCustomerDetails
                                                                                                                       // method
                                                                                                                       // in
                                                                                                                       // accounts
                                                                                                                       // service
        return null;
    }
}
