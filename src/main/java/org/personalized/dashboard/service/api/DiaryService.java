package org.personalized.dashboard.service.api;

import org.personalized.dashboard.model.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by sudan on 3/4/15.
 */
public interface DiaryService {

    /**
     * Create a new page for the user
     * @param page
     * @param year
     * @return
     */
    String createPage(Page page, int year);

    /**
     * Get page for the given pageId
     * @param pageId
     * @param year
     * @return
     */
    Page getPage(String pageId, int year);

    /**
     * Update page for the user
     * @param page
     * @param year
     * @return
     */
    Long updatePage(Page page, int year);

    /**
     * Delete the page given the pageId
     * @param pageId
     * @param year
     * @return
     */
    Long deletePage(String pageId, int year);

    /**
     * Count the pages for the given year
     * @param year
     * @return
     */
    Long countPages(int year);

    /**
     * Get all pages based on limit and offset
     * @param limit
     * @param offset
     * @return
     */
    Map<Integer, List<Page>> getPages(int limit, int offset);
}
