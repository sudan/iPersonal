package org.personalized.dashboard.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;
import org.personalized.dashboard.utils.Constants;

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by sudan on 3/4/15.
 */
@XmlRootElement
public class Pin {

    private String pinId;

    @NotEmpty
    @Size(max=Constants.TITLE_MAX_LENGTH)
    private String name;

    @NotEmpty
    @Size(max=Constants.CONTENT_MAX_LENGTH)
    private String description;

    @NotEmpty
    @Size(max=Constants.URL_MAX_LENGTH)
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

    public void setCreatedOn(Long createdOn) {
        this.createdOn = createdOn;
    }

    public Long getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Long modifiedAt) {
        this.modifiedAt = modifiedAt;
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
