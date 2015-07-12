package org.personalized.dashboard.controller;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.personalized.dashboard.model.Tag;
import org.personalized.dashboard.service.api.TagService;
import org.personalized.dashboard.utils.validator.ErrorEntity;
import org.personalized.dashboard.utils.validator.ValidationService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by sudan on 11/7/15.
 */
@Component
@Scope("request")
@Path("/tags")
public class TagController {

    private final TagService tagService;
    private final ValidationService tagValidationService;

    @Inject
    public TagController(TagService tagService,
                         @Named("tag") ValidationService tagValidationService) {
        this.tagService = tagService;
        this.tagValidationService = tagValidationService;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateTags(Tag tag) {
        List<ErrorEntity> errorEntities = tagValidationService.validate(tag);
        if (CollectionUtils.isEmpty(errorEntities)) {
            Long modifiedCount = tagService.updateTags(tag);
            if (modifiedCount > 0) {
                return Response.status(Response.Status.OK).entity(tag).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        } else {
            GenericEntity<List<ErrorEntity>> errorObj = new GenericEntity<List<ErrorEntity>>
                    (errorEntities) {
            };
            return Response.status(Response.Status.BAD_REQUEST).entity(errorObj).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTags() {
        List<String> tags = tagService.getTags();
        Tag tag = new Tag();
        tag.setTags(tags);
        GenericEntity<Tag> tagsObj = new GenericEntity<Tag>(tag) {
        };
        return Response.status(Response.Status.OK).entity(tagsObj).build();
    }
}