package com.jkbank.loans.service.impl;

import com.jkbank.loans.constants.LoansConstants;
import com.jkbank.loans.dto.CustomerAccountDto;
import com.jkbank.loans.dto.LoansDto;
import com.jkbank.loans.dto.LoansMsgDto;
import com.jkbank.loans.entity.Loans;
import com.jkbank.loans.exception.LoanAlreadyExistsException;
import com.jkbank.loans.exception.ResourceNotFoundException;
import com.jkbank.loans.mapper.LoansMapper;
import com.jkbank.loans.repository.LoansRepository;
import com.jkbank.loans.service.ILoansService;
import com.jkbank.loans.service.client.AccountsFeignClient;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class LoansServiceImpl implements ILoansService {

    private static final Logger log = LoggerFactory.getLogger(LoansServiceImpl.class);

    private LoansRepository loansRepository;

    private AccountsFeignClient accountsFeignClient;

    private final StreamBridge streamBridge; // autowired - to publish events to Kafka topics

    /**
     * @param mobileNumber - Mobile Number of the Customer
     */
    @Override
    public void createLoan(String mobileNumber, String correlationId) {
        Optional<Loans> optionalLoans= loansRepository.findByMobileNumber(mobileNumber);
        if(optionalLoans.isPresent()){
            throw new LoanAlreadyExistsException("Loan already registered with given mobileNumber "+mobileNumber);
        }

        Loans loan = loansRepository.save(createNewLoan(mobileNumber));
        LoansMapper.mapToLoansDto(loan, new LoansDto());

        // Fetch customer account details using AccountsFeignClient
        ResponseEntity<CustomerAccountDto> customerAccountDtoResponseEntity = accountsFeignClient.fetchCustomerAccountDetails(correlationId, mobileNumber);
        System.out.println("customerAccountDtoResponseEntity: " + customerAccountDtoResponseEntity);
        log.info("customerAccountDtoResponseEntity: {}", customerAccountDtoResponseEntity.getBody());
        if(customerAccountDtoResponseEntity.getBody() != null) {
            CustomerAccountDto customerAccountDto = customerAccountDtoResponseEntity.getBody();

            // Trigger communication event after successful loan creation
            sendCommunication(loan, customerAccountDto);
        }
    }

    private void sendCommunication(Loans loan, CustomerAccountDto customerAccountDto) {
        try {
            var loansMsgDto = new LoansMsgDto(
                    customerAccountDto.getName(),
                    customerAccountDto.getAccountNumber(),
                    customerAccountDto.getAccountType(),
                    customerAccountDto.getMobileNumber(),
                    loan.getLoanNumber(),
                    loan.getLoanType(),
                    loan.getTotalLoan()
            ); // var -> reduce boilerplate when the type is obvious from the right-hand side
            log.info("Sending Communication request for loan: {}", loan.getLoanNumber());
            var result = streamBridge.send("sendCommunication-out-0", loansMsgDto);
            log.info("Communication request triggered: {}", result);
        } catch (Exception e) {
            log.error("Failed to send communication for loan: {}", loan.getLoanNumber(), e);
            // Don't throw exception - loan already created, communication is secondary
        }
    }

    /**
     * @param mobileNumber - Mobile Number of the Customer
     * @return the new loan details
     */
    private Loans createNewLoan(String mobileNumber) {
        Loans newLoan = new Loans();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(LoansConstants.HOME_LOAN);
        newLoan.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);
        return newLoan;
    }

    /**
     *
     * @param mobileNumber - Input mobile Number
     * @return Loan Details based on a given mobileNumber
     */
    @Override
    public LoansDto fetchLoan(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
        );
        return LoansMapper.mapToLoansDto(loans, new LoansDto());
    }

    /**
     *
     * @param loansDto - LoansDto Object
     * @return boolean indicating if the update of loan details is successful or not
     */
    @Override
    public boolean updateLoan(LoansDto loansDto) {
        Loans loans = loansRepository.findByLoanNumber(loansDto.getLoanNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "LoanNumber", loansDto.getLoanNumber()));
        LoansMapper.mapToLoans(loansDto, loans);
        loansRepository.save(loans);
        return  true;
    }

    /**
     * @param mobileNumber - Input MobileNumber
     * @return boolean indicating if the delete of loan details is successful or not
     */
    @Override
    public boolean deleteLoan(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
        );
        loansRepository.deleteById(loans.getLoanId());
        return true;
    }

    /**
     * Update communication status for the given account number.
     *
     * @param loanNumber the account number of the customer
     * @return true if the update was successful, false otherwise
     */
    @Override
    public boolean updateCommunicationStatus(Long loanNumber) {
        boolean isUpdated = false;
        if(loanNumber != null) {
            Loans loans = loansRepository.findByLoanNumber(loanNumber.toString()).orElseThrow(
                    () -> new ResourceNotFoundException("Loan", "LoanNumber", loanNumber.toString())
            );
            loans.setCommunicationSwitch(true);
            loansRepository.save(loans);
            isUpdated = true;
        }
        return isUpdated;
    }
}
