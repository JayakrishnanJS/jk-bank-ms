package com.jkbank.accounts.repository;

import com.jkbank.accounts.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> { // spring data jpa will provide implementation at runtime

    Optional<Customer> findByMobileNumber(String mobileNumber);
}
