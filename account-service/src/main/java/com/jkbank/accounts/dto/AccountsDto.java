package com.jkbank.accounts.dto;

import lombok.Data;

@Data // This annotation generates getters, setters, toString, equals, and hashCode methods, not used in entity classes
public class AccountsDto {

    private Long accountNumber;

    private String accountType;

    private String branchAddress;
}
