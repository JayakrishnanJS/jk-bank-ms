package com.jkbank.accounts.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data // This annotation generates getters, setters, toString, equals, and hashCode methods, not used in entity classes
public class AccountsDto {

    @Pattern(regexp = "(^$|[0-9]{10})", message = "Account number must be 10 digits") // Validation annotation to ensure the account number is exactly 10 digits)
    private Long accountNumber;

    @NotEmpty(message = "Account type cannot be null or empty") // Validation annotation to ensure the account type is not empty
    private String accountType;

    @NotEmpty(message = "Branch address cannot be null or empty") // Validation annotation to ensure the branch address is not empty
    private String branchAddress;
}
