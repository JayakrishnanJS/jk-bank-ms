package com.jkbank.message.functions;

import com.jkbank.message.dto.AccountsMsgDto;
import com.jkbank.message.dto.LoansMsgDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class MessageFunctions {

    private static final Logger log = LoggerFactory.getLogger(MessageFunctions.class.getName());

    @Bean
    public Function<AccountsMsgDto, AccountsMsgDto> emailAccountDetails(){
        return accountsMsgDto -> {
          log.info("Sending email with the details: "+accountsMsgDto.toString());
          return accountsMsgDto;
        };
    }

    @Bean
    public Function<AccountsMsgDto, Long> smsAccountDetails(){
        return accountsMsgDto -> {
            log.info("Sending sms with the details: "+accountsMsgDto.toString());
            return accountsMsgDto.accountNumber();
        };
    }

    // --- Loans Functions ---
    @Bean
    public Function<LoansMsgDto, LoansMsgDto> emailLoanDetails() {
        return loansMsgDto -> {
            log.info("Sending email with the LOAN details: {}", loansMsgDto);
            return loansMsgDto;
        };
    }

    @Bean
    public Function<LoansMsgDto, Long> smsLoanDetails() {
        return loansMsgDto -> {
            log.info("Sending SMS with the LOAN details: {}", loansMsgDto);
            return Long.valueOf(loansMsgDto.loanNumber());
        };
    }
}

