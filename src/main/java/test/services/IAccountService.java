package test.services;

import test.entities.Account;
import test.models.AccountRequest;
import test.models.AccountResponse;

import java.util.List;

public interface IAccountService {
    AccountResponse add(AccountRequest obj);

    List<Account> getAll();
}
