package test.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import test.entities.Account;
import test.entities.Url;
import test.services.IAccountService;
import test.services.IAuthService;
import test.services.IUrlService;

import java.util.*;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@RunWith(SpringRunner.class)
public class UrlControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    @Qualifier("urlService")
    private IUrlService urlService;

    @MockBean
    @Qualifier("accService")
    private IAccountService accountService;

    @MockBean
    @Qualifier("basicAuthService")
    private IAuthService authService;

    // Tests the /statistic/{accountId} get endpoint (Unauthorized)
    @Test
    public void GetUrlStatisticsUnauthorizedTest() throws Exception {
        given(authService.authenticate("")).willReturn(null);

        this.mockMvc.perform(get("/statistic/someAccountId"))
                .andExpect(status().isUnauthorized());
    }

    // Tests the /statistic/{accountId} get endpoint
    @Test
    public void GetUrlStatisticsTest() throws Exception {
        Account acc = new Account("testAccount", "testAccountPw");
        Url url = new Url("http://test.com", "test0", acc);

        String testToken = "Basic testToken";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", testToken);

        Map<String, Integer> urlMap = new HashMap<String, Integer>() {{
            put(url.getUrlString(), url.getNumberOfRedirects());
        }};

        given(authService.authenticate(testToken)).willReturn(acc);
        given(urlService.getByAccountId(acc.getAccountId())).willReturn(urlMap);

        String expectedJson = String.format("{'%s': %s}", url.getUrlString(), url.getNumberOfRedirects());

        this.mockMvc.perform(
                get("/statistic/" + acc.getAccountId())
                .headers(httpHeaders)
            )
            .andExpect(status().isOk())
            .andExpect(content().json(expectedJson));
    }
}
