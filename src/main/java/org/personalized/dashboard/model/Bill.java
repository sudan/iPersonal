package org.personalized.dashboard.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Currency;
import java.util.Date;

/**
 * Created by sudan on 5/5/15.
 */
public class Bill {

    private String billId;
    private String name;
    private String description;
    private double amount;
    private Currency currency;
    private Long createdOn;
    private Long modifiedAt;
    private Date date;

    public Bill() {

    }

    public Bill(String billId, String name, String description, double amount, Currency currency,
                Date date) {
        this.billId = billId;
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.currency = currency;
        this.date = date;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Long getCreatedOn() {
        return createdOn;
    }

    public Long getModifiedAt() {
        return modifiedAt;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("billId", billId)
                .append("name", name)
                .append("description", description)
                .append("currency", currency)
                .append("createdOn", createdOn)
                .append("modifiedAt", modifiedAt)
                .append("date", date)
                .toString();
    }

}
