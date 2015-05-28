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
}
