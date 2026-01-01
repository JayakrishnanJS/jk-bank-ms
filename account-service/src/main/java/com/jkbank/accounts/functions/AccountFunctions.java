package com.jkbank.accounts.functions;

import com.jkbank.accounts.service.IAccountsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class AccountFunctions {

    private static final Logger log = LoggerFactory.getLogger(AccountFunctions.class.getName());

    @Bean
    public Consumer<Long> updateCommunication(IAccountsService iAccountsService){
        return accountNumber -> {
            log.info("Updating communication status for account number: " + accountNumber.toString());
            iAccountsService.updateCommunicationStatus(accountNumber);
        };
    }
}
