package test.controllers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import test.entities.Url;
import test.services.IUrlService;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class RedirectionController {
    private final IUrlService urlService;

    RedirectionController(@Qualifier("urlService") IUrlService urlService) {
        this.urlService = urlService;
    }

    // 1.2 - Redirecting the client on the configured address with the configured http status
    @GetMapping("/{shortUrl}")
    ResponseEntity redirect(@PathVariable String shortUrl) {
        Url url = urlService.getByShortUrl(shortUrl);
        if (url != null) {
            HttpHeaders httpHeaders = new HttpHeaders();
            try {
                httpHeaders.setLocation(new URI(url.getUrlString()));

                url.increaseNumberOfRedirects();
                urlService.save(url);

                return ResponseEntity
                        .status(HttpStatus.valueOf(url.getRedirectType()))
                        .headers(httpHeaders)
                        .build();
            } catch (URISyntaxException e) {
                //
            }
        }
        return ResponseEntity.notFound().build();
    }
}
