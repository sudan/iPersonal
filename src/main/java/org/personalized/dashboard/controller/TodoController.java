package org.personalized.dashboard.controller;

import com.google.inject.Inject;
import org.personalized.dashboard.service.api.TodoService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.ws.rs.Path;

/**
 * Created by sudan on 3/4/15.
 */

@Controller
@Scope("request")
@Path("/todo")
public class TodoController {

    private final TodoService todoService;

    @Inject
    public TodoController(TodoService todoService){
        this.todoService = todoService;
    }
}
