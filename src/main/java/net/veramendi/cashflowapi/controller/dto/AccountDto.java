package net.veramendi.cashflowapi.controller.dto;

import lombok.Data;
import net.veramendi.cashflowapi.domain.Account;
import net.veramendi.cashflowapi.domain.AccountStatus;
import net.veramendi.cashflowapi.domain.Transaction;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class AccountDto {

    private Long id;

    private String accountNumber;

    private String clientName;

    private double currentBalance;

    private AccountStatus status;

    private LocalDateTime createdDate;

    private List<TransactionDto> transactions;

    public AccountDto(Account account) {
        this.id = account. getId();
        this.accountNumber = account.getAccountNumber();
        this.clientName = account.getClientName();
        this.currentBalance = account.getCurrentBalance();
        this.status = account.getStatus();
        this.createdDate = account.getCreatedDate();
    }
}
