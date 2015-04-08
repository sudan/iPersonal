package org.personalized.dashboard.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by sudan on 6/4/15.
 */
public class Entity {

    private EntityType entityType;
    private String entityId;

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    @Override
    public String toString(){
        return new ToStringBuilder(this)
                .append("entityType", entityType)
                .append("entityId", entityId)
                .toString();
    }

}
