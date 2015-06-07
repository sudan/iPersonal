package org.personalized.dashboard.model;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

/**
 * Created by sudan on 5/6/15.
 */
@ActiveProfiles("test")
public class ActivityTest {

    @Test
    public void testActivityEntity() {

        Entity entity = new Entity(EntityType.BOOKMARK, "BOK123456789");
        Activity activity = new Activity("ACT123456789", ActivityType.CREATED, entity, "desc");

        Assert.assertEquals("activity ID is ACT123456789", "ACT123456789", activity.getActivityId
                ());
        Assert.assertEquals("Entity Type is Bookmark", EntityType.BOOKMARK.name(), entity
                .getEntityType().name());
        Assert.assertEquals("Entity ID is BOK123456789", "BOK123456789", entity.getEntityId());
        Assert.assertEquals("Activity Type is CREATED", ActivityType.CREATED.name(), activity
                .getActivityType().name());
        Assert.assertEquals("Activity desc is desc", "desc", activity.getDescription());

        activity.setActivityId("ACT234567891");
        activity.setActivityType(ActivityType.UPDATED);
        activity.setDescription("desc1");

        entity.setEntityId("TOD123456789");
        entity.setEntityType(EntityType.TODO);
        activity.setEntity(entity);

        Assert.assertEquals("activity ID is ACT234567891", "ACT234567891", activity.getActivityId
                ());
        Assert.assertEquals("Entity Type is Todo", EntityType.TODO.name(), entity.getEntityType()
                .name());
        Assert.assertEquals("Entity ID is TOD123456789", "TOD123456789", entity.getEntityId());
        Assert.assertEquals("Activity Type is UPDATED", ActivityType.UPDATED.name(), activity
                .getActivityType().name());
        Assert.assertEquals("Activity desc is desc1", "desc1", activity.getDescription());
    }
}
