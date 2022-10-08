package net.veramendi.cashflowapi.controller;

import net.veramendi.cashflowapi.domain.Account;
import net.veramendi.cashflowapi.domain.Transaction;
import net.veramendi.cashflowapi.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/{accountNumber}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createTransaction(@PathVariable String accountNumber, @RequestBody Transaction transaction) {
        Transaction createdTransaction = this.transactionService.create(accountNumber, transaction);

        return new ResponseEntity(createdTransaction, HttpStatus.CREATED);
    }
}
