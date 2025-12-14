package com.jkbank.accounts.service.impl;

import com.jkbank.accounts.constants.AccountsConstants;
import com.jkbank.accounts.dto.CustomerDto;
import com.jkbank.accounts.entity.Accounts;
import com.jkbank.accounts.entity.Customer;
import com.jkbank.accounts.mapper.CustomerMapper;
import com.jkbank.accounts.repository.AccountsRepository;
import com.jkbank.accounts.repository.CustomerRepository;
import com.jkbank.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountsService {

    private AccountsRepository accountsRepository; // autowired automatically by Spring-@AllArgsConstructor since we have a need for single constructor only
    private CustomerRepository customerRepository;
    /**
     * Create a new account for the given customer.
     *
     * @param customerDto - the customer data transfer object containing customer details
     */
    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));
    }

    /**
     * Create a new account for the given customer.
     * @param customer - the customer entity for whom the account is to be created
     * @return the newly created Accounts object
     */
    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);
        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        return newAccount;
    }
}
