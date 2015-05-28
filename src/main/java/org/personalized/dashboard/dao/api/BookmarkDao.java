package org.personalized.dashboard.dao.api;

import org.personalized.dashboard.model.Bookmark;

/**
 * Created by sudan on 3/4/15.
 */
public interface BookmarkDao {

    /**
     * Create a new bookmark for the user
     * @param bookmark
     */
    String create(Bookmark bookmark);

}