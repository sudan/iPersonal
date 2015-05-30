package org.personalized.dashboard.utils.validator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.personalized.dashboard.model.Note;
import org.personalized.dashboard.utils.Constants;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

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
        StringBuilder invalidTitle = new StringBuilder();
        for(int i = 0; i < Constants.NOTE_TITLE_MAX_LENGTH+1; i++)
            invalidTitle.append("a");

        StringBuilder invalidContent = new StringBuilder();
        for( int i = 0; i < Constants.NOTE_CONTENT_MAX_LENGTH + 1; i++)
            invalidContent.append("b");

        Note note = new Note("NOTE7W3687R4UFB3", "title", "content");
        List<ErrorEntity> errorEntities = noteValidationService.validate(note);
        Assert.assertEquals("Error count is 0", 0, errorEntities.size());

        note = new Note("NOTE7W3687R4UFB3", invalidTitle.toString(), "content");
        errorEntities = noteValidationService.validate(note);
        Assert.assertEquals("Error count is 1", 1, errorEntities.size());
        Assert.assertEquals("Error is MAX_TITLE_LENGTH_EXCEEDED", ErrorCodes.MAX_TITLE_LENGTH_EXCEEDED.name(), errorEntities.get(0).getName());

        note = new Note("NOTE7W3687R4UFB3", "note", invalidContent.toString());
        errorEntities = noteValidationService.validate(note);
        Assert.assertEquals("Error count is 1", 1, errorEntities.size());
        Assert.assertEquals("Error is MAX_CONTENT_LENGTH_EXCEEDED", ErrorCodes.MAX_CONTENT_LENGTH_EXCEEDED.name(), errorEntities.get(0).getName());

        note = new Note("NOTE7W3687R4UFB3", invalidTitle.toString(), invalidContent.toString());
        errorEntities = noteValidationService.validate(note);
        Assert.assertEquals("Error count is 2", 2  , errorEntities.size());
        Assert.assertEquals("Error is MAX_TITLE_LENGTH_EXCEEEDED", ErrorCodes.MAX_TITLE_LENGTH_EXCEEDED.name(), errorEntities.get(0).getName());
        Assert.assertEquals("Error is MAX_CONTENT_LENGTH_EXCEEDED", ErrorCodes.MAX_CONTENT_LENGTH_EXCEEDED.name(), errorEntities.get(1).getName());
    }
}
