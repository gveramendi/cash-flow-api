package net.veramendi.cashflowapi.controller.dto;

import lombok.Data;
import net.veramendi.cashflowapi.domain.Transaction;
import net.veramendi.cashflowapi.domain.TransactionType;

import java.time.LocalDateTime;

@Data
public class TransactionDto {

    private Long id;

    private double amount;

    private String description;

    private LocalDateTime createdDate;

    private TransactionType type;

    public TransactionDto(Transaction transaction) {
        this.id = transaction.getId();
        this.amount = transaction.getAmount();
        this.description = transaction.getDescription();
        this.createdDate = transaction.getCreatedDate();
        this.type = transaction.getType();
    }
}
