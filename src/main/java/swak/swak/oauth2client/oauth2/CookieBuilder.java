package swak.swak.oauth2client.oauth2;

import javax.servlet.http.Cookie;

public class CookieBuilder {
    private final Cookie cookie;

    private CookieBuilder(String key, String value) {
        this.cookie = new Cookie(key, value);
    }

    public static CookieBuilder builder(String key, String value) {
        return new CookieBuilder(key, value);
    }

    public CookieBuilder path(String path) {
        cookie.setPath(path);
        return this;
    }

    public CookieBuilder httpOnly(boolean httpOnly) {
        cookie.setHttpOnly(httpOnly);
        return this;
    }

    public CookieBuilder maxAge(int expiry) {
        cookie.setMaxAge(expiry);
        return this;
    }

    public CookieBuilder secure(boolean secure) {
        cookie.setSecure(secure);
        return this;
    }

    public Cookie build() {
        return cookie;
    }
}
