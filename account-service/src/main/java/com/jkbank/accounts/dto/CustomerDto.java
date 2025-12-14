package com.jkbank.accounts.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDto {

    @NotEmpty(message = "Name cannot be null or empty") // Validation annotation to ensure the name is not empty
    @Size(min = 5, max = 30, message = "Name must be between 5 and 30 characters") // Validation annotation to enforce size constraints
    private String name;

    @NotEmpty(message = "Email cannot be null or empty") // Validation annotation to ensure the email is not empty
    @Email(message = "Email should be valid") // Validation annotation to ensure the email format is valid
    private String email;

    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") // Validation annotation to ensure the mobile number is exactly 10 digits
    private String mobileNumber;

    private AccountsDto accountsDto; // Composition: CustomerDto has an AccountsDto, we can also create separate DTOs for better modularity
}
