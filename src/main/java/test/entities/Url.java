package test.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpStatus;

import javax.persistence.*;

@Entity
public class Url {
    //region Fields
    @Id
    @GeneratedValue
    private Long id;
    private String urlString;
    private String shortUrlString;
    private int redirectType = HttpStatus.FOUND.value();

    @JsonIgnore
    private int numberOfRedirects = 0;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "account_accountId")
    private Account addedBy;
    //endregion

    public Url() {}

    public Url(String url, String shortUrl, Account addedBy) {
        this.urlString = url;
        this.addedBy = addedBy;
        this.shortUrlString = shortUrl;
    }

    //region GettersSetters
    public Long getId() {
        return id;
    }

    public String getUrlString() {
        return urlString;
    }

    public String getShortUrlString() {
        return shortUrlString;
    }

    public Account getAddedBy() {
        return addedBy;
    }

    public void setRedirectType(int redirectType){
        this.redirectType = redirectType;
    }

    public int getRedirectType(){
        return redirectType;
    }

    public int getNumberOfRedirects() {
        return numberOfRedirects;
    }

    public void increaseNumberOfRedirects() {
        this.numberOfRedirects++;
    }
    //endregion
}
