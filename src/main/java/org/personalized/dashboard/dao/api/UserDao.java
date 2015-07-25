package org.personalized.dashboard.dao.api;

import org.personalized.dashboard.model.User;

/**
 * Created by sudan on 25/7/15.
 */
public interface UserDao {

    /**
     * Creates a new user
     * @param user
     * @return
     */
    String create(User user);

    /**
     * Gets user based on UserId
     * @param userId
     * @return
     */
    User get(String userId);

    /**
     * Updates user object
     * @param user
     * @return
     */
    Long update(User user);
}
