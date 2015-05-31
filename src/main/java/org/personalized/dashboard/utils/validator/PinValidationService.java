package org.personalized.dashboard.utils.validator;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.personalized.dashboard.model.Pin;
import org.personalized.dashboard.utils.Constants;

import java.text.MessageFormat;
import java.util.List;

/**
 * Created by sudan on 30/5/15.
 */
public class PinValidationService implements ValidationService<Pin> {

    @Override
    public List<ErrorEntity> validate(Pin pin) {
        List<ErrorEntity> errorEntities = Lists.newArrayList();
        validateImageUrl(pin, errorEntities);
        validateName(pin, errorEntities);
        validateDescription(pin, errorEntities);
        return errorEntities;
    }

    private void validateImageUrl(Pin pin, List<ErrorEntity> errorEntities) {
        UrlValidator urlValidator = new UrlValidator();
        if(!urlValidator.isValid(pin.getImageUrl())) {
            ErrorEntity errorEntity = new ErrorEntity(ErrorCodes.INVALID_URL.name(), ErrorCodes.INVALID_URL.getDescription());
            errorEntities.add(errorEntity);
        }
        if(pin.getImageUrl().length() > Constants.PIN_URL_MAX_LENGTH) {
            ErrorEntity errorEntity = new ErrorEntity(ErrorCodes.PIN_URL_LENGTH_EXCEEDED.name(),
                    MessageFormat.format(ErrorCodes.PIN_URL_LENGTH_EXCEEDED.getDescription(), Constants.PIN_URL_MAX_LENGTH));
            errorEntities.add(errorEntity);
        }
    }

    private void validateName(Pin pin, List<ErrorEntity> errorEntities) {

        if(StringUtils.isEmpty(pin.getName())) {
            ErrorEntity errorEntity = new ErrorEntity(ErrorCodes.EMPTY_PIN_NAME.name(), ErrorCodes.EMPTY_PIN_NAME.getDescription());
            errorEntities.add(errorEntity);
        }
        else if(pin.getName().length() > Constants.PIN_NAME_MAX_LENGTH) {
            ErrorEntity errorEntity = new ErrorEntity(ErrorCodes.PIN_NAME_LENGTH_EXCEEDED.name(),
                    MessageFormat.format(ErrorCodes.PIN_NAME_LENGTH_EXCEEDED.getDescription(), Constants.PIN_NAME_MAX_LENGTH));
            errorEntities.add(errorEntity);
        }
    }

    private void validateDescription(Pin pin, List<ErrorEntity> errorEntities) {

        if(StringUtils.isEmpty(pin.getDescription())) {
            ErrorEntity errorEntity = new ErrorEntity(ErrorCodes.EMPTY_PIN_DESC.name(), ErrorCodes.EMPTY_PIN_DESC.getDescription());
            errorEntities.add(errorEntity);
        }
        else if(pin.getDescription().length() > Constants.PIN_DESCRIPTION_MAX_LENGTH) {
            ErrorEntity errorEntity = new ErrorEntity(ErrorCodes.PIN_DESC_LENGTH_EXCEEDED.name(),
                    MessageFormat.format(ErrorCodes.PIN_DESC_LENGTH_EXCEEDED.getDescription(), Constants.PIN_DESCRIPTION_MAX_LENGTH));
            errorEntities.add(errorEntity);
        }
    }
}
