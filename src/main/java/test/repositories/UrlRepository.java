package test.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import test.entities.Url;

public interface UrlRepository extends JpaRepository<Url, Long> {
}
