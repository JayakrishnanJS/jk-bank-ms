package com.jkbank.accounts.service;

import com.jkbank.accounts.dto.CustomerDto;

public interface IAccountsService {

    /**
     * Create a new account for the given customer.
     *
     * @param customerDto the customer data transfer object containing customer details
     */
    void createAccount(CustomerDto customerDto);


    /**
     * Fetch account details based on mobile number.
     *
     * @param mobileNumber the mobile number of the customer
     * @return the customer data transfer object containing customer details
     */
    CustomerDto fetchAccount(String mobileNumber);
}
