package org.personalized.dashboard.auth;

import com.google.inject.Inject;
import org.personalized.dashboard.bootstrap.OAuthBootstrap;
import org.personalized.dashboard.dao.api.UserDao;
import org.personalized.dashboard.model.User;
import org.personalized.dashboard.utils.ConfigKeys;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.api.impl.GoogleTemplate;
import org.springframework.social.oauth2.AccessGrant;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by sudan on 26/7/15.
 */
public class GoogleLoginCallbackServlet extends HttpServlet {

    private final UserDao userDao;

    @Inject
    public GoogleLoginCallbackServlet(UserDao userDao) {
        this.userDao = userDao;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {


            AccessGrant accessGrant = OAuthBootstrap.getGooglePlusFactory().getOAuthOperations().exchangeForAccess(
                    request.getParameter("code"), OAuthBootstrap.getoAuth2Parameters().getRedirectUri(), null);
            Google google = new GoogleTemplate(accessGrant.getAccessToken());

            User user = new User();
            user.setEmail(google.plusOperations().getGoogleProfile().getAccountEmail());
            user.setUsername(google.plusOperations().getGoogleProfile().getDisplayName());
            user.setProfilePicURL(google.plusOperations().getGoogleProfile().getImageUrl());
            userDao.createOrUpdate(user);
            response.sendRedirect(ConfigKeys.DASHBOARD);

        } catch (Exception e) {
            response.sendRedirect(ConfigKeys.GOOGLE_LOGIN);
        }

    }
}
