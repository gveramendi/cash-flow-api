package net.veramendi.cashflowapi.controller;

import net.veramendi.cashflowapi.domain.Account;
import net.veramendi.cashflowapi.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
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
        Account account = this.accountService.getByAccountNumber(accountNumber);

        return new ResponseEntity(account, HttpStatus.OK);
    }
}
