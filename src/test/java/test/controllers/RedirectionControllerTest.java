package test.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import test.entities.Url;
import test.services.IAccountService;
import test.services.IAuthService;
import test.services.IUrlService;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@RunWith(SpringRunner.class)
public class RedirectionControllerTest {
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

    // Tests the /{shortUrl} get endpoint for redirection
    @Test
    public void GetAllAccountsTest() throws Exception {
        Url url = new Url("http://test1.com", "test1", null);

        given(urlService.getByShortUrl(url.getShortUrlString())).willReturn(url);
        this.mockMvc.perform(get("/" + url.getShortUrlString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().exists("Location"));

        given(urlService.getByShortUrl(url.getShortUrlString())).willReturn(null);
        this.mockMvc.perform(get("/" + url.getShortUrlString()))
                .andExpect(status().isNotFound());
    }
}
