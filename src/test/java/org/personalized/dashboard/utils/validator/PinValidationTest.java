package org.personalized.dashboard.utils.validator;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

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
        // TODO this will be revisited again once the validation is refactored completely

        /*
        Pin pin = new Pin("PINE7W7VETWZAKE0", "pin","desc","http://www.google.com");

        List<ErrorEntity> errorEntities = pinValidationService.validate(pin);
        Assert.assertEquals("Error count is 0", 0, errorEntities.size());

        StringBuilder invalidUrl = new StringBuilder();
        for(int i = 0; i < Constants.URL_MAX_LENGTH + 1; i++) {
            invalidUrl.append("a");
        }

        pin = new Pin("PINE7W7VETWZAKE0", "pin","desc", invalidUrl.toString());
        errorEntities = pinValidationService.validate(pin);
        Assert.assertEquals("Error count is 2", 2, errorEntities.size());

        Assert.assertEquals("Error 1 is INVALID_URL", ErrorCodes.INVALID_URL.name(), errorEntities.get(0).getName());
        Assert.assertEquals("Error description matches", ErrorCodes.INVALID_URL.getDescription(), errorEntities.get(0).getDescription());

        Assert.assertEquals("Error 2 is MAX_PIN_URL_LENGTH_EXCEEDED", ErrorCodes.LENGTH_EXCEEDED.name(), errorEntities.get(1).getName());
        Assert.assertEquals("Error description matches", "url length cannot exceed 300 characters", errorEntities.get(1).getDescription());

        StringBuilder invalidName  = new StringBuilder();
        for(int i = 0; i < Constants.TITLE_MAX_LENGTH + 1; i++){
            invalidName.append("a");
        }

        StringBuilder invalidDescription = new StringBuilder();
        for(int i = 0; i < Constants.CONTENT_MAX_LENGTH + 1; i++) {
            invalidDescription.append("b");
        }

        pin = new Pin("PINE7W7VETWZAKE0", invalidName.toString(),invalidDescription.toString(), invalidUrl.toString());

        errorEntities = pinValidationService.validate(pin);
        Assert.assertEquals("Error count is 2", 4, errorEntities.size());

        Assert.assertEquals("Error 1 is INVALID_URL", ErrorCodes.INVALID_URL.name(), errorEntities.get(0).getName());
        Assert.assertEquals("Error description matches", ErrorCodes.INVALID_URL.getDescription(), errorEntities.get(0).getDescription());

        Assert.assertEquals("Error 2 is MAX_PIN_URL_LENGTH_EXCEEDED", ErrorCodes.LENGTH_EXCEEDED.name(), errorEntities.get(1).getName());
        Assert.assertEquals("Error description matches", "url length cannot exceed 300 characters", errorEntities.get(1).getDescription());


        Assert.assertEquals("Error 3 is MAX_PIN_NAME_LENGTH_EXCEEDED", ErrorCodes.LENGTH_EXCEEDED.name(), errorEntities.get(2).getName());
        Assert.assertEquals("Error description matches", "name length cannot exceed 50 characters", errorEntities.get(2).getDescription());

        Assert.assertEquals("Error 2 is MAX_PIN_DESC_LENGTH_EXCEEDED", ErrorCodes.LENGTH_EXCEEDED.name(), errorEntities.get(3).getName());
        Assert.assertEquals("Error description matches", "description length cannot exceed 1,000 characters", errorEntities.get(3).getDescription());
        */
    }
}
