package org.personalized.dashboard.controller;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.apache.commons.lang3.StringUtils;
import org.personalized.dashboard.model.Todo;
import org.personalized.dashboard.service.api.TodoService;
import org.personalized.dashboard.utils.validator.ErrorEntity;
import org.personalized.dashboard.utils.validator.ValidationService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by sudan on 3/4/15.
 */

@Controller
@Scope("request")
@Path("/todos")
public class TodoController {

    private final TodoService todoService;
    private final ValidationService batchSizeValidationService;
    private final ValidationService todoValidationService;

    @Inject
    public TodoController(TodoService todoService,
                         @Named("batchSize") ValidationService batchSizeValidationService,
                         @Named("todo") ValidationService todoValidationService){
        this.todoService = todoService;
        this.batchSizeValidationService = batchSizeValidationService;
        this.todoValidationService = todoValidationService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTodo(Todo todo){
        List<ErrorEntity> errorEntities = todoValidationService.validate(todo);
        if(CollectionUtils.isEmpty(errorEntities)){
            String todoId = todoService.createTodo(todo);
            return Response.status(Response.Status.CREATED).entity(todoId).build();
        }
        else {
            GenericEntity<List<ErrorEntity>> errorObj = new GenericEntity<List<ErrorEntity>>(errorEntities){};
            return Response.status(Response.Status.BAD_REQUEST).entity(errorObj).build();
        }
    }

    @GET
    @Path("{todoId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTodo(@PathParam("todoId") String todoId) {
        if(StringUtils.isEmpty(todoId)){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        else {
            Todo todo = todoService.getTodo(todoId);
            if(todo == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            else {
                return Response.status(Response.Status.OK).entity(todo).build();
            }
        }
    }
}
