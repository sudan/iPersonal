package org.personalized.dashboard.utils.validator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.personalized.dashboard.model.Bookmark;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

/**
 * Created by sudan on 30/5/15.
 */
@ActiveProfiles("test")
public class BookmarkValidationTest {

    private ValidationService bookmarkValidationService;

    @Before
    public void initialize(){
        this.bookmarkValidationService = new BookmarkValidationService();
    }

    @Test
    public void testBookmarkValidation(){
        Bookmark bookmark = new Bookmark("BOO123456789", "google", "search engine", "http://www.google.com");
        List<ErrorEntity> errorEntities = bookmarkValidationService.validate(bookmark);
        Assert.assertEquals("Error count is 0", 0 , errorEntities.size());

        bookmark = new Bookmark("BOO123456789", "google", "search engine", "http//www.google.com");
        errorEntities = bookmarkValidationService.validate(bookmark);
        Assert.assertEquals("Error count is 1", 1 , errorEntities.size());
        Assert.assertEquals("Error is INVALID_URL",ErrorCodes.INVALID_URL.name(), errorEntities.get(0).getName());
    }
}
