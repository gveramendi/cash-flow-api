package net.veramendi.cashflowapi.repository;

import net.veramendi.cashflowapi.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> getAllByAccountId(Long accountId);

}
