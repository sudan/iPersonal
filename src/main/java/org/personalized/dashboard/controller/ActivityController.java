package org.personalized.dashboard.controller;

import com.google.inject.Inject;
import org.personalized.dashboard.model.Activity;
import org.personalized.dashboard.service.api.ActivityService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by sudan on 31/5/15.
 */
@Component
@Scope("request")
@Path("activities")
public class ActivityController {

    private final ActivityService activityService;

    @Inject
    public ActivityController(ActivityService activityService){
        this.activityService = activityService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getActivities() {
        List<Activity> activities  = activityService.get();
        GenericEntity<List<Activity>> activityListObj = new GenericEntity<List<Activity>>(activities ){};
        return Response.status(Response.Status.OK).entity(activityListObj).build();
    }
}

