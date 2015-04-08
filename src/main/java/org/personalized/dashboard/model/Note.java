package org.personalized.dashboard.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by sudan on 3/4/15.
 */
public class Note {

    private String noteId;
    private String title;
    private String note;
    private Long createdOn;
    private Long modifiedAt;
    private String userId;

    public Note(){

    }

    public Note(String noteId, String title, String note, String userId){
        this.noteId = noteId;
        this.title = title;
        this.note = note;
        this.userId = userId;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getCreatedOn() {
        return createdOn;
    }

    public Long getModifiedAt() {
        return modifiedAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString(){
        return new ToStringBuilder(this)
                .append("noteId", noteId)
                .append("title", title)
                .append("note", note)
                .append("userId", userId)
                .append("createdOn", createdOn)
                .append("modifiedAt", modifiedAt)
                .toString();
    }
}
