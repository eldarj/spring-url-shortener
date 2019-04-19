package test.models;

import org.springframework.http.HttpStatus;
import test.models.base.ResponseModel;

public class UrlResponse extends ResponseModel {
    //region Fields
    private String shortUrl;
    private int redirectType = HttpStatus.FOUND.value(); // define redirect httpStatusCode
    //endregion

    public UrlResponse(String shortUrl, HttpStatus httpStatus) {
        super(httpStatus);
        this.shortUrl = shortUrl;
    }

    public UrlResponse(String shortUrl, int redirectType, HttpStatus httpStatus) {
        super(httpStatus);
        this.shortUrl = shortUrl;
        this.redirectType = redirectType;
    }

    //region GettersSetters
    public String getShortUrl() {
        return shortUrl;
    }

    public int getRedirectType() {
        return redirectType;
    }
    //endregion
}
