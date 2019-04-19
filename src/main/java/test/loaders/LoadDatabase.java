package test.loaders;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;
import test.helpers.strings.RandomStringGenerator;
import test.repositories.AccountRepository;
import test.repositories.UrlRepository;
import test.entities.Account;
import test.entities.Url;

@Configuration
public class LoadDatabase {
    @Bean
    CommandLineRunner initData(UrlRepository urlRepo, AccountRepository accountRepo) {
        return args -> {
            Account acc1 = accountRepo.save(new Account("eldarj", RandomStringGenerator.generate()));
            Account acc2 = accountRepo.save(new Account("myuserid", RandomStringGenerator.generate()));
            urlRepo.save(new Url("https://www.microsoft.com", RandomStringGenerator.generate(), acc1));
            urlRepo.save(new Url("https://www.java.com", RandomStringGenerator.generate(), acc1));
            urlRepo.save(new Url("https://www.infobip.com", RandomStringGenerator.generate(), acc1));
            urlRepo.save(new Url("https://www.google.com", RandomStringGenerator.generate(), acc2));
        };
    }
}
