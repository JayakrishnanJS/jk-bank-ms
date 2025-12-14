package com.jkbank.accounts.dto;

import lombok.Data;

@Data
public class CustomerDto {

    private String name;

    private String email;

    private String mobileNumber;

    private AccountsDto accountsDto; // Composition: CustomerDto has an AccountsDto, we can also create separate DTOs for better modularity
}
