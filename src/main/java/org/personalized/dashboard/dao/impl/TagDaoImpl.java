package org.personalized.dashboard.dao.impl;

import com.google.common.collect.Lists;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.personalized.dashboard.bootstrap.MongoBootstrap;
import org.personalized.dashboard.dao.api.TagDao;
import org.personalized.dashboard.model.Entity;
import org.personalized.dashboard.utils.Constants;
import org.personalized.dashboard.utils.FieldKeys;

import java.util.List;

import static com.mongodb.client.model.Filters.*;
/**
 * Created by sudan on 11/7/15.
 */
public class TagDaoImpl implements TagDao {

    @Override
    public Long update(List<String> tags, Entity entity, String userId) {

        MongoCollection<Document> collection = getCollection(entity);
        if(collection != null) {

           Document document = new Document()
                   .append(FieldKeys.ENTITY_TAGS, tags)
                   .append(FieldKeys.MODIFIED_AT, System.currentTimeMillis());

           UpdateResult updateResult = collection.updateOne(
                    and(
                        eq(FieldKeys.PRIMARY_KEY, entity.getEntityId()),
                        eq(FieldKeys.USER_ID, userId),
                        ne(FieldKeys.IS_DELETED, true)
                    ),
                    new Document(Constants.SET_OPERATION, document)
           );
           addToUserTags(userId, tags);
           return updateResult.getModifiedCount();
        }
        return 0L;
    }

    @Override
    public List<String> get(String userId) {
        MongoCollection<Document> collection = MongoBootstrap.getMongoDatabase().getCollection(Constants.USER_TAGS);
        Document document = collection.find(and(
                eq(FieldKeys.PRIMARY_KEY, userId)
        )).first();

        if(document == null) {
            return Lists.newArrayList();
        }
        else {
            return (List<String>) document.get(FieldKeys.ENTITY_TAGS);
        }
    }

    private MongoCollection<Document> getCollection(Entity entity) {

        switch (entity.getEntityType()) {

            case BOOKMARK:
                return MongoBootstrap.getMongoDatabase().getCollection(Constants.BOOKMARKS);
            case NOTE:
                return MongoBootstrap.getMongoDatabase().getCollection(Constants.NOTES);
            case PIN:
                return MongoBootstrap.getMongoDatabase().getCollection(Constants.PINS);
            case TODO:
                return MongoBootstrap.getMongoDatabase().getCollection(Constants.TODOS);
        }
        return null;
    }

    private void addToUserTags(String userId, List<String> tags) {

        MongoCollection<Document> collection = MongoBootstrap.getMongoDatabase().getCollection(Constants.USER_TAGS);

        Document nestedDocument = new Document(Constants.EACH, tags);
        Document document = new Document(FieldKeys.ENTITY_TAGS, nestedDocument);

        UpdateOptions updateOptions = new UpdateOptions();
        updateOptions.upsert(true);
        collection.updateOne(eq(FieldKeys.PRIMARY_KEY, userId),
                new Document(Constants.ADD_TO_SET_OPERATION, document), updateOptions);

    }
}
