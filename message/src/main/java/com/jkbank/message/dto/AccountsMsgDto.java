package com.jkbank.message.dto;

/**
 * Data Transfer Object representing account information.
 * @param accountNumber the unique account number
 * @param name          the name of the account holder
 * @param email         the email address of the account holder
 * @param mobileNumber  the mobile number of the account holder
 */
public record AccountsMsgDto(Long accountNumber, String name, String email, String mobileNumber) {

}
