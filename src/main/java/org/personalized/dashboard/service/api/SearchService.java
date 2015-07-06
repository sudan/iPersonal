package org.personalized.dashboard.service.api;

import org.personalized.dashboard.model.ESDocument;
import org.personalized.dashboard.model.SearchContext;

import java.util.List;

/**
 * Created by sudan on 6/7/15.
 */
public interface SearchService {

    /**
     * Returns list of documents
     *
     * @param searchContext
     * @return
     */
    List<ESDocument> searchEntities(SearchContext searchContext);
}
