package org.personalized.dashboard.utils.generator;

import com.google.inject.Inject;
import org.personalized.dashboard.model.*;
import org.personalized.dashboard.utils.generator.IdGenerator;

/**
 * Created by sudan on 30/5/15.
 */
public class ActivityGenerator<T> {

    private final IdGenerator idGenerator;

    @Inject
    public ActivityGenerator(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }
    public Activity generate(ActivityType activityType, EntityType entityType, String entityId, String entityName) {

        StringBuilder description = new StringBuilder();
        description
                .append(entityName)
                .append(" has been ")
                .append(activityType.name());

        Activity activity = new Activity();
        activity.setDescription(description.toString());
        activity.setActivityType(activityType);
        activity.setEntity(new Entity(entityType, entityId));
        return activity;
    }
}
