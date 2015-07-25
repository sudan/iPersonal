package org.personalized.dashboard.auth.user;

import org.personalized.dashboard.model.User;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by sudan on 25/7/15.
 */
public class SimpleSignInAdapter implements SignInAdapter {

    @Override
    public String signIn(String userId, Connection<?> connection, NativeWebRequest request) {

        User user = new User();
        user.setUserId(userId);
        UserCookieGenerator userCookieGenerator = new UserCookieGenerator();
        SecurityContext.setCurrentUser(user);
        userCookieGenerator.addCookie(userId, request.getNativeResponse(HttpServletResponse.class));
        return null;
    }
}
