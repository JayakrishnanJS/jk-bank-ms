package com.jkbank.accounts.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditAwareImpl") // naming the bean to avoid confusion in case of multiple implementations
public class AuditAwareImpl implements AuditorAware<String> {

    /**
     * This method is used to get the current auditor (user) for auditing purposes.
     * @return An Optional containing the current auditor's identifier, or empty if not available.
     */
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("ACCOUNTS_MS");
    }
}
