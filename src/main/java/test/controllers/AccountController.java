package test.controllers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import test.models.base.ResponseModel;
import test.services.IAccountService;
import test.models.AccountRequest;
import test.entities.Account;
import java.util.List;

@RestController
public class AccountController {
    private final IAccountService accountService;

    public AccountController(@Qualifier("accService") IAccountService accountService) {
        this.accountService = accountService;
    }

    // 1.1 - a) Opening of accounts
    @PostMapping(path = "account", consumes = "application/json", produces = "application/json")
    ResponseEntity createAccount(@RequestBody AccountRequest obj) {
        ResponseModel accResponse = accountService.add(obj);
        return accResponse != null ?
            ResponseEntity
                .status(accResponse.getHttpStatus())
                .body(accResponse) :
            ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .build();
    }

    // Not requested but just list out all added users and their links..
    @GetMapping("/accounts")
    List<Account> getAllAccs() {
        return accountService.getAll();
    }


}
