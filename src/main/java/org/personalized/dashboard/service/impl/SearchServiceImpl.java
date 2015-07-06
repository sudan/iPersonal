package org.personalized.dashboard.service.impl;

import com.google.inject.Inject;
import org.personalized.dashboard.elasticsearch.ElasticsearchClient;
import org.personalized.dashboard.model.ESDocument;
import org.personalized.dashboard.model.SearchContext;
import org.personalized.dashboard.service.api.SearchService;

import java.util.List;

/**
 * Created by sudan on 6/7/15.
 */
public class SearchServiceImpl implements SearchService {

    private ElasticsearchClient elasticsearchClient;

    @Inject
    public SearchServiceImpl(ElasticsearchClient elasticsearchClient) {
        this.elasticsearchClient = elasticsearchClient;
    }

    @Override
    public List<ESDocument> searchEntities(SearchContext searchContext) {
        return elasticsearchClient.search(searchContext);
    }
}
