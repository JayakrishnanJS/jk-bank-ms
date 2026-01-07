package com.jkbank.accounts.service.impl;

import com.jkbank.accounts.dto.*;
import com.jkbank.accounts.entity.Accounts;
import com.jkbank.accounts.entity.Customer;
import com.jkbank.accounts.exception.ResourceNotFoundException;
import com.jkbank.accounts.mapper.AccountsMapper;
import com.jkbank.accounts.mapper.CustomerAccountDtoAssembler;
import com.jkbank.accounts.mapper.CustomerMapper;
import com.jkbank.accounts.repository.AccountsRepository;
import com.jkbank.accounts.repository.CustomerRepository;
import com.jkbank.accounts.service.ICustomerService;
import com.jkbank.accounts.service.client.CardsFeignClient;
import com.jkbank.accounts.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    private CardsFeignClient cardsFeignClient;
    private LoansFeignClient loansFeignClient;

    /**
     * Fetch Customer Details by Mobile Number
     * @param mobileNumber
     * @return CustomerDetailsDto containing customer, account, loan, and card details
     */
    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );
        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

        // Fetch loan details using LoansFeignClient
        // add correlationId as first parameter to propagate it to downstream service
        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(correlationId, mobileNumber);
        if(null != loansDtoResponseEntity) {
            customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody()); // getBody() returns the LoansDto object
        }
        // Fetch card details using CardsFeignClient
        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCardDetails(correlationId, mobileNumber);
        if(null != cardsDtoResponseEntity) {
            customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody()); // getBody() returns the LoansDto object
        }
        return customerDetailsDto;
    }

    /**
     * Fetch Customer Account Details by Mobile Number
     * @param mobileNumber
     * @return CustomerAccountDto containing customer and account details
     */
    @Override
    public CustomerAccountDto fetchCustomerAccountDetails(String mobileNumber, String correlationId) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );

        // Use assembler to create CustomerAccountDto
        CustomerAccountDto customerAccountDto = CustomerAccountDtoAssembler.assemble(customer, accounts);
        return customerAccountDto;
    }
}
