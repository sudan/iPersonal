package org.personalized.dashboard.controller;

import com.google.inject.Inject;
import org.personalized.dashboard.model.Bookmark;
import org.personalized.dashboard.service.api.BookmarkService;
import org.personalized.dashboard.utils.validator.ErrorEntity;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by sudan on 3/4/15.
 */

@Controller
@Scope("request")
@Path("/bookmarks")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @Inject
    public BookmarkController(BookmarkService bookmarkService){
        this.bookmarkService = bookmarkService;
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBookmark(Bookmark bookmark) {
        List<ErrorEntity> errorEntities = bookmarkService.createBookmark(bookmark);
        if(CollectionUtils.isEmpty(errorEntities)) {
            return Response.status(Response.Status.CREATED).build();
        }
        else{
            GenericEntity<List<ErrorEntity>> errorObj = new GenericEntity<List<ErrorEntity>>(errorEntities){};
            return Response.status(Response.Status.BAD_REQUEST).entity(errorObj).build();
        }
    }
}
