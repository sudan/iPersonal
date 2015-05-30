package org.personalized.dashboard.service.impl;

import com.google.inject.Inject;
import org.personalized.dashboard.dao.api.NoteDao;
import org.personalized.dashboard.model.Note;
import org.personalized.dashboard.service.api.NoteService;
import org.personalized.dashboard.utils.auth.SessionManager;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sudan on 3/4/15.
 */
@Repository
public class NoteServiceImpl implements NoteService {

    private final NoteDao noteDao;
    private final SessionManager sessionManager;

    @Inject
    public NoteServiceImpl(NoteDao noteDao, SessionManager sessionManager){
        this.noteDao = noteDao;
        this.sessionManager = sessionManager;
    }

    @Override
    public String createNote(Note note) {
        return noteDao.create(note, sessionManager.getUserIdFromSession());
    }

    @Override
    public Note getNote(String noteId) {
        return noteDao.get(noteId, sessionManager.getUserIdFromSession());
    }

    @Override
    public Long updateNote(Note note) {
        return noteDao.update(note, sessionManager.getUserIdFromSession());
    }

    @Override
    public void deleteNote(String noteId) {
        noteDao.delete(noteId, sessionManager.getUserIdFromSession());
    }

    @Override
    public Long countNotes() {
        return noteDao.count(sessionManager.getUserIdFromSession());
    }

    @Override
    public List<Note> fetchNotes(int limit, int offset) {
        return noteDao.get(limit, offset, sessionManager.getUserIdFromSession());
    }
}
