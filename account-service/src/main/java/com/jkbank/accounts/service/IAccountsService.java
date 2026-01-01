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

    /**
     * Update account details for the given customer.
     *
     * @param customerDto the customer data transfer object containing updated customer details
     * @return true if the update was successful, false otherwise
     */
    boolean updateAccount(CustomerDto customerDto);

    /**
     * Delete account based on mobile number.
     *
     * @param mobileNumber the mobile number of the customer
     * @return true if the deletion was successful, false otherwise
     */
    boolean deleteAccount(String mobileNumber);

    /**
     * Update communication status for the given account number.
     *
     * @param accountNumber the account number of the customer
     * @return true if the update was successful, false otherwise
     */
    boolean updateCommunicationStatus(Long accountNumber);
}
