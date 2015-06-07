package org.personalized.dashboard.model;

import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.List;

/**
 * Created by sudan on 6/5/15.
 */
@ActiveProfiles("test")
public class ExpenseTest {

    @Test
    public void testExpenseEntity() throws ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
        Bill bill1 = new Bill("BIL123456789", "bill1", "description1", 100.0, Currency
                .getInstance("USD"), simpleDateFormat.parse("2015-10-02"));
        Bill bill2 = new Bill("BIL123456987", "bill2", "description2", 150.0, Currency
                .getInstance("USD"), simpleDateFormat.parse("2015-11-02"));

        List<Bill> bills = Lists.newArrayList();
        bills.add(bill1);
        bills.add(bill2);

        Expense expense = new Expense("EXP123456789", "expense", bills);

        Assert.assertEquals("Expense ID is EXP123456789", "EXP123456789", expense.getExpenseId());
        Assert.assertEquals("Expense name is expense", "expense", expense.getName());
        Assert.assertEquals("Total number of bills is 2", 2, expense.getBills().size());
        Assert.assertNull("CreatedOn is null on creation.Hence only Data Layer can set it",
                expense.getCreatedOn());
        Assert.assertNull("modifiedAt is null.Hence only data layer can set it", expense
                .getModifiedAt());

        Assert.assertEquals("Bill1 id BIL123456789", "BIL123456789", expense.getBills().get(0)
                .getBillId());
        Assert.assertEquals("Bill2 id BIL123456987", "BIL123456987", expense.getBills().get(1)
                .getBillId());

        Assert.assertEquals("Bill1 name is bill1", "bill1", expense.getBills().get(0).getName());
        Assert.assertEquals("Bill2 name is bill2", "bill2", expense.getBills().get(1).getName());

        Assert.assertEquals("Bill1 description is description1", "description1", expense.getBills
                ().get(0).getDescription());
        Assert.assertEquals("Bill2 description is description2", "description2", expense.getBills
                ().get(1).getDescription());

        Assert.assertEquals("Bill1 amount in int is 100", (int) (100.0), (int) (expense.getBills
                ().get(0).getAmount()));
        Assert.assertEquals("Bill2 amount in int is 150", (int) (150.0), (int) (expense.getBills
                ().get(1).getAmount()));

        Assert.assertEquals("Bill1 currency is USD", "USD", expense.getBills().get(0).getCurrency
                ().getCurrencyCode());
        Assert.assertEquals("Bill2 currency is USD", "USD", expense.getBills().get(1).getCurrency
                ().getCurrencyCode());

        Assert.assertNull("CreatedOn is null on creation.Hence only Data Layer can set it",
                expense.getBills().get(0).getCreatedOn());
        Assert.assertNull("modifiedAt is null.Hence only data layer can set it", expense.getBills
                ().get(0).getModifiedAt());

        Assert.assertNull("CreatedOn is null on creation.Hence only Data Layer can set it",
                expense.getBills().get(1).getCreatedOn());
        Assert.assertNull("modifiedAt is null.Hence only data layer can set it", expense.getBills
                ().get(1).getModifiedAt());

        Assert.assertEquals("Bill1 date is 2015-10-02", "2015-10-02", simpleDateFormat.format
                (expense.getBills().get(0).getDate()));
        Assert.assertEquals("Bill1 date is 2015-11-02", "2015-11-02", simpleDateFormat.format
                (expense.getBills().get(1).getDate()));

        expense.setExpenseId("EXP123456988");
        expense.setName("newexpense");

        bills = Lists.newArrayList();
        bill1.setBillId("BIL123456788");
        bill1.setName("newbill");
        bill1.setAmount(1000.0);
        bill1.setCurrency(Currency.getInstance("INR"));
        bill1.setDescription("desc1");
        bill1.setDate(simpleDateFormat.parse("2015-10-22"));
        bills.add(bill1);
        expense.setBills(bills);

        Assert.assertEquals("Expense ID is EXP123456988", "EXP123456988", expense.getExpenseId());
        Assert.assertEquals("Expense name is newexpense", "newexpense", expense.getName());
        Assert.assertEquals("Total number of bills is 2", 1, expense.getBills().size());


        Assert.assertEquals("Bill1 id BIL123456788", "BIL123456788", expense.getBills().get(0)
                .getBillId());
        Assert.assertEquals("Bill1 name is newbill", "newbill", expense.getBills().get(0).getName
                ());
        Assert.assertEquals("Bill1 description is desc1", "desc1", expense.getBills().get(0)
                .getDescription());
        Assert.assertEquals("Bill1 amount in int is 1000", (int) (1000.0), (int) (expense
                .getBills().get(0).getAmount()));
        Assert.assertEquals("Bill1 currency is INR", "INR", expense.getBills().get(0).getCurrency
                ().getCurrencyCode());
        Assert.assertEquals("Bill1 date is 2015-10-22", "2015-10-22", simpleDateFormat.format
                (expense.getBills().get(0).getDate()));

    }

}
