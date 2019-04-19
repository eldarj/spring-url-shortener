package test.entities;

import java.util.Base64;
import javax.persistence.OneToMany;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
public class Account {
    //region Fields
    @Id
    private String accountId;
    private String password;
    private String token;

    @OneToMany(mappedBy = "addedBy")
    private List<Url> addedUrls;
    //endregion

    public Account() {}

    public Account(String accountId, String password) {
        this.accountId = accountId;
        this.password = password;
        this.token = Base64.getEncoder().encodeToString(String.format("%s:%s", accountId, password).getBytes());
    }

    //region GettersSetters
    public String getAccountId() {
        return accountId;
    }

    public String getPassword() {
        return password;
    }

    public List<Url> getAddedUrls() {
        return addedUrls;
    }

    public String getToken(){
        return token;
    }

    public void setAddedUrls(List<Url> addedUrls) {
        this.addedUrls = addedUrls;
    }

    //endregion
}
