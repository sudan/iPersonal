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

    /**
     * Get bookmark for the given bookmarkId and userId
     * @param bookmarkId
     * @param userId
     * @return
     */
    Bookmark get(String bookmarkId, String userId);

    /**
     * Update the bookmark and return the updated one
     * @param bookmark
     * @return
     */
    Bookmark update(Bookmark bookmark, String userId);

    /**
     * Delete the bookmark for the given bookmarkId and userId
     * @param bookmarkId
     * @param userId
     */
    void delete(String bookmarkId, String userId);
}