package com.jkbank.accounts.service;

import com.jkbank.accounts.dto.CustomerDto;

public interface IAccountsService {

    /**
     * Create a new account for the given customer.
     *
     * @param customerDto the customer data transfer object containing customer details
     */
    void createAccount(CustomerDto customerDto);
}
