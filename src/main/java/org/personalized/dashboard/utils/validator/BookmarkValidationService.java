package org.personalized.dashboard.utils.validator;

import com.google.common.collect.Lists;
import org.apache.commons.validator.routines.UrlValidator;
import org.personalized.dashboard.model.Bookmark;

import java.util.List;

/**
 * Created by sudan on 26/5/15.
 */
public class BookmarkValidationService implements ValidationService<Bookmark> {

    @Override
    public List<ErrorEntity> validate(Bookmark bookmark) {
        List<ErrorEntity> errorEntities = Lists.newArrayList();
        validateUrl(bookmark, errorEntities);
        return errorEntities;
    }

    private void validateUrl(Bookmark bookmark, List<ErrorEntity> errorEntities) {
        UrlValidator urlValidator = new UrlValidator();
        if(!urlValidator.isValid(bookmark.getUrl())) {
            ErrorEntity errorEntity = new ErrorEntity(ErrorCodes.INVALID_URL.name(), ErrorCodes.INVALID_URL.getDescription());
            errorEntities.add(errorEntity);
        }
    }
}
