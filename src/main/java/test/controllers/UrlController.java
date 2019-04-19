package test.controllers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

import test.helpers.http.ResponseHelper;
import test.models.base.ResponseModel;
import test.services.IAuthService;
import test.services.IUrlService;
import test.models.UrlRequest;
import test.entities.Account;

import java.util.Map;

@RestController
public class UrlController {
    private final IUrlService urlService;
    private final IAuthService authService;

    UrlController(@Qualifier("urlService") IUrlService urlService,
                  @Qualifier("basicAuthService") IAuthService authService) {
        this.urlService = urlService;
        this.authService = authService;
    }

    // 1.1 - b) Registration of URLs
    @PostMapping(path = "register", consumes = "application/json", produces = "application/json")
    ResponseEntity newUrl(@RequestHeader(value = "Authorization", required = false) String basicAuthHeader,
                          @RequestBody UrlRequest requestObj,
                          HttpServletRequest servletRequest) {
        Account authedAccount = authService.authenticate(basicAuthHeader);
        if(authedAccount != null) {
            ResponseModel urlResponse = urlService.add(requestObj, authedAccount, servletRequest);
            return ResponseEntity
                    .status(urlResponse.getHttpStatus())
                    .body(urlResponse);
        }
        return ResponseHelper.responseUnauthorized();
    }

    // 1.1 - c) Retrieval of statistics
    @GetMapping(path = "statistic/{accountId}", produces = "application/json")
    ResponseEntity getByAccount(@RequestHeader(value = "Authorization", required = false) String basicAuthHeader,
                                @PathVariable String accountId) {
        Account authedAccount = authService.authenticate(basicAuthHeader);
        if (authedAccount != null ){
            Map<String, Integer> byAccountId = urlService.getByAccountId(accountId);
            return ResponseEntity.ok(byAccountId);
        }
        return ResponseHelper.responseUnauthorized();
    }
}
