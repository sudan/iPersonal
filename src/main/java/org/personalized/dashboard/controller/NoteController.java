package org.personalized.dashboard.controller;

import com.google.inject.Inject;
import org.personalized.dashboard.service.api.NoteService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.ws.rs.Path;

/**
 * Created by sudan on 3/4/15.
 */

@Controller
@Scope("request")
@Path("/note")
public class NoteController {

    private final NoteService noteService;

    @Inject
    public NoteController(NoteService noteService){
        this.noteService = noteService;
    }
}
