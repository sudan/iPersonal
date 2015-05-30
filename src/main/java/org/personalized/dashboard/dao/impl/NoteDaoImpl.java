package org.personalized.dashboard.dao.impl;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.personalized.dashboard.bootstrap.MongoBootstrap;
import org.personalized.dashboard.dao.api.NoteDao;
import org.personalized.dashboard.model.Note;
import org.personalized.dashboard.utils.Constants;
import org.personalized.dashboard.utils.generator.IdGenerator;

import java.util.List;

import static com.mongodb.client.model.Filters.*;

/**
 * Created by sudan on 3/4/15.
 */
public class NoteDaoImpl implements NoteDao {

    private IdGenerator idGenerator;

    @Inject
    public NoteDaoImpl(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public String create(Note note, String userId) {
        MongoCollection<Document> collection = MongoBootstrap.getMongoDatabase().getCollection(Constants.NOTES);

        String noteId = idGenerator.generateId(Constants.NOTE_PREFIX, Constants.ID_LENGTH);
        Document document = new Document()
                .append(Constants.PRIMARY_KEY, noteId)
                .append(Constants.NOTE_TITLE, note.getTitle())
                .append(Constants.NOTE_CONTENT, note.getNote())
                .append(Constants.USER_ID, userId)
                .append(Constants.CREATED_ON, System.currentTimeMillis())
                .append(Constants.MODIFIED_AT, System.currentTimeMillis());
        collection.insertOne(document);

        return noteId;
    }

    @Override
    public Note get(String noteId, String userId) {
        MongoCollection<Document> collection = MongoBootstrap.getMongoDatabase().getCollection(Constants.NOTES);
        Document document = collection.find(and
                        (
                                eq(Constants.PRIMARY_KEY, noteId),
                                eq(Constants.USER_ID, userId),
                                ne(Constants.IS_DELETED, true)
                        )
        ).first();

        if(document != null) {
            Note note = new Note();
            note.setNoteId(document.getString(Constants.PRIMARY_KEY));
            note.setTitle(document.getString(Constants.NOTE_TITLE));
            note.setNote(document.getString(Constants.NOTE_CONTENT));
            note.setCreatedOn(document.getLong(Constants.CREATED_ON));
            note.setModifiedAt(document.getLong(Constants.MODIFIED_AT));
            return note;
        }
        return null;

    }

    @Override
    public Long update(Note note, String userId) {
        MongoCollection<Document> collection = MongoBootstrap.getMongoDatabase().getCollection(Constants.NOTES);
        Document document = new Document()
                .append(Constants.NOTE_TITLE, note.getTitle())
                .append(Constants.NOTE_CONTENT, note.getNote())
                .append(Constants.MODIFIED_AT, System.currentTimeMillis());

        UpdateResult updateResult = collection.updateOne(
                and(
                        eq(Constants.PRIMARY_KEY, note.getNoteId()),
                        eq(Constants.USER_ID, userId),
                        ne(Constants.IS_DELETED, true)
                ),
                new Document(Constants.SET_OPERATION, document)
        );
        return updateResult.getModifiedCount();

    }

    @Override
    public Long delete(String noteId, String userId) {
        MongoCollection<Document> collection = MongoBootstrap.getMongoDatabase().getCollection(Constants.NOTES);

        Document document = new Document()
                .append(Constants.IS_DELETED, true);

        UpdateResult updateResult = collection.updateOne(
                and(
                        eq(Constants.PRIMARY_KEY, noteId),
                        eq(Constants.USER_ID, userId)
                ),
                new Document(Constants.SET_OPERATION, document)
        );
        return updateResult.getModifiedCount();

    }

    @Override
    public Long count(String userId) {
        MongoCollection<Document> collection = MongoBootstrap.getMongoDatabase().getCollection(Constants.NOTES);

        return collection.count(
                and(
                        eq(Constants.USER_ID, userId),
                        ne(Constants.IS_DELETED, true)
                )
        );

    }

    @Override
    public List<Note> get(int limit, int offset, String userId) {
        MongoCollection<Document> collection = MongoBootstrap.getMongoDatabase().getCollection(Constants.NOTES);

        FindIterable<Document> iterator = collection.find(and
                        (
                                eq(Constants.USER_ID, userId),
                                ne(Constants.IS_DELETED, true)
                        )
        ).skip(offset).limit(limit).sort(
                new Document(Constants.MODIFIED_AT, -1)
        );

        final List<Note> notes = Lists.newArrayList();
        iterator.forEach(new Block<Document>() {
            @Override
            public void apply(Document document) {
                Note note = new Note();
                note.setNoteId(document.getString(Constants.PRIMARY_KEY));
                note.setTitle(document.getString(Constants.NOTE_TITLE));
                note.setNote(document.getString(Constants.NOTE_CONTENT));
                note.setCreatedOn(document.getLong(Constants.CREATED_ON));
                note.setModifiedAt(document.getLong(Constants.MODIFIED_AT));
                notes.add(note);
            }
        });
        return notes;

    }
}
