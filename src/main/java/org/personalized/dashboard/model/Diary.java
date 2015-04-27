package org.personalized.dashboard.model;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Iterator;
import java.util.List;

/**
 * Created by sudan on 3/4/15.
 */
public class Diary {

    private String diaryId;
    private List<Page> pages = Lists.newArrayList();
    private String userId;
    private Long createdOn;
    private Long modifiedAt;

    public Diary(){

    }

    public Diary(String diaryId, List<Page> pages, String userId){
        this.diaryId = diaryId;
        this.pages = pages;
        this.userId = userId;
    }

    public String getDiaryId() {
        return diaryId;
    }

    public void setDiaryId(String diaryId) {
        this.diaryId = diaryId;
    }

    public List<Page> getPages() {
        return pages;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getCreatedOn() {
        return createdOn;
    }

    public Long getModifiedAt() {
        return modifiedAt;
    }

    public void addPage(Page page){
        this.pages.add(page);
    }

    public void deletePage(Page page){
        Iterator iterator = pages.iterator();
        while (iterator.hasNext()){
            Page p  = (Page)iterator.next();
            if(p.getPageId().equals(page.getPageId()))
                iterator.remove();
        }
    }
    @Override
    public String toString(){
        return new ToStringBuilder(this)
                .append("diaryId", diaryId)
                .append("pages", pages)
                .append("userId", userId)
                .append("createdOn", createdOn)
                .append("modifiedAt", modifiedAt)
                .toString();
    }
}
