package test.services.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import test.entities.Account;
import test.helpers.strings.RandomStringGenerator;
import test.models.AccountRequest;
import test.models.AccountResponse;
import test.repositories.AccountRepository;
import test.services.IAccountService;

import java.util.List;

@Service
@Qualifier("accService")
public class AccountService implements IAccountService {
    private final static String SUCCESS_DESC = "Your account is opened";
    private final static String FAIL_DESC = "Account with that ID already exists";
    private final static String ID_REQ_DESC = "accountId is mandatory";

    private final AccountRepository accountRepo;

    public AccountService(AccountRepository accountRepo){
        this.accountRepo = accountRepo;
    }

    @Override
    public AccountResponse add(AccountRequest obj)
    {
        if (obj.getAccountId() != null && !obj.getAccountId().trim().isEmpty()) {
            if (accountRepo.existsById(obj.getAccountId())) {
                return new AccountResponse(false, FAIL_DESC, HttpStatus.BAD_REQUEST);
            }

            Account newAcc = new Account(obj.getAccountId(), RandomStringGenerator.generate());
            accountRepo.save(newAcc);
            return new AccountResponse(true, SUCCESS_DESC, HttpStatus.OK, newAcc.getPassword(), newAcc.getToken());
        }

        return new AccountResponse(false, ID_REQ_DESC, HttpStatus.BAD_REQUEST);
    }

    @Override
    public List<Account> getAll(){
        return accountRepo.findAll();
    }
}
