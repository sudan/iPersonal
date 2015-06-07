package org.personalized.dashboard.model;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Map;

/**
 * Created by sudan on 27/4/15.
 */
public class Page {

    private String pageId;
    private String title;
    private String template;
    private Map<String, String> placeholders = Maps.newHashMap();
    private int month;
    private int date;
    private Long createdOn;
    private Long modifiedAt;

    public Page() {

    }

    public Page(String pageId, String title, String template, Map<String, String> placeholders, int month, int date) {
        this.pageId = pageId;
        this.title = title;
        this.template = template;
        this.placeholders = placeholders;
        this.month = month;
        this.date = date;
    }

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public Map<String, String> getPlaceholders() {
        return placeholders;
    }

    public void setPlaceholders(Map<String, String> placeholders) {
        this.placeholders = placeholders;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
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
                .append("pageId", pageId)
                .append("title", title)
                .append("template", template)
                .append("placeholders", placeholders)
                .append("date", date)
                .append("createdOn", createdOn)
                .append("modifiedAt", modifiedAt)
                .toString();
    }
}
