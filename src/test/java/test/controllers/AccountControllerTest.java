package test.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import test.entities.Account;
import test.entities.Url;
import test.models.AccountRequest;
import test.models.AccountResponse;
import test.services.IAccountService;
import test.services.IAuthService;
import test.services.IUrlService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@RunWith(SpringRunner.class)
public class AccountControllerTest {
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

    // Tests the /accounts get endpoint
    @Test
    public void GetAllAccountsTest() throws Exception {
        Account acc = new Account("testAccount", "");
        Account acc2 = new Account("testAccount", "");

        Url url1 = new Url("http://test1.com", "test1", acc);
        Url url2 = new Url("http://test2.com", "test2", acc);

        acc.setAddedUrls(Arrays.asList(url1, url2));
        List<Account> accounts = Arrays.asList(acc, acc2);

        given(accountService.getAll()).willReturn(accounts);

        ObjectMapper mapper = new ObjectMapper();
        String expectedJson = mapper.writeValueAsString(accounts);

        this.mockMvc.perform(get("/accounts"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    // Tests the /account post endpoint
    @Test
    public void CreateNewAccountTest()  throws Exception {
        AccountRequest accRequest = new AccountRequest("testAccount");
        AccountResponse accResponse = new AccountResponse(true, "test description", HttpStatus.OK);

        given(accountService.add(accRequest)).willReturn(accResponse);

        ObjectMapper mapper = new ObjectMapper();

        this.mockMvc.perform(
                post("/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(mapper.writeValueAsString(accRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(accResponse)));
    }
}
