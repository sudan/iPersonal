package org.personalized.dashboard.utils.validator;

/**
 * Created by sudan on 27/5/15.
 */
public enum ErrorCodes {

    INVALID_URL("Invalid URL format"),
    UNAUTHORIZED("Unauthorized access to resource"),
    INVALID_LIMIT("Invalid batch size"),
    INVALID_OFFSET("Invalid offset");

    private String description;

    ErrorCodes(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription() {
        this.description = description;
    }

}
