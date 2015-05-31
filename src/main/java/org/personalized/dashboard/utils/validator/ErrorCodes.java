package org.personalized.dashboard.utils.validator;

/**
 * Created by sudan on 27/5/15.
 */
public enum ErrorCodes {

    INVALID_URL("Invalid URL format"),
    INVALID_LIMIT("Invalid batch size"),
    INVALID_OFFSET("Invalid offset"),
    BOOKMARK_NAME_LENGTH_EXCEEDED("name length cannot exceed {0} characters"),
    EMPTY_BOOKMARK_NAME("name cannot be empty"),
    BOOKMARK_CONTENT_LENGTH_EXCEEDED("description length cannot exceed {0} characters"),
    EMPTY_BOOKMARK_CONTENT("description cannot be empty"),
    BOOKMARK_URL_LENGTH_EXCEEDED("url length cannot exceed {0} characters"),
    NOTE_TITLE_LENGTH_EXCEEDED("title length cannot exceed {0} characters"),
    EMPTY_NOTE_TITLE("title cannot be empty"),
    NOTE_CONTENT_LENGTH_EXCEEDED("content length cannot exceed {0} characters"),
    EMPTY_NOTE_CONTENT("content cannot be empty"),
    PIN_NAME_LENGTH_EXCEEDED("name length cannot exceed {0} characters"),
    EMPTY_PIN_NAME("name cannot be empty"),
    PIN_DESC_LENGTH_EXCEEDED("description length cannot exceed {0} characters"),
    EMPTY_PIN_DESC("description cannot be empty"),
    PIN_URL_LENGTH_EXCEEDED("url length cannot exceed {0} characters"),
    TOTO_NAME_LENGTH_EXCEEDED("name cannot be exceed {0} characters"),
    EMPTY_TODO_NAME("name cannot be empty"),
    TASKS_LENGTH_EXCEEDED("Task list exceeded the limit {0}"),
    EMPTY_TASK_LIST("Task list cannot be empty"),
    TASK_NAME_LENGTH_EXCEEDED("name cannot exceed {0} characters"),
    EMPTY_TASK_NAME("name cannot be empty"),
    TASK_CONTENT_LENGTH_EXCEEDED("content cannot exceed {0} characters"),
    EMPTY_TASK_CONTENT("content cannot be empty");

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
