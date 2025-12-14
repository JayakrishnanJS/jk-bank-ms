package com.jkbank.accounts.repository;

import com.jkbank.accounts.entity.Accounts;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Long> {

    Optional<Accounts> findByCustomerId(Long customerId);

    @Transactional // to manage transaction boundaries during delete/ update operation
    @Modifying // to let spring data jpa know that this is a modifying query
    void deleteByCustomerId(Long customerId); // custom method to delete account by customerId
}
