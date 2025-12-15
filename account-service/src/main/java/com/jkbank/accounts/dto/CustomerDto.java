package com.jkbank.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "Customer",
        description = "Schema to hold customer and account information"
)
public class CustomerDto {

    @Schema(
        description = "Name of the customer",
        example = "JK"
    )
    @NotEmpty(message = "Name cannot be null or empty") // Validation annotation to ensure the name is not empty
    @Size(min = 5, max = 30, message = "Name must be between 5 and 30 characters") // Validation annotation to enforce size constraints
    private String name;

    @Schema(
            description = "Email Id of the customer",
            example = "jk@jkbank.com"
    )
    @NotEmpty(message = "Email cannot be null or empty") // Validation annotation to ensure the email is not empty
    @Email(message = "Email should be valid") // Validation annotation to ensure the email format is valid
    private String email;

    @Schema(
            description = "Mobile number of the customer",
            example = "9999999999"
    )
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") // Validation annotation to ensure the mobile number is exactly 10 digits
    private String mobileNumber;

    @Schema(
            description = "Account details of the customer"
    )
    private AccountsDto accountsDto; // Composition: CustomerDto has an AccountsDto, we can also create separate DTOs for better modularity
}
