package org.personalized.dashboard.dao.impl;

import com.google.inject.Inject;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.personalized.dashboard.bootstrap.MongoBootstrap;
import org.personalized.dashboard.dao.api.BookmarkDao;
import org.personalized.dashboard.model.Bookmark;
import org.personalized.dashboard.utils.Constants;
import org.personalized.dashboard.utils.generator.IdGenerator;

/**
 * Created by sudan on 3/4/15.
 */
public class BookmarkDaoImpl implements BookmarkDao {

    private IdGenerator idGenerator;

    @Inject
    public BookmarkDaoImpl(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public void create(Bookmark bookmark) {
        MongoCollection<Document> collection = MongoBootstrap.getMongoDatabase().getCollection(Constants.BOOKMARKS);

        Document document = new Document()
                .append(Constants.PRIMARY_KEY, idGenerator.generateId(Constants.BOOKMARK_PREFIX,Constants.ID_LENGTH))
                .append(Constants.BOOKMARK_NAME, bookmark.getName())
                .append(Constants.BOOKMARK_DESCRIPTION, bookmark.getDescription())
                .append(Constants.BOOKMARK_URL, bookmark.getUrl())
                .append(Constants.BOOKMARK_USER_ID, bookmark.getUserId())
                .append(Constants.BOOKMARK_CREATED_ON, System.currentTimeMillis())
                .append(Constants.BOOKMARK_MODIFIED_AT, System.currentTimeMillis());
        collection.insertOne(document);
    }
}
