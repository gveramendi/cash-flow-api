package net.veramendi.cashflowapi.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    private double amount;

    private String description;

    private LocalDateTime createdDate;

    private TransactionType type;
}
