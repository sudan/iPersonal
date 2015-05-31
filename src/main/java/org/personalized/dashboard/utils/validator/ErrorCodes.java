package org.personalized.dashboard.utils.validator;

/**
 * Created by sudan on 27/5/15.
 */
public enum ErrorCodes {

    INVALID_URL("Invalid URL format"),
    INVALID_LIMIT("Invalid batch size"),
    INVALID_OFFSET("Invalid offset"),
    BOOKMARK_NAME_LENGTH_EXCEEDED("name length cannot exceed {0} characters"),
    BOOKMARK_CONTENT_LENGTH_EXCEEDED("description length cannot exceed {0} characters"),
    BOOKMARK_URL_LENGTH_EXCEEDED("url length cannot exceed {0} characters"),
    NOTE_TITLE_LENGTH_EXCEEDED("title length cannot exceed {0} characters"),
    NOTE_CONTENT_LENGTH_EXCEEDED("content length cannot exceed {0} characters"),
    PIN_NAME_LENGTH_EXCEEDED("name length cannot exceed {0} characters"),
    PIN_DESC_LENGTH_EXCEEDED("description length cannot exceed {0} characters"),
    PIN_URL_LENGTH_EXCEEDED("url length cannot exceed {0} characters"),
    TASKS_LENGTH_EXCEEDED("Task list exceeded the limit {0}"),
    EMPTY_TASK_LIST("Task list cannot be empty");

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
