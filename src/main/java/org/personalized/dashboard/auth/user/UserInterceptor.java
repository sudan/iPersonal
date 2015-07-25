package org.personalized.dashboard.auth.user;

import com.google.inject.Inject;
import org.personalized.dashboard.dao.api.SessionDao;
import org.personalized.dashboard.dao.api.UserDao;
import org.personalized.dashboard.model.User;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class UserInterceptor extends HandlerInterceptorAdapter {

    private final UserCookieGenerator userCookieGenerator;
    private final SessionDao sessionDao;
    private final UserDao userDao;

    @Inject
    public UserInterceptor(SessionDao sessionDao, UserCookieGenerator userCookieGenerator,
                           UserDao userDao) {
        this.sessionDao = sessionDao;
        this.userCookieGenerator = userCookieGenerator;
        this.userDao = userDao;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        rememberUser(request, response);
        handleSignOut(request, response);
        if (SecurityContext.userSignedIn() || requestForSignIn(request)) {
            return true;
        } else {
            return requireSignIn(request, response);
        }
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        SecurityContext.remove();
    }

    private void rememberUser(HttpServletRequest request, HttpServletResponse response) {
        String userId = userCookieGenerator.readCookieValue(request);
        if (userId == null) {
            return;
        }
        if (userDao.get(userId) == null) {
            userCookieGenerator.removeCookie(response);
            return;
        }
        User user = new User();
        user.setUserId(userId);
        SecurityContext.setCurrentUser(user);
    }

    private void handleSignOut(HttpServletRequest request, HttpServletResponse response) {
        if (SecurityContext.userSignedIn() && request.getServletPath().startsWith("/signout")) {
            userCookieGenerator.removeCookie(response);
            sessionDao.remove(userCookieGenerator.readCookieValue(request));
            SecurityContext.remove();
        }
    }

    private boolean requestForSignIn(HttpServletRequest request) {
        return request.getServletPath().startsWith("/signin");
    }

    private boolean requireSignIn(HttpServletRequest request, HttpServletResponse response) throws Exception {
        new RedirectView("/signin", true).render(null, request, response);
        return false;
    }
}