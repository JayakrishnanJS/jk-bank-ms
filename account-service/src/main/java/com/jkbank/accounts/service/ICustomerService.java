package com.jkbank.accounts.service;

import com.jkbank.accounts.dto.CustomerAccountDto;
import com.jkbank.accounts.dto.CustomerDetailsDto;

public interface ICustomerService {

    /**
     * Fetch Customer Details by Mobile Number
     * @param mobileNumber
     * @return CustomerDetailsDto containing customer, account, loan, and card details
     */
    CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId);

    /**
     * Fetch Customer Account Details by Mobile Number
     * @param mobileNumber
     * @return CustomerAccountDto containing customer and account details
     */
    CustomerAccountDto fetchCustomerAccountDetails(String mobileNumber, String correlationId);
}
