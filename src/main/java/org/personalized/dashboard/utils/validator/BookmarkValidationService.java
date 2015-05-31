package org.personalized.dashboard.utils.validator;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.personalized.dashboard.model.Bookmark;
import org.personalized.dashboard.utils.Constants;

import java.text.MessageFormat;
import java.util.List;

/**
 * Created by sudan on 26/5/15.
 */
public class BookmarkValidationService implements ValidationService<Bookmark> {

    @Override
    public List<ErrorEntity> validate(Bookmark bookmark) {
        List<ErrorEntity> errorEntities = Lists.newArrayList();
        validateUrl(bookmark, errorEntities);
        validateName(bookmark, errorEntities);
        validateDescription(bookmark, errorEntities);
        return errorEntities;
    }

    private void validateUrl(Bookmark bookmark, List<ErrorEntity> errorEntities) {
        UrlValidator urlValidator = new UrlValidator();
        if(!urlValidator.isValid(bookmark.getUrl())) {
            ErrorEntity errorEntity = new ErrorEntity(ErrorCodes.INVALID_URL.name(), ErrorCodes.INVALID_URL.getDescription());
            errorEntities.add(errorEntity);
        }
        if(bookmark.getUrl().length() > Constants.BOOKMARK_URL_MAX_LENGTH) {
            ErrorEntity errorEntity = new ErrorEntity(ErrorCodes.BOOKMARK_URL_LENGTH_EXCEEDED.name(),
                    MessageFormat.format(ErrorCodes.BOOKMARK_URL_LENGTH_EXCEEDED.getDescription(), Constants.BOOKMARK_URL_MAX_LENGTH));
            errorEntities.add(errorEntity);
        }
    }

    private void validateName(Bookmark bookmark, List<ErrorEntity> errorEntities) {

        if(StringUtils.isEmpty(bookmark.getName())) {
            ErrorEntity errorEntity = new ErrorEntity(ErrorCodes.EMPTY_BOOKMARK_NAME.name(), ErrorCodes.EMPTY_BOOKMARK_NAME.getDescription());
            errorEntities.add(errorEntity);
        }
        else if(bookmark.getName().length() > Constants.BOOKMARK_NAME_MAX_LENGTH) {
            ErrorEntity errorEntity = new ErrorEntity(ErrorCodes.BOOKMARK_NAME_LENGTH_EXCEEDED.name(),
                    MessageFormat.format(ErrorCodes.BOOKMARK_NAME_LENGTH_EXCEEDED.getDescription(), Constants.BOOKMARK_NAME_MAX_LENGTH));
            errorEntities.add(errorEntity);
        }
    }

    private void validateDescription(Bookmark bookmark, List<ErrorEntity> errorEntities) {

        if(StringUtils.isEmpty(bookmark.getDescription())) {
            ErrorEntity errorEntity = new ErrorEntity(ErrorCodes.EMPTY_BOOKMARK_CONTENT.name(), ErrorCodes.EMPTY_BOOKMARK_CONTENT.getDescription());
            errorEntities.add(errorEntity);
        }
        else if(bookmark.getDescription().length() > Constants.BOOKMARK_CONTENT_MAX_LENGTH) {
            ErrorEntity errorEntity = new ErrorEntity(ErrorCodes.BOOKMARK_CONTENT_LENGTH_EXCEEDED.name(),
                    MessageFormat.format(ErrorCodes.BOOKMARK_CONTENT_LENGTH_EXCEEDED.getDescription(), Constants.BOOKMARK_CONTENT_MAX_LENGTH));
            errorEntities.add(errorEntity);
        }
    }
}
