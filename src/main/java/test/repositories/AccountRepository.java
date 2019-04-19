package test.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import test.entities.Account;

public interface AccountRepository extends JpaRepository<Account, String> {
}
