package org.personalized.dashboard.service.impl;

import com.google.inject.Inject;
import org.personalized.dashboard.dao.api.BookmarkDao;
import org.personalized.dashboard.model.Bookmark;
import org.personalized.dashboard.service.api.BookmarkService;
import org.personalized.dashboard.utils.auth.SessionManager;
import org.springframework.stereotype.Repository;

/**
 * Created by sudan on 3/4/15.
 */
@Repository
public class BookmarkServiceImpl implements BookmarkService {

    private final BookmarkDao bookmarkDao;
    private final SessionManager sessionManager;

    @Inject
    public BookmarkServiceImpl(BookmarkDao bookmarkDao, SessionManager sessionManager){
        this.bookmarkDao = bookmarkDao;
        this.sessionManager = sessionManager;
    }

    @Override
    public String  createBookmark(Bookmark bookmark) {
            bookmark.setUserId(sessionManager.getUserIdFromSession());
            return bookmarkDao.create(bookmark);
    }
}
