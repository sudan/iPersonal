package org.personalized.dashboard.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Created by sudan on 3/4/15.
 */
public class Tag {

    private String userId;
    private String tagName;
    private List<Entity> entities;

    public Tag() {

    }

    public Tag(String userId, String tagName, List<Entity> entities) {
        this.userId = userId;
        this.tagName = tagName;
        this.entities = entities;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("userId", userId)
                .append("tagName", tagName)
                .append("entities", entities)
                .toString();
    }
}
