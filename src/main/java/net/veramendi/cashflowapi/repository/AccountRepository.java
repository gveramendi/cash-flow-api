package net.veramendi.cashflowapi.repository;

import net.veramendi.cashflowapi.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findById(Long id);

    Optional<Account> findByAccountNumber(String accountNumber);
}
