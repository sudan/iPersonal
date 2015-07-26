package org.personalized.dashboard.controller;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.personalized.dashboard.model.BatchSize;
import org.personalized.dashboard.model.Diary;
import org.personalized.dashboard.model.Page;
import org.personalized.dashboard.service.api.DiaryService;
import org.personalized.dashboard.utils.validator.ErrorEntity;
import org.personalized.dashboard.utils.validator.ValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;

import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.Map;

/**
 * Created by sudan on 3/4/15.
 */

@Controller
@Scope("request")
@Path("/diaries")
public class DiaryController {

    private final Logger LOGGER = LoggerFactory.getLogger(DiaryController.class);

    private final DiaryService diaryService;
    private final ValidationService diaryValidationService;
    private final ValidationService batchSizeValidationService;


    @Inject
    public DiaryController(DiaryService diaryService,
                           @Named("diary") ValidationService diaryValidationService,
                           @Named("batchSize") ValidationService batchSizeValidationService) {

        this.diaryService = diaryService;
        this.diaryValidationService = diaryValidationService;
        this.batchSizeValidationService = batchSizeValidationService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPage(@Context HttpHeaders httpHeaders, Diary diary) {

        try {
            List<ErrorEntity> errorEntities = diaryValidationService.validate(diary);
            if (CollectionUtils.isEmpty(errorEntities)) {
                String pageId = diaryService.createPage(diary.getPages().get(0), diary.getYear());
                return Response.status(Response.Status.CREATED).entity(pageId).build();
            } else {
                GenericEntity<List<ErrorEntity>> errorObj = new GenericEntity<List<ErrorEntity>>
                        (errorEntities) {
                };
                return Response.status(Response.Status.BAD_REQUEST).entity(errorObj).build();
            }
        } catch (Exception e) {
            LOGGER.error("DiaryController encountered an error", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("{year}/{pageId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPage(@Context HttpHeaders httpHeaders, @PathParam("year") int year,
                            @PathParam("pageId") String pageId) {

        try {
            if (StringUtils.isEmpty(pageId)) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            } else {
                Page page = diaryService.getPage(pageId, year);
                if (page == null) {
                    return Response.status(Response.Status.NOT_FOUND).build();
                } else {
                    return Response.status(Response.Status.OK).entity(page).build();
                }
            }
        } catch (Exception e) {
            LOGGER.error("PageController encountered an error", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{year}")
    public Response updatePage(@Context HttpHeaders httpHeaders, @PathParam("year") int year,
                               Diary diary) {

        try {
            List<ErrorEntity> errorEntities = diaryValidationService.validate(diary);
            if (CollectionUtils.isEmpty(errorEntities)) {
                Long modifiedCount = diaryService.updatePage(diary.getPages().get(0), year);
                if (modifiedCount > 0) {
                    return Response.status(Response.Status.OK).entity(diary).build();
                } else {
                    return Response.status(Response.Status.BAD_REQUEST).build();
                }
            } else {
                GenericEntity<List<ErrorEntity>> errorObj = new GenericEntity<List<ErrorEntity>>
                        (errorEntities) {
                };
                return Response.status(Response.Status.BAD_REQUEST).entity(errorObj).build();
            }
        } catch (Exception e) {
            LOGGER.error("DiaryController encountered an error", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DELETE
    @Path("{year}/{pageId}")
    public Response deletePage(@Context HttpHeaders httpHeaders, @PathParam("year") int year,
                               @PathParam("pageId") String pageId) {

        try {
            if (StringUtils.isEmpty(pageId)) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            } else {
                diaryService.deletePage(pageId, year);
                return Response.status(Response.Status.OK).build();
            }
        } catch (Exception e) {
            LOGGER.error("DiaryController encountered an error", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("count")
    @Produces(MediaType.APPLICATION_JSON)
    public Response countPages(@Context HttpHeaders httpHeaders) {

        try {
            Long count = diaryService.countPages();
            return Response.status(Response.Status.OK).entity(String.valueOf(count)).build();
        } catch (Exception e) {
            LOGGER.error("DiaryController encountered an error", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response fetchPages(@Context HttpHeaders httpHeaders,
                               @QueryParam("limit") int limit, @QueryParam("offset") int
            offset) {

        try {
            BatchSize batchSize = new BatchSize(limit, offset);
            List<ErrorEntity> errorEntities = batchSizeValidationService.validate(batchSize);
            if (CollectionUtils.isEmpty(errorEntities)) {
                List<Diary> diaries = Lists.newArrayList();
                Map<Integer, List<Page>> yearToPagesMap = diaryService.getPages(limit, offset);
                for (Map.Entry<Integer, List<Page>> entry : yearToPagesMap.entrySet()) {
                    Diary diary = new Diary();
                    diary.setYear(entry.getKey());
                    diary.setPages(entry.getValue());
                    diaries.add(diary);
                }
                GenericEntity<List<Diary>> diaryListObj = new GenericEntity<List<Diary>>
                        (diaries) {
                };
                return Response.status(Response.Status.OK).entity(diaryListObj).build();
            } else {
                GenericEntity<List<ErrorEntity>> errorObj = new GenericEntity<List<ErrorEntity>>
                        (errorEntities) {
                };
                return Response.status(Response.Status.BAD_REQUEST).entity(errorObj).build();
            }
        } catch (Exception e) {
            LOGGER.error("DiaryController encountered an error", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
