package org.personalized.dashboard.dao.api;

import org.personalized.dashboard.model.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by sudan on 3/4/15.
 */
public interface DiaryDao {

    /**
     * Create a new page for the year and user
     *
     * @param page
     * @param year
     * @param userId
     * @return
     */
    String create(Page page, int year, String userId);

    /**
     * Get the page for the year and user
     *
     * @param pageId
     * @param year
     * @param userId
     * @return
     */
    Page get(String pageId, int year, String userId);

    /**
     * Update the page for the year and user
     *
     * @param page
     * @param year
     * @param userId
     * @return
     */
    Long update(Page page, int year, String userId);

    /**
     * Delete the page for the year and userId
     *
     * @param pageId
     * @param year
     * @param userId
     * @return
     */
    Long delete(String pageId, int year, String userId);

    /**
     * Count the number of pages for the year
     *
     * @param userId
     * @return
     */
    Long count(String userId);

    /**
     * Get all pages mapped to year for the user
     *
     * @param limit
     * @param offset
     * @param userId
     * @return
     */
    Map<Integer, List<Page>> getAll(int limit, int offset, String userId);
}
