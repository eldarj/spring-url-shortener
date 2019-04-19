package test.services.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import test.entities.Account;
import test.repositories.AccountRepository;
import test.services.IAuthService;

import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Qualifier("basicAuthService")
public class AuthService  implements IAuthService {

    private final AccountRepository accountRepo;

    public AuthService(AccountRepository accountRepo) {
        this.accountRepo = accountRepo;
    }

    @Override
    public Account authenticate(String basicAuthHeader) {
        try {
            return accountRepo.findAll().stream()
                    .filter(a -> Objects.equals(a.getToken(), basicAuthHeader.replace("Basic ", "")))
                    .collect(Collectors.toList())
                    .get(0); // hendlaj u slucaju da je elemenata > 1, extend repository ili .collect() ??
        } catch (Exception e) {
            //
            return null;
        }
    }
}
