package org.personalized.dashboard.dao.api;

/**
 * Created by sudan on 25/7/15.
 */
public interface SessionDao {

    void create(String sessionId, String userId);

    String get(String sessionId);

    void remove(String sessionId);
}
