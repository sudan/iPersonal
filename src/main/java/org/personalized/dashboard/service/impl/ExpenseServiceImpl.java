package org.personalized.dashboard.service.impl;

import com.google.inject.Inject;
import org.personalized.dashboard.dao.api.ExpenseDao;
import org.personalized.dashboard.service.api.ExpenseService;

/**
 * Created by sudan on 17/5/15.
 */
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseDao expenseDao;

    @Inject
    public ExpenseServiceImpl(ExpenseDao expenseDao){
        this.expenseDao = expenseDao;
    }
}
