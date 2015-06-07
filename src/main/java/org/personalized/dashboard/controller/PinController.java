package org.personalized.dashboard.controller;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.apache.commons.lang3.StringUtils;
import org.personalized.dashboard.model.BatchSize;
import org.personalized.dashboard.model.Pin;
import org.personalized.dashboard.service.api.PinService;
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
@Path("/pins")
public class PinController {

    private final PinService pinService;
    private final ValidationService batchSizeValidationService;
    private final ValidationService pinValidationService;

    @Inject
    public PinController(PinService pinService,
                         @Named("pin") ValidationService pinValidationService,
                         @Named("batchSize") ValidationService batchSizeValidationService) {
        this.pinService = pinService;
        this.pinValidationService = pinValidationService;
        this.batchSizeValidationService = batchSizeValidationService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPin(Pin pin) {
        List<ErrorEntity> errorEntities = pinValidationService.validate(pin);
        if (CollectionUtils.isEmpty(errorEntities)) {
            String pinId = pinService.createPin(pin);
            return Response.status(Response.Status.CREATED).entity(pinId).build();
        } else {
            GenericEntity<List<ErrorEntity>> errorObj = new GenericEntity<List<ErrorEntity>>(errorEntities) {
            };
            return Response.status(Response.Status.BAD_REQUEST).entity(errorObj).build();
        }
    }

    @GET
    @Path("{pinId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPin(@PathParam("pinId") String pinId) {
        if (StringUtils.isEmpty(pinId)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            Pin pin = pinService.getPin(pinId);
            if (pin == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            } else {
                return Response.status(Response.Status.OK).entity(pin).build();
            }
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePin(Pin pin) {
        List<ErrorEntity> errorEntities = pinValidationService.validate(pin);
        if (CollectionUtils.isEmpty(errorEntities)) {
            Long modifiedCount = pinService.updatePin(pin);
            if (modifiedCount > 0) {
                return Response.status(Response.Status.OK).entity(pin).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        } else {
            GenericEntity<List<ErrorEntity>> errorObj = new GenericEntity<List<ErrorEntity>>(errorEntities) {
            };
            return Response.status(Response.Status.BAD_REQUEST).entity(errorObj).build();
        }
    }

    @DELETE
    @Path("{pinId}")
    public Response deletePin(@PathParam("pinId") String pinId) {
        if (StringUtils.isEmpty(pinId)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            pinService.deletePin(pinId);
            return Response.status(Response.Status.OK).build();
        }
    }

    @GET
    @Path("count")
    @Produces(MediaType.APPLICATION_JSON)
    public Response countPins() {
        Long count = pinService.countPins();
        return Response.status(Response.Status.OK).entity(String.valueOf(count)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response fetchPins(@QueryParam("limit") int limit, @QueryParam("offset") int offset) {
        BatchSize batchSize = new BatchSize(limit, offset);
        List<ErrorEntity> errorEntities = batchSizeValidationService.validate(batchSize);
        if (CollectionUtils.isEmpty(errorEntities)) {
            List<Pin> pins = pinService.fetchPins(batchSize.getLimit(), batchSize.getOffset());
            GenericEntity<List<Pin>> pinListObj = new GenericEntity<List<Pin>>(pins) {
            };
            return Response.status(Response.Status.OK).entity(pinListObj).build();
        } else {
            GenericEntity<List<ErrorEntity>> errorObj = new GenericEntity<List<ErrorEntity>>(errorEntities) {
            };
            return Response.status(Response.Status.BAD_REQUEST).entity(errorObj).build();
        }
    }
}
