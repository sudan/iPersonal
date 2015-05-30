package org.personalized.dashboard.utils.validator;

import com.google.common.collect.Lists;
import org.personalized.dashboard.model.Note;
import org.personalized.dashboard.utils.Constants;

import java.text.MessageFormat;
import java.util.List;

/**
 * Created by sudan on 30/5/15.
 */
public class NoteValidationService implements ValidationService<Note> {

    @Override
    public List<ErrorEntity> validate(Note note) {
        List<ErrorEntity> errorEntities = Lists.newArrayList();
        validateTitleLength(note,errorEntities);
        validateContentLength(note, errorEntities);
        return errorEntities;
    }

    private void validateTitleLength(Note note, List<ErrorEntity> errorEntities) {
        if(note.getTitle().length() > Constants.NOTE_TITLE_MAX_LENGTH){
            ErrorEntity errorEntity = new ErrorEntity(ErrorCodes.MAX_NOTE_TITLE_LENGTH_EXCEEDED.name(),
                    MessageFormat.format(ErrorCodes.MAX_NOTE_TITLE_LENGTH_EXCEEDED.getDescription(), Constants.NOTE_TITLE_MAX_LENGTH));
            errorEntities.add(errorEntity);
        }
    }

    private void validateContentLength(Note note, List<ErrorEntity> errorEntities) {
        if(note.getNote().length() > Constants.NOTE_CONTENT_MAX_LENGTH) {
            ErrorEntity errorEntity = new ErrorEntity(ErrorCodes.MAX_NOTE_CONTENT_LENGTH_EXCEEDED.name(),
                    MessageFormat.format(ErrorCodes.MAX_NOTE_CONTENT_LENGTH_EXCEEDED.getDescription(), Constants.NOTE_CONTENT_MAX_LENGTH));
            errorEntities.add(errorEntity);
        }
    }
}
