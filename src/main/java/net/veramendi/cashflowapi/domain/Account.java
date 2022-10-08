package net.veramendi.cashflowapi.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    private String accountNumber;

    private String clientName;

    private double currentBalance;

    private AccountStatus status;

    private LocalDateTime createdDate;

    @JsonBackReference
    @OneToMany(
            mappedBy = "account",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Transaction> transactions;
}
