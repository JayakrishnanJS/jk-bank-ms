package com.jkbank.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data // This annotation generates getters, setters, toString, equals, and hashCode methods, not used in entity classes
@Schema(
        name = "Accounts",
        description = "Schema to hold account information"
)
public class AccountsDto {

    @Schema(
            description = "Account number of the customer",
            example = "1234567890"
    )
    @Pattern(regexp = "(^[0-9]{10})", message = "Account number must be 10 digits") // Validation annotation to ensure the account number is exactly 10 digits)
    private Long accountNumber;

    @Schema(
            description = "Account type of the customer",
            example = "Savings"
    )
    @NotEmpty(message = "Account type cannot be null or empty") // Validation annotation to ensure the account type is not empty
    private String accountType;

    @Schema(
            description = "Branch address of the bank where the customer holds the account",
            example = "123, NewYork"
    )
    @NotEmpty(message = "Branch address cannot be null or empty") // Validation annotation to ensure the branch address is not empty
    private String branchAddress;
}
