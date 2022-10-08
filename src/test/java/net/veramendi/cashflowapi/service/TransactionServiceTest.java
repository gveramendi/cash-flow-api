package net.veramendi.cashflowapi.service;

import net.veramendi.cashflowapi.domain.Account;
import net.veramendi.cashflowapi.domain.AccountStatus;
import net.veramendi.cashflowapi.domain.Transaction;
import net.veramendi.cashflowapi.domain.TransactionType;
import net.veramendi.cashflowapi.exception.ResourceFormatException;
import net.veramendi.cashflowapi.repository.AccountRepository;
import net.veramendi.cashflowapi.repository.TransactionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TransactionServiceTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionService transactionService;

    private Account account;

    @BeforeEach
    public void setUp() {
        Account account = new Account();
        account.setAccountNumber("1-000-10-100");
        account.setClientName("Juan Perez");

        this.account = this.accountService.create(account);
    }

    @AfterEach
    public void tearDown() {
        this.transactionRepository.deleteAll();
        this.accountRepository.deleteAll();
    }

    @Test
    public void createTransactionTest() {
        // ===============================================
        // DEPOSIT => 100 => ACTIVE
        // ===============================================
        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.DEPOSIT);
        transaction.setAmount(100d);
        transaction.setDescription("First deposit");

        Transaction createdTransaction = this.transactionService.create(this.account.getAccountNumber(), transaction);
        Assertions.assertEquals(transaction.getId(), createdTransaction.getId());
        Assertions.assertEquals(transaction.getType(), createdTransaction.getType());
        Assertions.assertEquals(transaction.getAmount(), createdTransaction.getAmount());
        Assertions.assertEquals(transaction.getDescription(), createdTransaction.getDescription());

        List<Transaction> transactions = this.transactionService.getTransactionsByAccountId(this.account.getId());
        Assertions.assertEquals(1, transactions.size());

        this.account = this.accountService.get(this.account.getId());
        Assertions.assertEquals(100d, this.account.getCurrentBalance());
        Assertions.assertEquals(AccountStatus.ACTIVE, this.account.getStatus());

        // ===============================================
        // DEPOSIT => 300 => ACTIVE
        // ===============================================
        transaction = new Transaction();
        transaction.setType(TransactionType.DEPOSIT);
        transaction.setAmount(200d);
        transaction.setDescription("Second deposit");

        createdTransaction = this.transactionService.create(this.account.getAccountNumber(), transaction);
        Assertions.assertEquals(transaction.getId(), createdTransaction.getId());
        Assertions.assertEquals(transaction.getType(), createdTransaction.getType());
        Assertions.assertEquals(transaction.getAmount(), createdTransaction.getAmount());
        Assertions.assertEquals(transaction.getDescription(), createdTransaction.getDescription());

        transactions = this.transactionService.getTransactionsByAccountId(this.account.getId());
        Assertions.assertEquals(2, transactions.size());

        this.account = this.accountService.get(this.account.getId());
        Assertions.assertEquals(300d, this.account.getCurrentBalance());
        Assertions.assertEquals(AccountStatus.ACTIVE, this.account.getStatus());

        // ===============================================
        // WITHDRAW => 100 => ACTIVE
        // ===============================================
        transaction = new Transaction();
        transaction.setType(TransactionType.WITHDRAW);
        transaction.setAmount(200d);
        transaction.setDescription("First withdraw");

        createdTransaction = this.transactionService.create(this.account.getAccountNumber(), transaction);
        Assertions.assertEquals(transaction.getId(), createdTransaction.getId());
        Assertions.assertEquals(transaction.getType(), createdTransaction.getType());
        Assertions.assertEquals(transaction.getAmount(), createdTransaction.getAmount());
        Assertions.assertEquals(transaction.getDescription(), createdTransaction.getDescription());

        transactions = this.transactionService.getTransactionsByAccountId(this.account.getId());
        Assertions.assertEquals(3, transactions.size());

        this.account = this.accountService.get(this.account.getId());
        Assertions.assertEquals(100d, this.account.getCurrentBalance());
        Assertions.assertEquals(AccountStatus.ACTIVE, this.account.getStatus());

        // ===============================================
        // WITHDRAW => -100 => HOLD
        // ===============================================
        transaction = new Transaction();
        transaction.setType(TransactionType.WITHDRAW);
        transaction.setAmount(200d);
        transaction.setDescription("Second withdraw");

        createdTransaction = this.transactionService.create(this.account.getAccountNumber(), transaction);
        Assertions.assertEquals(transaction.getId(), createdTransaction.getId());
        Assertions.assertEquals(transaction.getType(), createdTransaction.getType());
        Assertions.assertEquals(transaction.getAmount(), createdTransaction.getAmount());
        Assertions.assertEquals(transaction.getDescription(), createdTransaction.getDescription());

        transactions = this.transactionService.getTransactionsByAccountId(this.account.getId());
        Assertions.assertEquals(4, transactions.size());

        this.account = this.accountService.get(this.account.getId());
        Assertions.assertEquals(-100d, this.account.getCurrentBalance());
        Assertions.assertEquals(AccountStatus.HOLD, this.account.getStatus());

        // ===============================================
        // ACCOUNT HOLD !!!
        // ===============================================
        Transaction finalTransaction = new Transaction();
        finalTransaction.setType(TransactionType.WITHDRAW);
        finalTransaction.setAmount(200d);
        finalTransaction.setDescription("Third withdraw");

        ResourceFormatException thrown = Assertions.assertThrows(
                ResourceFormatException.class,
                () -> this.transactionService.create(this.account.getAccountNumber(), finalTransaction),
                "Expected this.supplierService.update() to throw, but it didn't"
        );
        Assertions.assertEquals("Account 1-000-10-100 is HOLD.", thrown.getMessage());

        // ===============================================
        // DEPOSIT => 100 => ACTIVE
        // ===============================================
        transaction = new Transaction();
        transaction.setType(TransactionType.DEPOSIT);
        transaction.setAmount(200d);
        transaction.setDescription("Third deposit");

        createdTransaction = this.transactionService.create(this.account.getAccountNumber(), transaction);
        Assertions.assertEquals(transaction.getId(), createdTransaction.getId());
        Assertions.assertEquals(transaction.getType(), createdTransaction.getType());
        Assertions.assertEquals(transaction.getAmount(), createdTransaction.getAmount());
        Assertions.assertEquals(transaction.getDescription(), createdTransaction.getDescription());

        transactions = this.transactionService.getTransactionsByAccountId(this.account.getId());
        Assertions.assertEquals(5, transactions.size());

        this.account = this.accountService.get(this.account.getId());
        Assertions.assertEquals(100d, this.account.getCurrentBalance());
        Assertions.assertEquals(AccountStatus.ACTIVE, this.account.getStatus());
    }
}
