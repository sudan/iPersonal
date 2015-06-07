package org.personalized.dashboard.controller;

import com.google.inject.Inject;
import org.personalized.dashboard.service.api.ExpenseService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.ws.rs.Path;

/**
 * Created by sudan on 17/5/15.
 */
@Controller
@Scope("request")
@Path("/expense")
public class ExpenseController {

    private final ExpenseService expenseService;

    @Inject
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }
}
