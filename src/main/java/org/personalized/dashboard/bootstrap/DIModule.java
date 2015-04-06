package org.personalized.dashboard.bootstrap;

import com.google.inject.AbstractModule;
import org.personalized.dashboard.dao.api.*;
import org.personalized.dashboard.dao.impl.*;
import org.personalized.dashboard.service.api.*;
import org.personalized.dashboard.service.impl.*;

/**
 * Created by sudan on 5/4/15.
 */
public class DIModule extends AbstractModule {

    @Override
    protected void configure() {

        bind(BookmarkService.class).to(BookmarkServiceImpl.class);
        bind(DiaryService.class).to(DiaryServiceImpl.class);
        bind(NoteService.class).to(NoteServiceImpl.class);
        bind(PinService.class).to(PinServiceImpl.class);
        bind(TodoService.class).to(TodoServiceImpl.class);

        bind(BookmarkDao.class).to(BookmarkDaoImpl.class);
        bind(DiaryDao.class).to(DiaryDaoImpl.class);
        bind(NoteDao.class).to(NoteDaoImpl.class);
        bind(PinDao.class).to(PinDaoImpl.class);
        bind(TodoDao.class).to(TodoDaoImpl.class);
    }
}
