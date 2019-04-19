package test.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import test.entities.Account;
import test.helpers.strings.RandomStringGenerator;
import test.models.AccountResponse;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import test.models.AccountRequest;
import test.repositories.AccountRepository;
import test.services.impl.AccountService;

@DataJpaTest
@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringRunner.class)
@PrepareForTest({RandomStringGenerator.class})
public class AccountIntegrationTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AccountRepository accountRepository;

    private IAccountService accountService;

    @Before
    public void init() {
        accountService = new AccountService(accountRepository);
        PowerMockito.mockStatic(RandomStringGenerator.class); // mock the static method within RandomStringGenerator class
    }

    // Tests the 'Add/register new account' use-case
    @Test
    public void AddTest() {
        when(RandomStringGenerator.generate()).thenReturn("generatedTestPw");

        Account acc1 = new Account("testId1", RandomStringGenerator.generate());

        entityManager.persist(acc1);
        entityManager.flush();

        Account newAccount = new Account("testId2", RandomStringGenerator.generate());
        AccountRequest request = new AccountRequest(newAccount.getAccountId());

        AccountResponse expected = new AccountResponse(true,
                "Your account is opened",
                HttpStatus.OK,
                newAccount.getPassword(),
                newAccount.getToken());

        assertEquals(expected, accountService.add(request));
    }

    // Tests the 'Add' use-case for an existing account ID
    @Test
    public void AddExistingTest() {
        AccountRequest request = new AccountRequest("testId");
        Account newAccount = new Account(request.getAccountId(), RandomStringGenerator.generate());

        entityManager.persist(newAccount);
        entityManager.flush();

        AccountResponse expected = new AccountResponse(false, "Account with that ID already exists", HttpStatus.BAD_REQUEST);

        assertEquals(expected, accountService.add(request));
    }
}
