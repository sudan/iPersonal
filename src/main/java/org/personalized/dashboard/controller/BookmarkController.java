package org.personalized.dashboard.controller;

import com.google.inject.Inject;
import org.personalized.dashboard.service.api.BookmarkService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.ws.rs.Path;

/**
 * Created by sudan on 3/4/15.
 */

@Controller
@Scope("request")
@Path("/bookmark")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @Inject
    public BookmarkController(BookmarkService bookmarkService){
        this.bookmarkService = bookmarkService;
    }
}
