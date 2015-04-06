package org.personalized.dashboard.controller;

import com.google.inject.Inject;
import org.personalized.dashboard.service.api.PinService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.ws.rs.Path;

/**
 * Created by sudan on 3/4/15.
 */

@Controller
@Scope("request")
@Path("/pin")
public class PinController {

    private final PinService pinService;

    @Inject
    public PinController(PinService pinService){
        this.pinService = pinService;
    }
}
