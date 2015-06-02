package org.personalized.dashboard.utils.validator;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
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

        if(StringUtils.isEmpty(note.getTitle())) {
            ErrorEntity errorEntity = new ErrorEntity(ErrorCodes.EMPTY_NOTE_TITLE.name(), ErrorCodes.EMPTY_NOTE_TITLE.getDescription());
            errorEntities.add(errorEntity);
        }
        else if(note.getTitle().length() > Constants.TITLE_MAX_LENGTH){
            ErrorEntity errorEntity = new ErrorEntity(ErrorCodes.NOTE_TITLE_LENGTH_EXCEEDED.name(),
                    MessageFormat.format(ErrorCodes.NOTE_TITLE_LENGTH_EXCEEDED.getDescription(), Constants.TITLE_MAX_LENGTH));
            errorEntities.add(errorEntity);
        }
    }

    private void validateContentLength(Note note, List<ErrorEntity> errorEntities) {

        if(StringUtils.isEmpty(note.getNote())) {
            ErrorEntity errorEntity = new ErrorEntity(ErrorCodes.EMPTY_NOTE_CONTENT.name(), ErrorCodes.EMPTY_NOTE_CONTENT.getDescription());
            errorEntities.add(errorEntity);
        }
        else if(note.getNote().length() > Constants.CONTENT_MAX_LENGTH) {
            ErrorEntity errorEntity = new ErrorEntity(ErrorCodes.NOTE_CONTENT_LENGTH_EXCEEDED.name(),
                    MessageFormat.format(ErrorCodes.NOTE_CONTENT_LENGTH_EXCEEDED.getDescription(), Constants.CONTENT_MAX_LENGTH));
            errorEntities.add(errorEntity);
        }
    }
}
