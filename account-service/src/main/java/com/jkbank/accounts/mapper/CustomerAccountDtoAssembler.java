package com.jkbank.accounts.mapper;

import com.jkbank.accounts.dto.CustomerAccountDto;
import com.jkbank.accounts.entity.Accounts;
import com.jkbank.accounts.entity.Customer;

/*
 * Assembler class to create CustomerAccountDto from Customer and Accounts entities.
 * When to Use                      Each Approach                     Use When
 *  Separate Mappers                  2-3 entities                      SOLID focus
 *  Assembler                         4+ entities                       complex composition
 *  Service Composition               Simple cases                      existing pattern
 */

public class CustomerAccountDtoAssembler {
// here service composition is enough, but assembler pattern shown for handling complex cases
    /**
     * Assembles CustomerAccountDto from Customer and Accounts entities
     * @param customer Customer entity
     * @param accounts Accounts entity
     * @return Fully populated CustomerAccountDto
     */
    public static CustomerAccountDto assemble(Customer customer, Accounts accounts) {
        CustomerAccountDto customerAccountDto = new CustomerAccountDto();
        
        // Map customer fields
        customerAccountDto.setName(customer.getName());
        customerAccountDto.setEmail(customer.getEmail());
        customerAccountDto.setMobileNumber(customer.getMobileNumber());
        
        // Map account fields
        customerAccountDto.setAccountNumber(accounts.getAccountNumber());
        customerAccountDto.setAccountType(accounts.getAccountType());
        
        return customerAccountDto;
    }
}

/*
* Approach 1: Separate Mapper Method (Current Recommendation)
✅ Best for: Clear separation, SOLID principles
// AccountsMapper.java
public static CustomerAccountDto mapAccountFieldsToCustomerAccountDto(Accounts accounts, CustomerAccountDto dto) {
    dto.setAccountNumber(accounts.getAccountNumber());
    dto.setAccountType(accounts.getAccountType());
    return dto;
}
Pros:
Clean separation of concerns
Each mapper handles only its entity
Follows SRP/OCP strictly
Cons:
Slightly more verbose
Two mapper calls in service layer

Approach 2: Builder/Assembler Pattern
✅ Best for: Complex DTOs with many sources
// CustomerAccountDtoAssembler.java
public class CustomerAccountDtoAssembler {
    public static CustomerAccountDto assemble(Customer customer, Accounts accounts) {
        CustomerAccountDto dto = new CustomerAccountDto();
        dto.setName(customer.getName());
        dto.setEmail(customer.getEmail());
        dto.setMobileNumber(customer.getMobileNumber());
        dto.setAccountNumber(accounts.getAccountNumber());
        dto.setAccountType(accounts.getAccountType());
        return dto;
    }
}
Pros:
Single responsibility: composition logic
Clean service layer
Easy to extend with more entities
Cons:
Additional class to maintain
Duplicates some mapping logic

Approach 3: Service Layer Composition (Current Implementation)
✅ Best for: Simple scenarios, minimal overhead
// CustomerServiceImpl.java
CustomerAccountDto dto = CustomerMapper.mapToCustomerAccountDto(customer, new CustomerAccountDto());
AccountsMapper.mapAccountFieldsToCustomerAccountDto(accounts, dto);
Pros:
Clear orchestration in service
Mappers stay focused
Flexible composition
Cons:
Service layer knows about DTO structure
Multiple mapper calls
* */