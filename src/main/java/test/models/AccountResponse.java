package test.models;

import org.springframework.http.HttpStatus;
import test.models.base.ResponseModel;

import java.util.Objects;

public class AccountResponse extends ResponseModel {
    //region Fields
    private boolean success;
    private String description;
    private String password;
    private String token;
    //endregion

    public AccountResponse(boolean success, String description, HttpStatus httpStatus) {
        super(httpStatus);
        this.success = success;
        this.description = description;
    }

    public AccountResponse(boolean success, String description, HttpStatus httpStatus, String password, String token) {
        super(httpStatus);
        this.success = success;
        this.description = description;
        this.password = password;
        this.token = token;
    }

    //region GettersSetters
    public boolean isSuccess() {
        return success;
    }

    public String getDescription() {
        return description;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }
    //endregion

    //region Overrides
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountResponse that = (AccountResponse) o;
        return success == that.success &&
                Objects.equals(description, that.description) &&
                Objects.equals(password, that.password) &&
                Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(success, description, password, token);
    }
    //endregion
}
