package com.jkbank.loans.functions;

import com.jkbank.loans.service.ILoansService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

// Function class to handle loan-related functions for messaging systems like Kafka
@Configuration
public class LoanFunctions {

    private static final Logger log = LoggerFactory.getLogger(LoanFunctions.class.getName());

    // Function to update communication status for a loan account number
    @Bean
    public Consumer<Long> updateCommunication(ILoansService iLoansService){
        return loanNumber -> {
            log.info("Updating communication status for account number: " + loanNumber.toString());
            iLoansService.updateCommunicationStatus(loanNumber);
        };
    }
}
