package org.personalized.dashboard.service.impl;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.personalized.dashboard.dao.api.BookmarkDao;
import org.personalized.dashboard.model.Bookmark;
import org.personalized.dashboard.service.api.BookmarkService;
import org.personalized.dashboard.utils.validator.ErrorEntity;
import org.personalized.dashboard.utils.validator.ValidationService;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by sudan on 3/4/15.
 */
@Repository
public class BookmarkServiceImpl implements BookmarkService {

    private final BookmarkDao bookmarkDao;
    private final ValidationService bookmarkValidationService;

    @Inject
    public BookmarkServiceImpl(BookmarkDao bookmarkDao, @Named("bookmark") ValidationService bookmarkValidationService){
        this.bookmarkDao = bookmarkDao;
        this.bookmarkValidationService = bookmarkValidationService;
    }

    @Override
    public List<ErrorEntity> createBookmark(Bookmark bookmark) {
        List<ErrorEntity> errorEntities = bookmarkValidationService.validate(bookmark);
        if(CollectionUtils.isEmpty(errorEntities)) {
            bookmarkDao.create(bookmark);
        }
        return errorEntities;
    }
}
