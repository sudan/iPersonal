package org.personalized.dashboard.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by sudan on 3/4/15.
 */
@XmlRootElement
public class Bookmark {

    private String bookmarkId;
    private String name;
    private String description;
    private String url;
    private Long createdOn;
    private Long modifiedAt;
    private String userId;

    public Bookmark(){

    }

    public Bookmark(String bookmarkId, String name, String description, String url, String userId){
        this.bookmarkId = bookmarkId;
        this.name = name;
        this.description = description;
        this.url = url;
        this.userId = userId;
    }

    public String getBookmarkId(){
        return bookmarkId;
    }

    public void setBookmarkId(String bookmarkId){
        this.bookmarkId = bookmarkId;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getUrl(){
        return url;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public Long getCreatedOn(){
        return createdOn;
    }

    public Long getModifiedAt(){
        return modifiedAt;
    }

    public String getUserId(){
        return userId;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    @Override
    public String toString(){
        return new ToStringBuilder(this)
                .append("bookmarkId", bookmarkId)
                .append("name", name)
                .append("description", description)
                .append("url", url)
                .append("createdOn", createdOn)
                .append("modifiedAt", modifiedAt)
                .append("userId", userId)
                .toString();
    }
}
