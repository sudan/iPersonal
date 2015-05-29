package org.personalized.dashboard.service.impl;

import com.google.inject.Inject;
import org.personalized.dashboard.dao.api.BookmarkDao;
import org.personalized.dashboard.model.Bookmark;
import org.personalized.dashboard.service.api.BookmarkService;
import org.personalized.dashboard.utils.auth.SessionManager;
import org.springframework.stereotype.Repository;

import java.util.List;

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
            return bookmarkDao.create(bookmark, sessionManager.getUserIdFromSession());
    }

    @Override
    public Bookmark getBookmark(String bookmarkId) {
        return bookmarkDao.get(bookmarkId, sessionManager.getUserIdFromSession());
    }

    @Override
    public Bookmark updateBookmark(Bookmark bookmark) {
        return bookmarkDao.update(bookmark, sessionManager.getUserIdFromSession());
    }

    @Override
    public void deleteBookmark(String bookmarkId) {
        bookmarkDao.delete(bookmarkId, sessionManager.getUserIdFromSession());
    }

    @Override
    public Long countBookmarks() {
        return bookmarkDao.count(sessionManager.getUserIdFromSession());
    }

    public List<Bookmark> fetchBookmarks(int limit, int offset) {
        return bookmarkDao.get(limit, offset, sessionManager.getUserIdFromSession());
    }
}
