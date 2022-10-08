package net.veramendi.cashflowapi.service;

import net.veramendi.cashflowapi.domain.Account;
import net.veramendi.cashflowapi.repository.AccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AccountServiceTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
        this.accountRepository.deleteAll();
    }

    @Test
    public void createAccountTest() {
        Account account = new Account();
        account.setAccountNumber("1-000-10-100");
        account.setClientName("Juan Perez");

        Account createdAccount = this.accountService.create(account);
        Assertions.assertEquals(account.getId(), createdAccount.getId());
        Assertions.assertEquals(account.getAccountNumber(), createdAccount.getAccountNumber());
        Assertions.assertEquals(account.getClientName(), createdAccount.getClientName());
        Assertions.assertEquals(account.getCurrentBalance(), createdAccount.getCurrentBalance());
        Assertions.assertEquals(account.getStatus(), createdAccount.getStatus());
    }
}
