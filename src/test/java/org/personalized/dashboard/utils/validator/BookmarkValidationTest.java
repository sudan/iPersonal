package org.personalized.dashboard.utils.validator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.personalized.dashboard.model.Bookmark;
import org.personalized.dashboard.utils.Constants;
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

        StringBuilder invalidUrl = new StringBuilder();
        for(int i = 0; i < Constants.BOOKMARK_URL_MAX_LENGTH + 1; i++) {
            invalidUrl.append("a");
        }

        StringBuilder invalidName = new StringBuilder();
        for(int i = 0; i < Constants.BOOKMARK_NAME_MAX_LENGTH + 1; i++) {
            invalidName.append("n");
        }

        StringBuilder invalidDescription = new StringBuilder();
        for(int i = 0; i < Constants.BOOKMARK_CONTENT_MAX_LENGTH + 1; i++) {
            invalidDescription.append("c");
        }

        Bookmark bookmark = new Bookmark("BOK123456789", "google", "search engine", "http://www.google.com");
        List<ErrorEntity> errorEntities = bookmarkValidationService.validate(bookmark);
        Assert.assertEquals("Error count is 0", 0 , errorEntities.size());

        bookmark = new Bookmark("BOK123456789", "google", "search engine", "http//www.google.com");
        errorEntities = bookmarkValidationService.validate(bookmark);
        Assert.assertEquals("Error count is 1", 1 , errorEntities.size());
        Assert.assertEquals("Error is INVALID_URL",ErrorCodes.INVALID_URL.name(), errorEntities.get(0).getName());
        Assert.assertEquals("Error description matches",ErrorCodes.INVALID_URL.getDescription(), errorEntities.get(0).getDescription());

        bookmark = new Bookmark("BOK123456789", "google", "search engine", invalidUrl.toString());
        errorEntities = bookmarkValidationService.validate(bookmark);
        Assert.assertEquals("Error count is 2", 2, errorEntities.size());
        Assert.assertEquals("Error 1 is INVALID_URL", ErrorCodes.INVALID_URL.name(), errorEntities.get(0).getName());
        Assert.assertEquals("Error 1 description matches",ErrorCodes.INVALID_URL.getDescription(), errorEntities.get(0).getDescription());
        Assert.assertEquals("Error 2 is MAX_BOOKMARK_URL_LENGTH_EXCEEDED", ErrorCodes.BOOKMARK_URL_LENGTH_EXCEEDED.name(),errorEntities.get(1).getName());
        Assert.assertEquals("Error 2 description matches", "url length cannot exceed 300 characters", errorEntities.get(1).getDescription());

        bookmark = new Bookmark("BOK123456789", invalidName.toString(), invalidDescription.toString(), invalidUrl.toString());
        errorEntities = bookmarkValidationService.validate(bookmark);
        Assert.assertEquals("Error count is 4", 4, errorEntities.size());
        Assert.assertEquals("Error 1 is INVALID_URL", ErrorCodes.INVALID_URL.name(), errorEntities.get(0).getName());
        Assert.assertEquals("Error description matches",ErrorCodes.INVALID_URL.getDescription(), errorEntities.get(0).getDescription());
        Assert.assertEquals("Error 2 description matches", "url length cannot exceed 300 characters", errorEntities.get(1).getDescription());
        Assert.assertEquals("Error 2 is MAX_BOOKMARK_URL_LENGTH_EXCEEDED", ErrorCodes.BOOKMARK_URL_LENGTH_EXCEEDED.name(),errorEntities.get(1).getName());
        Assert.assertEquals("Error 3 is MAX_BOOKMARK_NAME_LENGTH_EXCEEDED", ErrorCodes.BOOKMARK_NAME_LENGTH_EXCEEDED.name(), errorEntities.get(2).getName());
        Assert.assertEquals("Error 3 description matches", "name length cannot exceed 50 characters", errorEntities.get(2).getDescription());
        Assert.assertEquals("Error 4 is MAX_BOOKMARK_CONTENT_LENGTH_EXCEEDED", ErrorCodes.BOOKMARK_CONTENT_LENGTH_EXCEEDED.name(), errorEntities.get(3).getName());
        Assert.assertEquals("Error 4 description matches", "description length cannot exceed 1,000 characters", errorEntities.get(3).getDescription());
    }
}
