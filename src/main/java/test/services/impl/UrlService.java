package test.services.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import test.entities.Account;
import test.entities.Url;
import test.helpers.strings.RandomStringGenerator;
import test.helpers.http.ServletRequestHelper;
import test.models.UrlRequest;
import test.models.UrlResponse;
import test.repositories.UrlRepository;
import test.services.IUrlService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Qualifier("urlService")
public class UrlService implements IUrlService {
    private final static String URL_REQ = "url is mandatory";

    private final UrlRepository urlRepo;

    public UrlService(UrlRepository urlRepo) {
        this.urlRepo = urlRepo;
    }

    @Override
    public Url save(Url url) {
        return urlRepo.save(url);
    }

    @Override
    public UrlResponse add(UrlRequest urlRequest, Account createdBy, HttpServletRequest servletRequest)
    {
        Url url;
        if (urlRequest.getUrl() != null && !urlRequest.getUrl().trim().isEmpty()) {
            url = getExistingUrl(urlRequest.getUrl(), createdBy.getAccountId());

            if (url == null) {
                url = new Url(urlRequest.getUrl(), RandomStringGenerator.generate(), createdBy);
            }
            url.setRedirectType(urlRequest.getRedirectType()); // u svakom slučaju snimi redirectType i ako smo ga promijenili

            urlRepo.save(url);
            return new UrlResponse(ServletRequestHelper.getBaseUrl(servletRequest) + url.getShortUrlString(),
                    url.getRedirectType(),
                    HttpStatus.OK);
        }

        return new UrlResponse(URL_REQ, HttpStatus.BAD_REQUEST);
    }

    @Override
    public Map<String, Integer> getByAccountId(String createdById)
    {
        return urlRepo.findAll().stream()
            .filter(url -> Objects.equals(url.getAddedBy().getAccountId(), createdById))
            .collect(Collectors.toMap(Url::getUrlString, Url::getNumberOfRedirects));
    }

    @Override
    public Url getByShortUrl(String shortUrlString)
    {
        List<Url> existingUrls = urlRepo.findAll().stream()
                .filter(url -> Objects.equals(url.getShortUrlString(), shortUrlString))
                .collect(Collectors.toList());

        return existingUrls.size() > 0 ? existingUrls.get(0) : null;
    }

    // (Todo...) Checks for existing urls (but doesn't check url's format anywhere tho)
    private Url getExistingUrl(String urlString, String createdById)
    {
        List<Url> existingUrls = urlRepo.findAll().stream()
            .filter(url -> Objects.equals(url.getUrlString(), urlString)
                    && Objects.equals(url.getAddedBy().getAccountId(), createdById))
            .collect(Collectors.toList());

        return existingUrls.size() > 0 ? existingUrls.get(0) : null;
    }
}
