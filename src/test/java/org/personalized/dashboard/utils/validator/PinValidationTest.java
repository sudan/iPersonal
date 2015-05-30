package org.personalized.dashboard.utils.validator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.personalized.dashboard.model.Pin;
import org.personalized.dashboard.utils.Constants;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

/**
 * Created by sudan on 30/5/15.
 */
@ActiveProfiles("test")
public class PinValidationTest {

    private ValidationService pinValidationService;

    @Before
    public void initialize() {
        this.pinValidationService = new PinValidationService();
    }

    @Test
    public void testPinValidation() {
        Pin pin = new Pin("PINE7W7VETWZAKE0", "pin","desc","http://www.google.com");

        List<ErrorEntity> errorEntities = pinValidationService.validate(pin);
        Assert.assertEquals("Error count is 0", 0, errorEntities.size());

        StringBuilder invalidUrl = new StringBuilder();
        for(int i = 0; i < Constants.PIN_URL_MAX_LENGTH + 1; i++) {
            invalidUrl.append("a");
        }

        pin = new Pin("PINE7W7VETWZAKE0", "pin","desc", invalidUrl.toString());
        errorEntities = pinValidationService.validate(pin);
        Assert.assertEquals("Error count is 2", 2, errorEntities.size());

        Assert.assertEquals("Error 1 is INVALID_URL", ErrorCodes.INVALID_URL.name(), errorEntities.get(0).getName());
        Assert.assertEquals("Error description matches", ErrorCodes.INVALID_URL.getDescription(), errorEntities.get(0).getDescription());

        Assert.assertEquals("Error 2 is MAX_PIN_URL_LENGTH_EXCEEDED", ErrorCodes.MAX_PIN_URL_LENGTH_EXCEEDED.name(), errorEntities.get(1).getName());
        Assert.assertEquals("Error description matches", "url length cannot exceed 300 characters", errorEntities.get(1).getDescription());

        StringBuilder invalidName  = new StringBuilder();
        for(int i = 0; i < Constants.PIN_NAME_MAX_LENGTH + 1; i++){
            invalidName.append("a");
        }

        StringBuilder invalidDescription = new StringBuilder();
        for(int i = 0; i < Constants.PIN_DESCRIPTION_MAX_LENGTH + 1; i++) {
            invalidDescription.append("b");
        }

        pin = new Pin("PINE7W7VETWZAKE0", invalidName.toString(),invalidDescription.toString(), invalidUrl.toString());

        errorEntities = pinValidationService.validate(pin);
        Assert.assertEquals("Error count is 2", 4, errorEntities.size());

        Assert.assertEquals("Error 1 is INVALID_URL", ErrorCodes.INVALID_URL.name(), errorEntities.get(0).getName());
        Assert.assertEquals("Error description matches", ErrorCodes.INVALID_URL.getDescription(), errorEntities.get(0).getDescription());

        Assert.assertEquals("Error 2 is MAX_PIN_URL_LENGTH_EXCEEDED", ErrorCodes.MAX_PIN_URL_LENGTH_EXCEEDED.name(), errorEntities.get(1).getName());
        Assert.assertEquals("Error description matches", "url length cannot exceed 300 characters", errorEntities.get(1).getDescription());


        Assert.assertEquals("Error 3 is MAX_PIN_NAME_LENGTH_EXCEEDED", ErrorCodes.MAX_PIN_NAME_LENGTH_EXCEEDED.name(), errorEntities.get(2).getName());
        Assert.assertEquals("Error description matches", "name length cannot exceed 50 characters", errorEntities.get(2).getDescription());

        Assert.assertEquals("Error 2 is MAX_PIN_DESC_LENGTH_EXCEEDED", ErrorCodes.MAX_PIN_DESC_LENGTH_EXCEEDED.name(), errorEntities.get(3).getName());
        Assert.assertEquals("Error description matches", "description length cannot exceed 2,000 characters", errorEntities.get(3).getDescription());

    }
}
