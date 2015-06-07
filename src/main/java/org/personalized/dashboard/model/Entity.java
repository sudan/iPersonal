package org.personalized.dashboard.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by sudan on 6/4/15.
 */
@XmlRootElement
public class Entity {

    private EntityType entityType;
    private String entityId;

    public Entity() {

    }

    public Entity(EntityType entityType, String entityId) {
        this.entityType = entityType;
        this.entityId = entityId;

    }

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
    public String toString() {
        return new ToStringBuilder(this)
                .append("entityType", entityType)
                .append("entityId", entityId)
                .toString();
    }

}
