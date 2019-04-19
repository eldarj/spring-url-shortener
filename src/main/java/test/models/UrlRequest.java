package test.models;

import org.springframework.http.HttpStatus;

public class UrlRequest {
    private String url;
    private int redirectType = HttpStatus.FOUND.value();

    public UrlRequest() {}

    public UrlRequest(String url, int redirectType) {
        this.url = url;
        this.redirectType = redirectType;
    }

    public String getUrl() {
        return url;
    }

    public int getRedirectType() {
        return redirectType;
    }
}
