package org.personalized.dashboard.dao;

import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.personalized.dashboard.bootstrap.MongoBootstrap;
import org.personalized.dashboard.dao.api.*;
import org.personalized.dashboard.dao.impl.*;
import org.personalized.dashboard.model.*;
import org.personalized.dashboard.utils.ConfigKeys;
import org.personalized.dashboard.utils.Constants;
import org.personalized.dashboard.utils.generator.IdGenerator;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

/**
 * Created by sudan on 12/7/15.
 */
@ActiveProfiles("test")
public class TagDaoTest {

    private BookmarkDao bookmarkDao;
    private NoteDao noteDao;
    private PinDao pinDao;
    private TodoDao todoDao;
    private TagDao tagDao;

    private Bookmark bookmark1;
    private Bookmark bookmark2;
    private Note note1;
    private Note note2;
    private Pin pin1;
    private Pin pin2;
    private Todo todo1;
    private Todo todo2;

    @Before
    public void initialize() {
        this.bookmarkDao = new BookmarkDaoImpl(new IdGenerator());
        this.noteDao = new NoteDaoImpl(new IdGenerator());
        this.pinDao = new PinDaoImpl(new IdGenerator());
        this.todoDao = new TodoDaoImpl(new IdGenerator());
        this.tagDao = new TagDaoImpl();
    }

    @Test
    public void testTagDao() {

        Boolean isDebugMode = Boolean.valueOf(ConfigKeys.MONGO_DEBUG_FLAG);

        /*
            To run these test cases enable isDebugMode in config.properties

         */
        if(isDebugMode) {
            insertSampleData();

            Assert.assertEquals("Bookmark1 tag 0", "bookmark1", bookmark1.getTags().get(0));
            Assert.assertEquals("Bookmark2 tag 0", "bookmark1", bookmark2.getTags().get(0));
            Assert.assertEquals("Bookmark2 tag 1", "bookmark2", bookmark2.getTags().get(1));

            Assert.assertEquals("Note1 tag 0", "note1", note1.getTags().get(0));
            Assert.assertEquals("Note2 tag 0", "note1", note2.getTags().get(0));
            Assert.assertEquals("Note2 tag 1", "note2", note2.getTags().get(1));

            Assert.assertEquals("Pin1 tag 0", "pin1", pin1.getTags().get(0));
            Assert.assertEquals("Pin2 tag 0", "pin1", pin2.getTags().get(0));
            Assert.assertEquals("Pin2 tag 1", "pin2", pin2.getTags().get(1));

            Assert.assertEquals("Todo1 tag 0", "todo1", todo1.getTags().get(0));
            Assert.assertEquals("Todo2 tag 0", "todo1", todo2.getTags().get(0));
            Assert.assertEquals("Todo2 tag 1", "todo2", todo2.getTags().get(1));

            List<Bookmark> bookmarks = bookmarkDao.get(5,0, "1");
            List<Note> notes = noteDao.get(5,0, "1");
            List<Pin> pins = pinDao.get(5,0,"1");
            List<Todo> todos = todoDao.get(5,0, "1");

            Assert.assertEquals("Bookmark1 tag 0", "bookmark1", bookmarks.get(1).getTags().get(0));
            Assert.assertEquals("Bookmark2 tag 0", "bookmark1", bookmarks.get(0).getTags().get(0));
            Assert.assertEquals("Bookmark2 tag 1", "bookmark2", bookmarks.get(0).getTags().get(1));

            Assert.assertEquals("Note1 tag 0", "note1", notes.get(1).getTags().get(0));
            Assert.assertEquals("Note2 tag 0", "note1", notes.get(0).getTags().get(0));
            Assert.assertEquals("Note2 tag 1", "note2", notes.get(0).getTags().get(1));

            Assert.assertEquals("Pin1 tag 0", "pin1", pins.get(1).getTags().get(0));
            Assert.assertEquals("Pin2 tag 0", "pin1", pins.get(0).getTags().get(0));
            Assert.assertEquals("Pin2 tag 1", "pin2", pins.get(0).getTags().get(1));

            Assert.assertEquals("Todo1 tag 0", "todo1", todos.get(1).getTags().get(0));
            Assert.assertEquals("Todo2 tag 0", "todo1", todos.get(0).getTags().get(0));
            Assert.assertEquals("Todo2 tag 1", "todo2", todos.get(0).getTags().get(1));

        }
    }

