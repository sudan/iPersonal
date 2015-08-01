package org.personalized.dashboard.dao.api;

import java.util.List;

/**
 * Created by sudan on 2/8/15.
 */
public interface ExpenseCategoryDao {

    /**
     * Get all categories related to expense for that user
     * @param userId
     * @return
     */
    List<String> get(String userId);
}
