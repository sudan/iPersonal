package org.personalized.dashboard.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by sudan on 2/8/15.
 */
@XmlRootElement
public class ExpenseCategory {

    private List<String> categories;

    public ExpenseCategory() {

    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("categories",categories)
                .toString();
    }
}
