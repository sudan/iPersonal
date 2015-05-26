package org.personalized.dashboard.controller;

import com.google.inject.Inject;
import org.personalized.dashboard.model.Bookmark;
import org.personalized.dashboard.service.api.BookmarkService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
        bookmarkService.createBookmark(bookmark);
        return Response.status(Response.Status.CREATED).build();
    }
}
