package com.jkbank.loans.dto;

/**
 * Data Transfer Object representing loan information.
 * @param name          the name of the customer
 * @param accountNumber the account number associated with the loan
 * @param accountType   the type of the account
 * @param mobileNumber  the mobile number of the customer
 * @param loanNumber    the unique loan number
 * @param loanType      the type of the loan
 * @param totalLoan     the total loan amount
 */

// Using Java Record to create an immutable data carrier for loan information
public record LoansMsgDto(String name, Long accountNumber, String accountType, String mobileNumber, String loanNumber, String loanType, int totalLoan) {

}
