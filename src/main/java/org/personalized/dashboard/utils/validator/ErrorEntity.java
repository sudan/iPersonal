package org.personalized.dashboard.utils.validator;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by sudan on 27/5/15.
 */
@XmlRootElement
public class ErrorEntity {

    private String name;
    private String description;

    public ErrorEntity() {

    }

    public ErrorEntity(String name, String description) {
        this.name = name;
        this.description = description;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("description", description)
                .toString();
    }
}
