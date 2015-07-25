package org.personalized.dashboard.auth.user;

import org.springframework.web.util.CookieGenerator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by sudan on 25/7/15.
 */
public final class UserCookieGenerator {

    private final CookieGenerator userCookieGenerator = new CookieGenerator();

    public UserCookieGenerator() {
        userCookieGenerator.setCookieName("user_sec");
        userCookieGenerator.setCookieHttpOnly(true);
        userCookieGenerator.setCookieSecure(true);
    }

    public void addCookie(String userId, HttpServletResponse response) {
        userCookieGenerator.addCookie(response, userId);
    }

    public void removeCookie(HttpServletResponse response) {
        userCookieGenerator.addCookie(response, "");
    }

    public String readCookieValue(HttpServletRequest request) {
        Cookie [] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(userCookieGenerator.getCookieName())) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