    public void insertSampleData() {

        MongoBootstrap.init();
        MongoBootstrap.getMongoDatabase().getCollection(Constants.BOOKMARKS).drop();
        MongoBootstrap.getMongoDatabase().getCollection(Constants.NOTES).drop();
        MongoBootstrap.getMongoDatabase().getCollection(Constants.PINS).drop();
        MongoBootstrap.getMongoDatabase().getCollection(Constants.TODOS).drop();

        bookmark1 = new Bookmark();
        bookmark1.setName("name1");
        bookmark1.setDescription("desc1");
        bookmark1.setUrl("http://www.google.com");
        String bookmarkId1 = bookmarkDao.create(bookmark1, "1");

        bookmark2 = new Bookmark();
        bookmark2.setName("name2");
        bookmark2.setDescription("desc2");
        bookmark2.setUrl("http://www.bing.com");
        String bookmarkId2 = bookmarkDao.create(bookmark2, "1");

        note1 = new Note();
        note1.setTitle("title1");
        note1.setNote("note1");
        String noteId1 = noteDao.create(note1, "1");

        note2 = new Note();
        note2.setTitle("title2");
        note2.setNote("note2");
        String noteId2 = noteDao.create(note2, "1");

        pin1 = new Pin();
        pin1.setName("pin1");
        pin1.setDescription("desc1");
        pin1.setImageUrl("http://www.google.com.png");
        String pinId1 = pinDao.create(pin1, "1");

        pin2 = new Pin();
        pin2.setName("pin2");
        pin2.setDescription("desc2");
        pin2.setImageUrl("http://www.yahoo.com.png");
        String pinId2 = pinDao.create(pin2, "1");

        todo1 = new Todo();
        List<Task> tasks = Lists.newArrayList();
        Task task = new Task();
        task.setName("name1");
        task.setTask("task2");
        task.setPriority(Priority.MEDIUM);
        task.setPercentCompletion(10);
        tasks.add(task);
        todo1.setTitle("title1");
        todo1.setTasks(tasks);
        String todoId1 = todoDao.create(todo1, "1");

        todo2 = new Todo();
        todo2.setTitle("title2");
        todo2.setTasks(tasks);
        String todoId2 = todoDao.create(todo2, "1");

        Entity entity1 = new Entity(EntityType.BOOKMARK, bookmarkId1, "name1");
        Entity entity2 = new Entity(EntityType.BOOKMARK, bookmarkId2, "name2");
        Entity entity3 = new Entity(EntityType.NOTE, noteId1, "title1");
        Entity entity4 = new Entity(EntityType.NOTE, noteId2, "title2");
        Entity entity5 = new Entity(EntityType.PIN, pinId1, "pin1");
        Entity entity6 = new Entity(EntityType.PIN, pinId2, "pin2");
        Entity entity7 = new Entity(EntityType.TODO, todoId1, "name1");
        Entity entity8 = new Entity(EntityType.TODO, todoId2, "name2");

        List<String> tag1 = Lists.newArrayList();
        tag1.add("bookmark1");

        List<String> tag2 = Lists.newArrayList();
        tag2.add("bookmark1");
        tag2.add("bookmark2");

        List<String> tag3 = Lists.newArrayList();
        tag3.add("note1");

        List<String> tag4 = Lists.newArrayList();
        tag4.add("note1");
        tag4.add("note2");

        List<String> tag5 = Lists.newArrayList();
        tag5.add("pin1");

        List<String> tag6 = Lists.newArrayList();
        tag6.add("pin1");
        tag6.add("pin2");

        List<String> tag7 = Lists.newArrayList();
        tag7.add("todo1");

        List<String> tag8 = Lists.newArrayList();
        tag8.add("todo1");
        tag8.add("todo2");

        tagDao.update(tag1, entity1, "1");
        tagDao.update(tag2, entity2, "1");
        tagDao.update(tag3, entity3, "1");
        tagDao.update(tag4, entity4, "1");
        tagDao.update(tag5, entity5, "1");
        tagDao.update(tag6, entity6, "1");
        tagDao.update(tag7, entity7, "1");
        tagDao.update(tag8, entity8, "1");

        bookmark1 = bookmarkDao.get(bookmarkId1, "1");
        bookmark2 = bookmarkDao.get(bookmarkId2, "1");
        note1 = noteDao.get(noteId1, "1");
        note2 = noteDao.get(noteId2, "1");
        pin1 = pinDao.get(pinId1, "1");
        pin2 = pinDao.get(pinId2, "1");
        todo1 = todoDao.get(todoId1, "1");
        todo2 = todoDao.get(todoId2, "1");
    }
}
