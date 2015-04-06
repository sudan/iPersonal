package org.personalized.dashboard.service.impl;

import com.google.inject.Inject;
import org.personalized.dashboard.dao.api.TodoDao;
import org.personalized.dashboard.service.api.TodoService;
import org.springframework.stereotype.Repository;

/**
 * Created by sudan on 3/4/15.
 */
@Repository
public class TodoServiceImpl implements TodoService {

    private final TodoDao todoDao;

    @Inject
    public TodoServiceImpl(TodoDao todoDao){
        this.todoDao = todoDao;
    }
}
