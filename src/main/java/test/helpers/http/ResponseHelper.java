package test.helpers.http;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHelper {
    private static final String WWWAUTH_BASIC_APIREALM = "Basic realm=\"apirealm\" title=\"Basic auth. for Api realm";

    public static ResponseEntity responseUnauthorized() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("WWW-Authenticate", WWWAUTH_BASIC_APIREALM);

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .headers(httpHeaders)
                .build();
    }
}
