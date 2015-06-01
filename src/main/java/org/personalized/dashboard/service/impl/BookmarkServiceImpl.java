package org.personalized.dashboard.service.impl;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.personalized.dashboard.dao.api.ActivityDao;
import org.personalized.dashboard.dao.api.BookmarkDao;
import org.personalized.dashboard.model.Activity;
import org.personalized.dashboard.model.ActivityType;
import org.personalized.dashboard.model.Bookmark;
import org.personalized.dashboard.model.EntityType;
import org.personalized.dashboard.service.api.BookmarkService;
import org.personalized.dashboard.utils.generator.ActivityGenerator;
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
    private final ActivityGenerator activityGenerator;
    private final ActivityDao activityDao;

    @Inject
    public BookmarkServiceImpl(BookmarkDao bookmarkDao, SessionManager sessionManager,
                               ActivityGenerator activityGenerator, ActivityDao activityDao){
        this.bookmarkDao = bookmarkDao;
        this.sessionManager = sessionManager;
        this.activityGenerator = activityGenerator;
        this.activityDao = activityDao;
    }

    @Override
    public String  createBookmark(Bookmark bookmark) {
            String bookmarkId = bookmarkDao.create(bookmark, sessionManager.getUserIdFromSession());
            Activity activity = activityGenerator.generate(ActivityType.CREATED, EntityType.BOOKMARK, bookmarkId, bookmark.getName());
            activityDao.add(activity, sessionManager.getUserIdFromSession());
            return bookmarkId;
    }

    @Override
    public Bookmark getBookmark(String bookmarkId) {
        return bookmarkDao.get(bookmarkId, sessionManager.getUserIdFromSession());
    }

    @Override
    public Long updateBookmark(Bookmark bookmark) {
        Long modifiedCount = bookmarkDao.update(bookmark, sessionManager.getUserIdFromSession());
        if(modifiedCount > 0) {
            Activity activity = activityGenerator.generate(ActivityType.UPDATED, EntityType.BOOKMARK, bookmark.getBookmarkId(), bookmark.getName());
            activityDao.add(activity, sessionManager.getUserIdFromSession());
        }
        return modifiedCount;
    }

    @Override
    public void deleteBookmark(String bookmarkId) {
        Long deletedCount = bookmarkDao.delete(bookmarkId, sessionManager.getUserIdFromSession());
        if (deletedCount > 0) {
            Activity activity = activityGenerator.generate(ActivityType.DELETED, EntityType.BOOKMARK, bookmarkId, StringUtils.EMPTY);
            activityDao.add(activity, sessionManager.getUserIdFromSession());
        }
    }

    @Override
    public Long countBookmarks() {
        return bookmarkDao.count(sessionManager.getUserIdFromSession());
    }

    public List<Bookmark> fetchBookmarks(int limit, int offset) {
        return bookmarkDao.get(limit, offset, sessionManager.getUserIdFromSession());
    }
}
