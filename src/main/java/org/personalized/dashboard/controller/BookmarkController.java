package org.personalized.dashboard.controller;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.apache.commons.lang3.StringUtils;
import org.personalized.dashboard.model.Bookmark;
import org.personalized.dashboard.service.api.BookmarkService;
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
@Path("/bookmarks")
public class BookmarkController {

    private final BookmarkService bookmarkService;
    private final ValidationService bookmarkValidationService;

    @Inject
    public BookmarkController(BookmarkService bookmarkService, @Named("bookmark") ValidationService bookmarkValidationService){
        this.bookmarkService = bookmarkService;
        this.bookmarkValidationService = bookmarkValidationService;
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBookmark(Bookmark bookmark) {
        List<ErrorEntity> errorEntities = bookmarkValidationService.validate(bookmark) ;
        if(CollectionUtils.isEmpty(errorEntities)) {
            String bookmarkId = bookmarkService.createBookmark(bookmark);
            return Response.status(Response.Status.CREATED).entity(bookmarkId).build();
        }
        else{
            GenericEntity<List<ErrorEntity>> errorObj = new GenericEntity<List<ErrorEntity>>(errorEntities){};
            return Response.status(Response.Status.BAD_REQUEST).entity(errorObj).build();
        }
    }

    @GET
    @Path("{bookmarkId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookmark(@PathParam("bookmarkId") String bookmarkId) {
        if(StringUtils.isEmpty(bookmarkId)){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        else {
            Bookmark bookmark = bookmarkService.getBookmark(bookmarkId);
            if(bookmark == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            else {
                return Response.status(Response.Status.OK).entity(bookmark).build();
            }
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBookmark(Bookmark bookmark) {
        List<ErrorEntity> errorEntities = bookmarkValidationService.validate(bookmark) ;
        if(CollectionUtils.isEmpty(errorEntities)) {
            Bookmark updatedBookmark = bookmarkService.updateBookmark(bookmark);
            return Response.status(Response.Status.OK).entity(updatedBookmark).build();
        }
        else {
            GenericEntity<List<ErrorEntity>> errorObj = new GenericEntity<List<ErrorEntity>>(errorEntities){};
            return Response.status(Response.Status.BAD_REQUEST).entity(errorObj).build();
        }
    }

    @DELETE
    @Path("{bookmarkId}")
    public Response deleteBookmark(@PathParam("bookmarkId") String bookmarkId) {
        if(StringUtils.isEmpty(bookmarkId)){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        else {
            bookmarkService.deleteBookmark(bookmarkId);
            return Response.status(Response.Status.OK).build();
        }
    }

    @GET
    @Path("count")
    @Produces(MediaType.APPLICATION_JSON)
    public Response countBookmarks() {
        Long count = bookmarkService.countBookmarks();
        return Response.status(Response.Status.OK).entity(String.valueOf(count)).build();
    }
}
