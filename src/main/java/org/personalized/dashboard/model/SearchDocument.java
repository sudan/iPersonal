package org.personalized.dashboard.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by sudan on 5/7/15.
 */
@XmlRootElement
public class SearchDocument {

    private String documentId;
    private EntityType entityType;
    private String title;
    private String description;
    private Long createdAt;

    public SearchDocument() {

    }

    public SearchDocument(String documentId, EntityType entityType, String title, String description) {
        this.documentId = documentId;
        this.entityType = entityType;
        this.title = title;
        this.description = description;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("documentId", documentId)
                .append("entityType", entityType)
                .append("title", title)
                .append("description", description)
                .append("createdAt", createdAt)
                .toString();
    }
}
