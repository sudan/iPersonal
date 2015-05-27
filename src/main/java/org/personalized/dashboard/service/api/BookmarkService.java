package org.personalized.dashboard.service.api;

import org.personalized.dashboard.model.Bookmark;
import org.personalized.dashboard.utils.validator.ErrorEntity;

import java.util.List;

/**
 * Created by sudan on 3/4/15.
 */
public interface BookmarkService {

    /**
     * Create a bookmark for the user
     * @param bookmark
     * @return
     */
    List<ErrorEntity> createBookmark(Bookmark bookmark);
}
