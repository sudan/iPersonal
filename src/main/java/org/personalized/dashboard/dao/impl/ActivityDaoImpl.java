package org.personalized.dashboard.dao.impl;

import com.google.inject.Inject;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.personalized.dashboard.bootstrap.MongoBootstrap;
import org.personalized.dashboard.dao.api.ActivityDao;
import org.personalized.dashboard.model.Activity;
import org.personalized.dashboard.utils.Constants;
import org.personalized.dashboard.utils.generator.IdGenerator;

/**
 * Created by sudan on 30/5/15.
 */
public class ActivityDaoImpl implements ActivityDao {

    private IdGenerator idGenerator;

    @Inject
    public ActivityDaoImpl(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public void add(Activity activity, String userId) {
        MongoCollection<Document> collection = MongoBootstrap.getMongoDatabase().getCollection(Constants.ACTIVITIES);

        String activityId = idGenerator.generateId(Constants.ACTIVITIES_PREFIX, Constants.ID_LENGTH);
        Document document = new Document()
                .append(Constants.PRIMARY_KEY, activityId)
                .append(Constants.ACTIVITY_TYPE, activity.getActivityType().name())
                .append(Constants.ENTITY_ID, activity.getEntity().getEntityId())
                .append(Constants.ENTITY_TYPE, activity.getEntity().getEntityType().name())
                .append(Constants.ACTIVITY_DESC, activity.getDescription())
                .append(Constants.USER_ID, userId)
                .append(Constants.MODIFIED_AT, System.currentTimeMillis());
        collection.insertOne(document);

    }
}
