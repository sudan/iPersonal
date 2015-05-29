package org.personalized.dashboard.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by sudan on 3/4/15.
 */
public class Pin {

    private String pinId;
    private String name;
    private String description;
    private String imageUrl;
    private Long createdOn;
    private Long modifiedAt;

    public Pin(){

    }

    public Pin(String pinId, String name, String description, String imageUrl){
        this.pinId = pinId;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public String getPinId() {
        return pinId;
    }

    public void setPinId(String pinId) {
        this.pinId = pinId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getCreatedOn() {
        return createdOn;
    }

    public Long getModifiedAt() {
        return modifiedAt;
    }

    @Override
    public String toString(){
        return new ToStringBuilder(this)
                .append("pinId", pinId)
                .append("name", name)
                .append("description", description)
                .append("imageUrl", imageUrl)
                .append("createdOn", createdOn)
                .append("modifiedAt", modifiedAt)
                .toString();
    }
}
