package org.personalized.dashboard.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * Created by sudan on 26/5/15.
 */

@Controller
@Scope("request")
@Path("/health")
public class HealthController {

    @GET
    @Path("/ping")
    public String checkServerStatus() {
        return "pong";
    }
}
