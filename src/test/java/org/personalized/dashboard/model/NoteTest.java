package org.personalized.dashboard.model;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

/**
 * Created by sudan on 9/4/15.
 */

@ActiveProfiles("test")
public class NoteTest {

    @Test
    public void noteEntityTest() {
        Note note = new Note("NOT12345", "todo", "my todo today is to walk", "USR12345");

        Assert.assertEquals("Note ID is NOT12345", "NOT12345", note.getNoteId());
        Assert.assertEquals("Title for the note is todo", "todo", note.getTitle());
        Assert.assertEquals("Note is 'my todo today is to walk'", "my todo today is to walk", note.getNote());
        Assert.assertEquals("UserID is USR12345", "USR12345", note.getUserId());
        Assert.assertNull("CreatedOn is null on creation.Hence only Data Layer can set it",note.getCreatedOn());
        Assert.assertNull("modifiedAt is null.Hence only data layer can set it", note.getModifiedAt());

        note.setNoteId("NOT12346");
        note.setTitle("todolist");
        note.setNote("todo list has changed");
        note.setUserId("USR12346");

        Assert.assertEquals("Note ID is NOT12346", "NOT12346", note.getNoteId());
        Assert.assertEquals("Title for the note is todolist", "todolist", note.getTitle());
        Assert.assertEquals("Note is 'todo list has changed'", "todo list has changed", note.getNote());
        Assert.assertEquals("UserID is USR12346", "USR12346", note.getUserId());

    }
}
