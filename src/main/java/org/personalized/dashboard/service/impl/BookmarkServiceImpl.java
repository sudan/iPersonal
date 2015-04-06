package org.personalized.dashboard.service.impl;

import com.google.inject.Inject;
import org.personalized.dashboard.dao.api.BookmarkDao;
import org.personalized.dashboard.service.api.BookmarkService;
import org.springframework.stereotype.Repository;

/**
 * Created by sudan on 3/4/15.
 */
@Repository
public class BookmarkServiceImpl implements BookmarkService {

    private final BookmarkDao bookmarkDao;

    @Inject
    public BookmarkServiceImpl(BookmarkDao bookmarkDao){
        this.bookmarkDao = bookmarkDao;
    }
}
