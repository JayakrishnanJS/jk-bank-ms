package com.jkbank.accounts.service.impl;

import com.jkbank.accounts.constants.AccountsConstants;
import com.jkbank.accounts.dto.AccountsDto;
import com.jkbank.accounts.dto.AccountsMsgDto;
import com.jkbank.accounts.dto.CustomerDto;
import com.jkbank.accounts.entity.Accounts;
import com.jkbank.accounts.entity.Customer;
import com.jkbank.accounts.exception.CustomerAlreadyExistsException;
import com.jkbank.accounts.exception.ResourceNotFoundException;
import com.jkbank.accounts.mapper.AccountsMapper;
import com.jkbank.accounts.mapper.CustomerMapper;
import com.jkbank.accounts.repository.AccountsRepository;
import com.jkbank.accounts.repository.CustomerRepository;
import com.jkbank.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountsService {

    private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

    private AccountsRepository accountsRepository; // autowired automatically by Spring-@AllArgsConstructor since we have a need for single constructor only
    private CustomerRepository customerRepository;
    private final StreamBridge streamBridge; // autowired - to publish events to Kafka topics
    /**
     * Create a new account for the given customer.
     * @param customerDto - the customer data transfer object containing customer details
     */
    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if(optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already exists with given mobile number: " + customerDto.getMobileNumber());
        }
        Customer savedCustomer = customerRepository.save(customer);
        Accounts savedAccount = accountsRepository.save(createNewAccount(savedCustomer));
        // Trigger communication event after successful account creation
        sendCommunication(savedAccount, savedCustomer);
    }

    private void sendCommunication(Accounts account, Customer customer) {
        var accountsMsgDto = new AccountsMsgDto(account.getAccountNumber(), customer.getName(),
                customer.getEmail(), customer.getMobileNumber()); // var -> reduce boilerplate when the type is obvious from the right-hand side
        log.info("Sending Communication request for the details: {}", accountsMsgDto);
        var result = streamBridge.send("sendCommunication-out-0", accountsMsgDto);
        log.info("Is the Communication request successfully triggered ? : {}", result);
    }

    /**
     * Fetch account details based on mobile number.
     *
     * @param mobileNumber the mobile number of the customer
     * @return the customer data transfer object containing customer details
     */
    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));
        return customerDto;
    }

    /**
     * Update account details for the given customer.
     *
     * @param customerDto the customer data transfer object containing updated customer details
     * @return true if the update was successful, false otherwise
     */
    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if(accountsDto != null) {
            Accounts existingAccount = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString())
            );
            AccountsMapper.mapToAccounts(accountsDto, existingAccount); // update existingAccount with new values from accountsDto only if account exists
            accountsRepository.save(existingAccount);

            Long customerId = existingAccount.getCustomerId();
            Customer existingCustomer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString())
            );
            CustomerMapper.mapToCustomer(customerDto, existingCustomer);
            customerRepository.save(existingCustomer); // update existingCustomer based on primary key customerId
            isUpdated = true;
        }
        return isUpdated;
    }

    /**
     * Delete account based on mobile number.
     *
     * @param mobileNumber the mobile number of the customer
     * @return true if the deletion was successful, false otherwise
     */
    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        accountsRepository.deleteByCustomerId(customer.getCustomerId()); // delete account based on foreign key customerId
        customerRepository.deleteById(customer.getCustomerId()); // delete customer based on primary key customerId
        return true;
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
