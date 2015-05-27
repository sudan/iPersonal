package org.personalized.dashboard.bootstrap;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import org.personalized.dashboard.dao.api.*;
import org.personalized.dashboard.dao.impl.*;
import org.personalized.dashboard.service.api.*;
import org.personalized.dashboard.service.impl.*;
import org.personalized.dashboard.utils.generator.IdGenerator;
import org.personalized.dashboard.utils.validator.BookmarkValidationService;
import org.personalized.dashboard.utils.validator.ValidationService;

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
        bind(ExpenseService.class).to(ExpenseServiceImpl.class);

        bind(BookmarkDao.class).to(BookmarkDaoImpl.class);
        bind(DiaryDao.class).to(DiaryDaoImpl.class);
        bind(NoteDao.class).to(NoteDaoImpl.class);
        bind(PinDao.class).to(PinDaoImpl.class);
        bind(TodoDao.class).to(TodoDaoImpl.class);
        bind(ExpenseDao.class).to(ExpenseDaoImpl.class);

        bind(ValidationService.class).annotatedWith(Names.named("bookmark")).to(BookmarkValidationService.class);

        bind(IdGenerator.class).asEagerSingleton();
    }
}
