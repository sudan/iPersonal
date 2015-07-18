package org.personalized.dashboard.service.impl;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.personalized.dashboard.dao.api.ActivityDao;
import org.personalized.dashboard.dao.api.DiaryDao;
import org.personalized.dashboard.model.Activity;
import org.personalized.dashboard.model.ActivityType;
import org.personalized.dashboard.model.EntityType;
import org.personalized.dashboard.model.Page;
import org.personalized.dashboard.service.api.DiaryService;
import org.personalized.dashboard.utils.auth.SessionManager;
import org.personalized.dashboard.utils.generator.ActivityGenerator;
import org.personalized.dashboard.utils.htmltidy.DOMParser;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by sudan on 3/4/15.
 */
@Repository
public class DiaryServiceImpl implements DiaryService {

    private final DiaryDao diaryDao;
    private final SessionManager sessionManager;
    private final ActivityGenerator activityGenerator;
    private final ActivityDao activityDao;
    private final DOMParser domParser;

    @Inject
    public DiaryServiceImpl(DiaryDao diaryDao, SessionManager sessionManager,
                            ActivityGenerator activityGenerator,
                            ActivityDao activityDao, DOMParser domParser) {
        this.diaryDao = diaryDao;
        this.sessionManager = sessionManager;
        this.activityGenerator = activityGenerator;
        this.activityDao = activityDao;
        this.domParser = domParser;
    }

    @Override
    public String createPage(Page page, int year){
        page.setContent(domParser.removeMalformedTags(page.getContent()));
        String pageId = diaryDao.create(page, year, sessionManager.getUserIdFromSession());
        Activity activity = activityGenerator.generate(ActivityType.CREATED, EntityType.DIARY,
                pageId, page.getTitle());
        activityDao.add(activity, sessionManager.getUserIdFromSession());
        return pageId;
    }

    @Override
    public Page getPage(String pageId, int year) {
        return diaryDao.get(pageId, year, sessionManager.getUserIdFromSession());
    }

    @Override
    public Long updatePage(Page page, int year){
        page.setContent(domParser.removeMalformedTags(page.getContent()));
        Long modifiedCount = diaryDao.update(page, year, sessionManager.getUserIdFromSession());
        if (modifiedCount > 0) {
            Activity activity = activityGenerator.generate(ActivityType.UPDATED, EntityType.DIARY,
                                page.getPageId(), page.getTitle());
            activityDao.add(activity, sessionManager.getUserIdFromSession());
        }
        return modifiedCount;
    }

    @Override
    public Long deletePage(String pageId, int year) {
        Long deletedCount = diaryDao.delete(pageId, year, sessionManager.getUserIdFromSession());
        if (deletedCount > 0) {
            Activity activity = activityGenerator.generate(ActivityType.DELETED, EntityType.DIARY,
                                pageId, StringUtils.EMPTY);
            activityDao.add(activity, sessionManager.getUserIdFromSession());
        }
        return deletedCount;
    }

    @Override
    public Long countPages(int year) {
        return diaryDao.count(year, sessionManager.getUserIdFromSession());
    }

    @Override
    public Map<Integer, List<Page>> getPages(int limit, int offset) {
        return diaryDao.getAll(limit, offset, sessionManager.getUserIdFromSession());
    }
}
