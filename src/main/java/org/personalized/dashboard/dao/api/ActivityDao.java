package org.personalized.dashboard.dao.api;

import org.personalized.dashboard.model.Activity;

/**
 * Created by sudan on 30/5/15.
 */
public interface ActivityDao {

    /**
     * Add an entry of activity for the user
     * @param activity
     */
    void add(Activity activity, String userId);
}
