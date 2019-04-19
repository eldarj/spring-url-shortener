package test.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import test.entities.Account;
import test.entities.Url;
import test.repositories.UrlRepository;
import test.services.impl.UrlService;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@DataJpaTest
@RunWith(SpringRunner.class)
public class UrlIntegrationTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UrlRepository urlRepository;

    private IUrlService urlService;

    private Account testAccount;
    private Url testUrl;
    private List<Url> testUrlList;

    @Before
    public void init() {
        urlService = new UrlService(urlRepository);

        // -- Prepare data --
        testAccount = new Account("testAccount", "testAccount");
        testUrl = new Url("http://test1.com", "test1", testAccount);
        Url testUrl2 = new Url("http://test2.com", "test2", testAccount);

        testUrlList = Arrays.asList(testUrl, testUrl2);

        entityManager.persist(testAccount);
        for(Url url : testUrlList) {
            entityManager.persist(url);
        };

        entityManager.flush();
    }

    // Tests the 'Get by account id' service/repo method
    @Test
    public void GetByAccountIdTest() {
        Map<String, Integer> expectedMap = testUrlList.stream()
                .collect(Collectors.toMap(Url::getUrlString, Url::getNumberOfRedirects));

        assertThat(urlService.getByAccountId(testAccount.getAccountId()), is(expectedMap));
        assertThat(urlService.getByAccountId("unexistingAccountId").size(), is(0));
    }

    // Tests the 'Get by short url' service/repo method
    @Test
    public void GetByShortUrlTest() {
        //Url expected = testUrl;
        assertEquals(testUrl, urlService.getByShortUrl(testUrl.getShortUrlString()));
        assertNull(urlService.getByShortUrl("unexistingShortUrl"));
    }
}
