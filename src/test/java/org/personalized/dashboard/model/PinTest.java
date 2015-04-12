package org.personalized.dashboard.model;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

/**
 * Created by sudan on 12/4/15.
 */
@ActiveProfiles("test")
public class PinTest {

    @Test
    public void pinEntityTest(){

        Pin pin = new Pin("PIN123456789", "pin", "description", "http://www.google.com", "USR123456789");
        Assert.assertEquals("Pin ID is PIN123456789", "PIN123456789", pin.getPinId());
        Assert.assertEquals("pin name is pin", "pin", pin.getName());
        Assert.assertEquals("pin description is description", "description", pin.getDescription());
        Assert.assertEquals("pin imageurl is http://www.google.com","http://www.google.com", pin.getImageUrl());
        Assert.assertEquals("pin userId is USR123456789", "USR123456789", pin.getUserId());
        Assert.assertNull("CreatedOn is null on creation.Hence only Data Layer can set it",pin.getCreatedOn());
        Assert.assertNull("modifiedAt is null.Hence only data layer can set it", pin.getModifiedAt());

        pin.setPinId("PIN123456798");
        pin.setName("pin1");
        pin.setDescription("description1");
        pin.setImageUrl("http://www.yahoo.com");
        pin.setUserId("USR123456798");

        Assert.assertEquals("Pin ID is PIN123456798", "PIN123456798", pin.getPinId());
        Assert.assertEquals("pin name is pin1", "pin1", pin.getName());
        Assert.assertEquals("pin description is description1", "description1", pin.getDescription());
        Assert.assertEquals("pin imageurl is http://www.yahoo.com","http://www.yahoo.com", pin.getImageUrl());
        Assert.assertEquals("pin userId is USR123456798", "USR123456798", pin.getUserId());
    }
}
