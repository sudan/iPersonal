package org.personalized.dashboard.utils.validator;

/**
 * Created by sudan on 27/5/15.
 */
public enum ErrorCodes {

    INVALID_URL("Invalid URL format"),
    INVALID_LIMIT("Invalid batch size"),
    INVALID_OFFSET("Invalid offset"),
    MAX_BOOKMARK_NAME_LENGTH_EXCEEDED("name length cannot exceed {0} characters"),
    MAX_BOOKMARK_CONTENT_LENGTH_EXCEEDED("description length cannot exceed {0} characters"),
    MAX_BOOKMARK_URL_LENGTH_EXCEEDED("url length cannot exceed {0} characters"),
    MAX_NOTE_TITLE_LENGTH_EXCEEDED("Title length cannot exceed {0} characters"),
    MAX_NOTE_CONTENT_LENGTH_EXCEEDED("Content length cannot exceed {0} characters"),
    MAX_PIN_NAME_LENGTH_EXCEEDED("Name length cannot exceed {0} characters"),
    MAX_PIN_DESC_LENGTH_EXCEEDED("Description length cannot exceed {0} characters"),
    MAX_PIN_URL_LENGTH_EXCEEDED("Url length cannot exceed {0} characters");

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
