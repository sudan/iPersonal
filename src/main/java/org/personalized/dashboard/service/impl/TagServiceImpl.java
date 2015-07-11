package org.personalized.dashboard.service.impl;

import com.google.inject.Inject;
import org.personalized.dashboard.dao.api.ActivityDao;
import org.personalized.dashboard.dao.api.TagDao;
import org.personalized.dashboard.model.Activity;
import org.personalized.dashboard.model.ActivityType;
import org.personalized.dashboard.model.Entity;
import org.personalized.dashboard.model.Tag;
import org.personalized.dashboard.service.api.TagService;
import org.personalized.dashboard.utils.auth.SessionManager;
import org.personalized.dashboard.utils.generator.ActivityGenerator;

/**
 * Created by sudan on 11/7/15.
 */
public class TagServiceImpl implements TagService {

    private final TagDao tagDao;
    private final SessionManager sessionManager;
    private final ActivityGenerator activityGenerator;
    private final ActivityDao activityDao;

    @Inject
    public TagServiceImpl(TagDao tagDao, SessionManager sessionManager,
                          ActivityGenerator activityGenerator,
                          ActivityDao activityDao) {
        this.tagDao = tagDao;
        this.sessionManager = sessionManager;
        this.activityGenerator = activityGenerator;
        this.activityDao = activityDao;

    }
    @Override
    public Long updateTags(Tag tag) {
        Entity entity = tag.getEntity();
        Long modifiedCount = tagDao.update(tag.getTags(), entity, sessionManager.getUserIdFromSession());
        if(modifiedCount > 0) {
            Activity activity = activityGenerator.generate(ActivityType.UPDATED, entity.getEntityType(),
                    entity.getEntityId(), entity.getTitle());
            activityDao.add(activity, sessionManager.getUserIdFromSession());
        }
        return modifiedCount;
    }
}
