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

    /**
     * Get the todo for the todoId
     * @param todoId
     * @return
     */
    Todo getTodo(String todoId);

    /**
     * Updates the todo
     * @param todo
     * @return
     */
    Long updateTodo(Todo todo);

    /**
     * Delete the todo
     * @param todoId
     * @return
     */
    void deleteTodo(String todoId);
}
