package org.personalized.dashboard.dao.impl;

import com.google.inject.Inject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.personalized.dashboard.bootstrap.MongoBootstrap;
import org.personalized.dashboard.dao.api.UserDao;
import org.personalized.dashboard.model.User;
import org.personalized.dashboard.utils.Constants;
import org.personalized.dashboard.utils.FieldKeys;
import org.personalized.dashboard.utils.generator.IdGenerator;

import static com.mongodb.client.model.Filters.*;

/**
 * Created by sudan on 25/7/15.
 */
public class UserDaoImpl implements UserDao {

    private IdGenerator idGenerator;

    @Inject
    public UserDaoImpl(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public String create(User user) {
        MongoCollection<Document> collection = MongoBootstrap.getMongoDatabase().getCollection(Constants.USERS);

        String userId = idGenerator.generateId(Constants.USER_PREFIX, Constants.ID_LENGTH);
        Document document = new Document()
                .append(FieldKeys.PRIMARY_KEY, userId)
                .append(FieldKeys.USER_NAME, user.getUsername())
                .append(FieldKeys.USER_EMAIL, user.getEmail())
                .append(FieldKeys.USER_PROFILE_PIC_URL, user.getProfilePicURL())
                .append(FieldKeys.CREATED_ON, System.currentTimeMillis())
                .append(FieldKeys.MODIFIED_AT, System.currentTimeMillis());
        collection.insertOne(document);
        return userId;
    }

    @Override
    public User get(String userId) {
        MongoCollection<Document> collection = MongoBootstrap.getMongoDatabase().getCollection(Constants.USERS);

        Document document = collection.find(eq(FieldKeys.PRIMARY_KEY, userId)).first();

        if (document != null) {
            User user = new User();
            user.setUserId(document.getString(FieldKeys.PRIMARY_KEY));
            user.setUsername(document.getString(FieldKeys.USER_NAME));
            user.setEmail(document.getString(FieldKeys.USER_EMAIL));
            user.setProfilePicURL(document.getString(FieldKeys.USER_PROFILE_PIC_URL));
            return user;
        }
        return null;
    }

    @Override
    public Long update(User user) {

        MongoCollection<Document> collection = MongoBootstrap.getMongoDatabase().getCollection(Constants.USERS);

        Document document = new Document()
                .append(FieldKeys.USER_NAME, user.getUsername())
                .append(FieldKeys.USER_PROFILE_PIC_URL, user.getProfilePicURL())
                .append(FieldKeys.MODIFIED_AT, System.currentTimeMillis());

        UpdateResult updateResult = collection.updateOne(
                and(
                        eq(FieldKeys.PRIMARY_KEY, user.getUserId())

                ),
                new Document(Constants.SET_OPERATION, document)
        );
        return updateResult.getModifiedCount();
    }
}
