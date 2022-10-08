package net.veramendi.cashflowapi.service;

import lombok.extern.slf4j.Slf4j;
import net.veramendi.cashflowapi.domain.Account;
import net.veramendi.cashflowapi.domain.AccountStatus;
import net.veramendi.cashflowapi.domain.Transaction;
import net.veramendi.cashflowapi.domain.TransactionType;
import net.veramendi.cashflowapi.exception.ResourceFormatException;
import net.veramendi.cashflowapi.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    private final AccountService accountService;

    public TransactionService(TransactionRepository transactionRepository, AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
    }

    @Transactional
    public Transaction create(String accountNumber, Transaction newTransaction) {
        Account account = this.accountService.getByAccountNumber(accountNumber);
        Transaction transaction = null;

        if (newTransaction.getType() == TransactionType.DEPOSIT) {
            transaction = createTransaction(account, newTransaction);
        } else if (newTransaction.getType() == TransactionType.WITHDRAW) {
            if (account.getStatus() == AccountStatus.HOLD) {
                log.error("Account " + account.getAccountNumber() + " is HOLD.");
                throw new ResourceFormatException("Account " + account.getAccountNumber() + " is HOLD.");
            } else if (account.getStatus() == AccountStatus.ACTIVE) {
                transaction = createTransaction(account, newTransaction);
            }
        }

        return transaction;
    }

    public List<Transaction> getTransactionsByAccountId(Long accountId) {
        return this.transactionRepository.getAllByAccountId(accountId);
    }

    private Transaction createTransaction(Account account, Transaction newTransaction) {
        Transaction createdTransaction;
        try {
            this.accountService.update(account, newTransaction.getAmount(), newTransaction.getType());

            newTransaction.setAccount(account);
            newTransaction.setCreatedDate(LocalDateTime.now());
            createdTransaction = this.transactionRepository.save(newTransaction);
            log.info("Transaction with id " + createdTransaction.getId() + " was created.");
        } catch (Exception exception) {
            log.error("Created transaction error: " + exception.getMessage());
            throw new ResourceFormatException("Created transaction error: " + exception.getMessage());
        }

        return createdTransaction;
    }
}
