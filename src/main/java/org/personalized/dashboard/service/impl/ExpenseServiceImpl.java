package org.personalized.dashboard.service.impl;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.personalized.dashboard.dao.api.ActivityDao;
import org.personalized.dashboard.dao.api.ExpenseDao;
import org.personalized.dashboard.model.*;
import org.personalized.dashboard.service.api.ExpenseService;
import org.personalized.dashboard.utils.auth.SessionManager;
import org.personalized.dashboard.utils.generator.ActivityGenerator;

import java.util.List;

/**
 * Created by sudan on 17/5/15.
 */
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseDao expenseDao;
    private final SessionManager sessionManager;
    private final ActivityGenerator activityGenerator;
    private final ActivityDao activityDao;

    @Inject
    public ExpenseServiceImpl(ExpenseDao expenseDao, SessionManager sessionManager,
                              ActivityGenerator activityGenerator, ActivityDao activityDao) {
        this.expenseDao = expenseDao;
        this.sessionManager = sessionManager;
        this.activityGenerator = activityGenerator;
        this.activityDao = activityDao;
    }

    @Override
    public String createExpense(Expense expense) {
        String expenseId = expenseDao.create(expense, sessionManager.getUserIdFromSession());
        Activity activity = activityGenerator.generate(ActivityType.CREATED, EntityType.EXPENSE,
                expenseId, expense.getTitle());
        activityDao.add(activity, sessionManager.getUserIdFromSession());
        return expenseId;
    }

    @Override
    public Expense getExpense(String expenseId) {
        return expenseDao.get(expenseId, sessionManager.getUserIdFromSession());
    }

    @Override
    public Long updateExpense(Expense expense) {
        Long modifiedCount = expenseDao.update(expense, sessionManager.getUserIdFromSession());
        if(modifiedCount > 0) {
            Activity activity = activityGenerator.generate(ActivityType.UPDATED, EntityType.EXPENSE,
                    expense.getExpenseId(), expense.getTitle());
            activityDao.add(activity, sessionManager.getUserIdFromSession());
        }
        return modifiedCount;
    }

    @Override
    public void deleteExpense(String expenseId) {
        Long deletedCount = expenseDao.delete(expenseId, sessionManager.getUserIdFromSession());
        if(deletedCount > 0) {
            Activity activity = activityGenerator.generate(ActivityType.DELETED, EntityType.EXPENSE,
                    expenseId, StringUtils.EMPTY);
            activityDao.add(activity, sessionManager.getUserIdFromSession());
        }
    }

    @Override
    public Long countExpense(ExpenseFilter expenseFilter) {
        return expenseDao.count(expenseFilter, sessionManager.getUserIdFromSession());
    }

    @Override
    public List<Expense> fetchExpenses(ExpenseFilter expenseFilter, int limit, int offset) {
        return expenseDao.get(expenseFilter, limit, offset, sessionManager.getUserIdFromSession());
    }
}
