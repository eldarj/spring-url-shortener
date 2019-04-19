package test.services;

import test.entities.Account;
import test.entities.Url;
import test.models.UrlRequest;
import test.models.UrlResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface IUrlService {
    UrlResponse add(UrlRequest urlRequest, Account createdBy, HttpServletRequest servletRequest);

    Map<String, Integer> getByAccountId(String createdById);

    Url getByShortUrl(String shortUrlString);

    Url save(Url url);
}
