package org.personalized.dashboard.utils;

/**
 * Created by sudan on 25/5/15.
 */
public class Constants {

    public static final String USER_ID = "user_id";
    public static final String CREATED_ON = "created_on";
    public static final String MODIFIED_AT = "modified_at";
    public static final String IS_DELETED = "is_deleted";
    public static final String PRIMARY_KEY = "_id";

    public static final String BOOKMARKS = "bookmarks";
    public static final String BOOKMARK_PREFIX = "BOK";
    public static final String BOOKMARK_NAME = "name";
    public static final String BOOKMARK_DESCRIPTION = "description";
    public static final String BOOKMARK_URL = "url";
    public static final int BOOKMARK_NAME_MAX_LENGTH = 50;
    public static final int BOOKMARK_CONTENT_MAX_LENGTH = 1000;
    public static final int BOOKMARK_URL_MAX_LENGTH = 300;

    public static final String NOTES = "notes";
    public static final String NOTE_PREFIX = "NOT";
    public static final String NOTE_TITLE = "title";
    public static final String NOTE_CONTENT = "note";
    public static final int NOTE_TITLE_MAX_LENGTH = 50;
    public static final int NOTE_CONTENT_MAX_LENGTH = 1000;

    public static final String PINS = "pins";
    public static final String PIN_PREFIX = "PIN";
    public static final String PIN_NAME = "name";
    public static final String PIN_DESCRIPTION = "description";
    public static final String PIN_IMAGE_URL = "image_url";
    public static final int PIN_NAME_MAX_LENGTH = 50;
    public static final int PIN_DESCRIPTION_MAX_LENGTH = 2000;
    public static final int PIN_URL_MAX_LENGTH = 300;

    public static final int ID_LENGTH = 16;
    public static final int MAX_BATCH_SIZE = 20;

    public static final String ACTIVITIES = "activities";
    public static final String ACTIVITIES_PREFIX = "ACT";
    public static final String ACTIVITY_TYPE = "type";
    public static final String ACTIVITY_DESC = "description";
    public static final String ENTITY_TYPE = "entity_type";
    public static final String ENTITY_ID = "entity_id";

    public static final String SET_OPERATION = "$set";
}
