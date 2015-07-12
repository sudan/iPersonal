package org.personalized.dashboard.service.api;

import org.personalized.dashboard.model.Tag;

import java.util.List;

/**
 * Created by sudan on 11/7/15.
 */
public interface TagService {

    /**
     * Update tags
     * @param tag
     */
    Long updateTags(Tag tag);

    /**
     * Get all tags for a user
     * @return
     */
    List<String> getTags();

}
