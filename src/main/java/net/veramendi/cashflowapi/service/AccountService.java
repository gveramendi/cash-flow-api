package net.veramendi.cashflowapi.service;

import lombok.extern.slf4j.Slf4j;
import net.veramendi.cashflowapi.domain.Account;
import net.veramendi.cashflowapi.domain.AccountStatus;
import net.veramendi.cashflowapi.domain.TransactionType;
import net.veramendi.cashflowapi.exception.ResourceFormatException;
import net.veramendi.cashflowapi.exception.ResourceNotFoundException;
import net.veramendi.cashflowapi.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account create(Account newAccount) {
        Account createdAccount;
        try {
            newAccount.setStatus(AccountStatus.ACTIVE);
            newAccount.setCurrentBalance(0d);
            newAccount.setCreatedDate(LocalDateTime.now());

            createdAccount = this.accountRepository.save(newAccount);
            log.info("Account with id " + createdAccount.getId() + " was created.");
        } catch (Exception exception) {
            log.error("Created account error: " + exception.getMessage());
            throw new ResourceFormatException("Created supplier error: " + exception.getMessage());
        }

        return createdAccount;
    }

    public Account get(Long supplierId) {
        Optional<Account> account = this.accountRepository.findById(supplierId);
        if (account.isPresent()) {
            return account.get();
        }

        log.warn("Account with id " + supplierId + " does not exist.");
        throw new ResourceNotFoundException("Account with id " + supplierId + " does not exist.");
    }

    public Account getByAccountNumber(String accountNumber) {
        Optional<Account> account = this.accountRepository.findByAccountNumber(accountNumber);
        if (account.isPresent()) {
            return account.get();
        }

        log.warn("Account " + accountNumber + " does not exist!");
        throw new ResourceNotFoundException("Account " + accountNumber + " does not exist.");
    }

    public List<Account> getAll() {
        return this.accountRepository.findAll();
    }

    public Account update(Account account, double amount, TransactionType transactionType) {
        Account updatedAccount;

        try {
            if (transactionType == TransactionType.DEPOSIT) {
                account.setCurrentBalance(account.getCurrentBalance() + amount);
            } else if (transactionType == TransactionType.WITHDRAW) {
                account.setCurrentBalance(account.getCurrentBalance() - amount);
            }

            account.setStatus((account.getCurrentBalance() > 0) ? AccountStatus.ACTIVE : AccountStatus.HOLD);

            updatedAccount = this.accountRepository.save(account);
        } catch (Exception exception) {
            log.error("Created account error: " + exception.getMessage());
            throw new ResourceFormatException("Created supplier error: " + exception.getMessage());
        }

        return updatedAccount;
    }
}
