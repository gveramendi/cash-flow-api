package net.veramendi.cashflowapi.controller;

import net.veramendi.cashflowapi.controller.dto.AccountDto;
import net.veramendi.cashflowapi.controller.dto.TransactionDto;
import net.veramendi.cashflowapi.domain.Account;
import net.veramendi.cashflowapi.domain.Transaction;
import net.veramendi.cashflowapi.service.AccountService;
import net.veramendi.cashflowapi.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    private final TransactionService transactionService;

    public AccountController(AccountService accountService, TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createAccount(@RequestBody Account account) {
        Account createdAccount = this.accountService.create(account);

        return new ResponseEntity(createdAccount, HttpStatus.CREATED);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAll() {
        List<Account> accounts = this.accountService.getAll();

        return new ResponseEntity(accounts, HttpStatus.OK);
    }

    @GetMapping("/{accountNumber}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAccountByAccountNumber(@PathVariable String accountNumber) {
        AccountDto account = new AccountDto(this.accountService.getByAccountNumber(accountNumber));
        List<TransactionDto> transactions = new ArrayList<>();
        for (Transaction transaction : this.transactionService.getTransactionsByAccountNumber(accountNumber)) {
            transactions.add(new TransactionDto(transaction));
        }
        account.setTransactions(transactions);

        return new ResponseEntity(account, HttpStatus.OK);
    }
}
