package org.personalized.dashboard.model;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Created by sudan on 3/4/15.
 */
public class Diary {

    private String diaryId;
    private List<Page> pages = Lists.newArrayList();
    private int year;
    private Long createdOn;
    private Long modifiedAt;

    public Diary() {

    }

    public Diary(String diaryId, List<Page> pages, int year) {
        this.diaryId = diaryId;
        this.pages = pages;
        this.year = year;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Long getCreatedOn() {
        return createdOn;
    }

    public Long getModifiedAt() {
        return modifiedAt;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("diaryId", diaryId)
                .append("pages", pages)
                .append("year", year)
                .append("createdOn", createdOn)
                .append("modifiedAt", modifiedAt)
                .toString();
    }
}
