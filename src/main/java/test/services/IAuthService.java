package test.services;

import test.entities.Account;

public interface IAuthService {
    Account authenticate(String basicAuthHeader);
}
