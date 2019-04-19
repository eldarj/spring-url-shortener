package test.helpers.http;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class ServletRequestHelper {

    public static String getBaseUrl(HttpServletRequest servletRequest)
    {
        String base = servletRequest.getLocalAddr();
        base = Objects.equals(base, "0:0:0:0:0:0:0:1") ? "localhost" : base; // check for ipv6

        return (servletRequest.isSecure() ? "https" : "http")
                + "://" + base + ":" + servletRequest.getServerPort() + "/";
    }
}
