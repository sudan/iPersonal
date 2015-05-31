package org.personalized.dashboard.service.api;

import org.personalized.dashboard.model.Todo;

/**
 * Created by sudan on 3/4/15.
 */
public interface TodoService {

    /**
     * Create a new todo for the user
     * @param todo
     * @return
     */
    String createTodo(Todo todo);
}
