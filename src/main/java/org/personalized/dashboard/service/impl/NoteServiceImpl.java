package org.personalized.dashboard.service.impl;

import com.google.inject.Inject;
import org.personalized.dashboard.dao.api.NoteDao;
import org.personalized.dashboard.service.api.NoteService;
import org.springframework.stereotype.Repository;

/**
 * Created by sudan on 3/4/15.
 */
@Repository
public class NoteServiceImpl implements NoteService {

    private final NoteDao noteDao;

    @Inject
    public NoteServiceImpl(NoteDao noteDao){
        this.noteDao = noteDao;
    }

}
