package org.personalized.dashboard.dao.impl;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.personalized.dashboard.bootstrap.MongoBootstrap;
import org.personalized.dashboard.dao.api.PinDao;
import org.personalized.dashboard.model.Pin;
import org.personalized.dashboard.utils.Constants;
import org.personalized.dashboard.utils.generator.IdGenerator;

import java.util.List;

import static com.mongodb.client.model.Filters.*;

/**
 * Created by sudan on 3/4/15.
 */
public class PinDaoImpl implements PinDao {

    private IdGenerator idGenerator;

    @Inject
    public PinDaoImpl(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public String create(Pin pin, String userId) {
        MongoCollection<Document> collection = MongoBootstrap.getMongoDatabase().getCollection(Constants.PINS);

        String pinId = idGenerator.generateId(Constants.PIN_PREFIX, Constants.ID_LENGTH);
        Document document = new Document()
                .append(Constants.PRIMARY_KEY, pinId)
                .append(Constants.PIN_NAME, pin.getName())
                .append(Constants.PIN_DESCRIPTION, pin.getDescription())
                .append(Constants.PIN_IMAGE_URL, pin.getImageUrl())
                .append(Constants.USER_ID, userId)
                .append(Constants.CREATED_ON, System.currentTimeMillis())
                .append(Constants.MODIFIED_AT, System.currentTimeMillis());
        collection.insertOne(document);

        return pinId;
    }

    @Override
    public Pin get(String pinId, String userId) {
        MongoCollection<Document> collection = MongoBootstrap.getMongoDatabase().getCollection(Constants.PINS);
        Document document = collection.find(and
                        (
                                eq(Constants.PRIMARY_KEY, pinId),
                                eq(Constants.USER_ID, userId),
                                ne(Constants.IS_DELETED, true)
                        )
        ).first();

        if(document != null) {
            Pin pin = new Pin();
            pin.setPinId(document.getString(Constants.PRIMARY_KEY));
            pin.setName(document.getString(Constants.PIN_NAME));
            pin.setDescription(document.getString(Constants.PIN_DESCRIPTION));
            pin.setImageUrl(document.getString(Constants.PIN_IMAGE_URL));
            pin.setCreatedOn(document.getLong(Constants.CREATED_ON));
            pin.setModifiedAt(document.getLong(Constants.MODIFIED_AT));
            return pin;
        }
        return null;


    }

    @Override
    public Long update(Pin pin, String userId) {
        MongoCollection<Document> collection = MongoBootstrap.getMongoDatabase().getCollection(Constants.PINS);
        Document document = new Document()
                .append(Constants.PIN_NAME, pin.getName())
                .append(Constants.PIN_DESCRIPTION, pin.getDescription())
                .append(Constants.PIN_IMAGE_URL, pin.getImageUrl())
                .append(Constants.MODIFIED_AT, System.currentTimeMillis());

        UpdateResult updateResult = collection.updateOne(
                and(
                        eq(Constants.PRIMARY_KEY, pin.getPinId()),
                        eq(Constants.USER_ID, userId),
                        ne(Constants.IS_DELETED, true)
                ),
                new Document(Constants.SET_OPERATION, document)
        );
        return updateResult.getModifiedCount();

    }

    @Override
    public Long delete(String pinId, String userId) {
        MongoCollection<Document> collection = MongoBootstrap.getMongoDatabase().getCollection(Constants.PINS);

        Document document = new Document()
                .append(Constants.IS_DELETED, true);

        UpdateResult updateResult = collection.updateOne(
                and(
                        eq(Constants.PRIMARY_KEY, pinId),
                        eq(Constants.USER_ID, userId)
                ),
                new Document(Constants.SET_OPERATION, document)
        );
        return updateResult.getModifiedCount();

    }

    @Override
    public Long count(String userId) {
        MongoCollection<Document> collection = MongoBootstrap.getMongoDatabase().getCollection(Constants.PINS);

        return collection.count(
                and(
                        eq(Constants.USER_ID, userId),
                        ne(Constants.IS_DELETED, true)
                )
        );

    }

    @Override
    public List<Pin> get(int limit, int offset, String userId) {
        MongoCollection<Document> collection = MongoBootstrap.getMongoDatabase().getCollection(Constants.PINS);

        FindIterable<Document> iterator = collection.find(and
                        (
                                eq(Constants.USER_ID, userId),
                                ne(Constants.IS_DELETED, true)
                        )
        ).skip(offset).limit(limit).sort(
                new Document(Constants.MODIFIED_AT, -1)
        );

        final List<Pin> pins = Lists.newArrayList();
        iterator.forEach(new Block<Document>() {
            @Override
            public void apply(Document document) {
                Pin pin = new Pin();
                pin.setPinId(document.getString(Constants.PRIMARY_KEY));
                pin.setName(document.getString(Constants.PIN_NAME));
                pin.setDescription(document.getString(Constants.PIN_DESCRIPTION));
                pin.setImageUrl(document.getString(Constants.PIN_IMAGE_URL));
                pin.setCreatedOn(document.getLong(Constants.CREATED_ON));
                pin.setModifiedAt(document.getLong(Constants.MODIFIED_AT));
                pins.add(pin);
            }
        });
        return pins;

    }
}
