package org.personalized.dashboard.utils.validator;

/**
 * Created by sudan on 27/5/15.
 */
public enum ErrorCodes {

    INVALID_URL("Invalid URL format"),
    INVALID_LIMIT("Invalid batch size"),
    INVALID_OFFSET("Invalid offset"),
    MAX_TITLE_LENGTH_EXCEEDED("Title length cannot exceed {0} characters"),
    MAX_CONTENT_LENGTH_EXCEEDED("Content length cannot exceed {0} characters");

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
