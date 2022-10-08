package net.veramendi.cashflowapi.controller;

import net.veramendi.cashflowapi.controller.dto.TransactionDto;
import net.veramendi.cashflowapi.domain.Account;
import net.veramendi.cashflowapi.domain.Transaction;
import net.veramendi.cashflowapi.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

        return new ResponseEntity(new TransactionDto(createdTransaction), HttpStatus.CREATED);
    }

    @GetMapping("/{accountNumber}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getTransactionsByAccountNumber(@PathVariable String accountNumber) {
        List<TransactionDto> transactions = new ArrayList<>();
        for (Transaction transaction : this.transactionService.getTransactionsByAccountNumber(accountNumber)) {
            transactions.add(new TransactionDto(transaction));
        }

        return new ResponseEntity(transactions, HttpStatus.OK);
    }
}
