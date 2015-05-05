package org.personalized.dashboard.model;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Created by sudan on 4/5/15.
 */
public class Expense {

    private String expenseId;
    private String name;
    private String userId;
    private List<Bill> bills = Lists.newArrayList();
    private Long createdOn;
    private Long modifiedAt;

    public Expense() {

    }

    public Expense(String expenseId, String name, String userId, List<Bill> bills){
        this.expenseId = expenseId;
        this.name = name;
        this.userId = userId;
        this.bills = bills;
    }

    public String getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(String expenseId) {
        this.expenseId = expenseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Bill> getBills() {
        return bills;
    }

    public void setBills(List<Bill> bills) {
        this.bills = bills;
    }

    public Long getCreatedOn() {
        return createdOn;
    }

    public Long getModifiedAt() {
        return modifiedAt;
    }

    @Override
    public String toString(){
        return new ToStringBuilder(this)
                .append("expenseId", expenseId)
                .append("name", name)
                .append("userId", userId)
                .append("bills", bills)
                .append("createdOn", createdOn)
                .append("modifiedAt", modifiedAt)
                .toString();
    }
}
