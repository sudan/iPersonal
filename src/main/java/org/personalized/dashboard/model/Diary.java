package org.personalized.dashboard.model;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;
import org.personalized.dashboard.utils.FieldKeys;
import org.personalized.dashboard.utils.validator.FieldName;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by sudan on 3/4/15.
 */
@XmlRootElement
public class Diary {

    @FieldName(name = FieldKeys.DIARY_YEAR)
    private int year;

    @Valid
    @NotEmpty
    @FieldName(name = FieldKeys.DIARY_PAGES)
    private List<Page> pages = Lists.newArrayList();

    public Diary() {

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

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("pages", pages)
                .append("year", year)
                .toString();
    }
}
