package org.personalized.dashboard.controller;

import com.google.inject.Inject;
import org.personalized.dashboard.service.api.DiaryService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.ws.rs.Path;

/**
 * Created by sudan on 3/4/15.
 */

@Controller
@Scope("request")
@Path("/diary")
public class DiaryController {

    private final DiaryService diaryService;

    @Inject
    public DiaryController(DiaryService diaryService){
        this.diaryService = diaryService;
    }
}
