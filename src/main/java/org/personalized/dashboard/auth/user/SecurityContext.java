package org.personalized.dashboard.auth.user;

import org.personalized.dashboard.model.User;

/**
 * Created by sudan on 25/7/15.
 */
public final class SecurityContext {

    private static final ThreadLocal<User> currentUser = new ThreadLocal<User>();

    public static User getCurrentUser() {
        User user = currentUser.get();
        if (user == null) {
            throw new IllegalStateException("No user is currently signed in");
        }
        return user;
    }

    public static void setCurrentUser(User user) {
        currentUser.set(user);
    }

    public static boolean userSignedIn() {
        return currentUser.get() != null;
    }

    public static void remove() {
        currentUser.remove();
    }
}
