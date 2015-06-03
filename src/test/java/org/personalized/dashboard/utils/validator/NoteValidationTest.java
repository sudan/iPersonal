package org.personalized.dashboard.utils.validator;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

/**
 * Created by sudan on 30/5/15.
 */
@ActiveProfiles("test")
public class NoteValidationTest {

    private ValidationService noteValidationService;

    @Before
    public void initialize() {
        this.noteValidationService = new NoteValidationService();
    }

    @Test
    public void testNoteValidation() {
        // TODO this will be revisited again once the validation is refactored completely
        /*
        StringBuilder invalidTitle = new StringBuilder();
        for(int i = 0; i < Constants.TITLE_MAX_LENGTH+1; i++)
            invalidTitle.append("a");

        StringBuilder invalidContent = new StringBuilder();
        for( int i = 0; i < Constants.CONTENT_MAX_LENGTH + 1; i++)
            invalidContent.append("b");

        Note note = new Note("NOTE7W3687R4UFB3", "title", "content");
        List<ErrorEntity> errorEntities = noteValidationService.validate(note);
        Assert.assertEquals("Error count is 0", 0, errorEntities.size());

        note = new Note("NOTE7W3687R4UFB3", invalidTitle.toString(), "content");
        errorEntities = noteValidationService.validate(note);
        Assert.assertEquals("Error count is 1", 1, errorEntities.size());
        Assert.assertEquals("Error is MAX_TITLE_LENGTH_EXCEEDED", ErrorCodes.LENGTH_EXCEEDED.name(), errorEntities.get(0).getName());
        Assert.assertEquals("Error description matches","title length cannot exceed 50 characters", errorEntities.get(0).getDescription());

        note = new Note("NOTE7W3687R4UFB3", "note", invalidContent.toString());
        errorEntities = noteValidationService.validate(note);
        Assert.assertEquals("Error count is 1", 1, errorEntities.size());
        Assert.assertEquals("Error is MAX_CONTENT_LENGTH_EXCEEDED", ErrorCodes.LENGTH_EXCEEDED.name(), errorEntities.get(0).getName());
        Assert.assertEquals("Error description matches", "content length cannot exceed 1,000 characters", errorEntities.get(0).getDescription());

        note = new Note("NOTE7W3687R4UFB3", invalidTitle.toString(), invalidContent.toString());
        errorEntities = noteValidationService.validate(note);
        Assert.assertEquals("Error count is 2", 2  , errorEntities.size());
        Assert.assertEquals("Error is MAX_TITLE_LENGTH_EXCEEEDED", ErrorCodes.LENGTH_EXCEEDED.name(), errorEntities.get(0).getName());
        Assert.assertEquals("Error description matches","title length cannot exceed 50 characters", errorEntities.get(0).getDescription());
        Assert.assertEquals("Error is MAX_CONTENT_LENGTH_EXCEEDED", ErrorCodes.LENGTH_EXCEEDED.name(), errorEntities.get(1).getName());
        Assert.assertEquals("Error description matches", "content length cannot exceed 1,000 characters", errorEntities.get(1).getDescription());
    */
    }
}
