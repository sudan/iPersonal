package org.personalized.dashboard.service.api;

import org.personalized.dashboard.model.Bookmark;

/**
 * Created by sudan on 3/4/15.
 */
public interface BookmarkService {

    /**
     * Create a bookmark for the user
     * @param bookmark
     * @return
     */
    String createBookmark(Bookmark bookmark);

    /**
     * Get the bookmark for the id
     * @param bookmarkId
     * @return
     */
    Bookmark getBookmark(String bookmarkId);

    /**
     * Update the bookmark and return the updated object
     * @param bookmark
     * @return
     */
    Bookmark updateBookmark(Bookmark bookmark);
}
