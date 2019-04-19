package test.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import test.entities.Account;
import test.repositories.AccountRepository;
import test.services.impl.AuthService;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@DataJpaTest
@RunWith(SpringRunner.class)
public class AuthIntegrationTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AccountRepository accountRepository;

    private IAuthService authService;

    @Before
    public void init() {
        authService = new AuthService(accountRepository);
    }

    // Tests the 'Authenticate account by token' use-case
    @Test
    public void AuthenticateTest() {
        Account acc1 = new Account("testAcc1", "testAcc1");
        Account acc2 = new Account("testAcc2", "testAcc2");
        List<Account> accounts = Arrays.asList(acc1, acc2);
        String basicToken = "Basic " + acc1.getToken();

        entityManager.persist(acc1);
        entityManager.persist(acc2);
        entityManager.flush();

        //Account expected = acc1;
        assertEquals(acc1, authService.authenticate(basicToken));
        assertNull(authService.authenticate("unexistingToken"));
    }
}
