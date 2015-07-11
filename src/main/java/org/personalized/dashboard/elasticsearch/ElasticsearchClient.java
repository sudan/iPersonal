package org.personalized.dashboard.elasticsearch;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.personalized.dashboard.bootstrap.ESBootstrap;
import org.personalized.dashboard.model.EntityType;
import org.personalized.dashboard.model.SearchContext;
import org.personalized.dashboard.model.SearchDocument;
import org.personalized.dashboard.utils.ConfigKeys;
import org.personalized.dashboard.utils.Constants;
import org.personalized.dashboard.utils.FieldKeys;
import org.personalized.dashboard.utils.auth.SessionManager;

import java.util.List;
import java.util.Map;

/**
 * Created by sudan on 5/7/15.
 */
public class ElasticsearchClient {

    private final SessionManager sessionManager;

    @Inject
    public ElasticsearchClient(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public IndexResponse insertOrUpdate(SearchDocument searchDocument) {
        searchDocument.setCreatedAt(System.currentTimeMillis());
        IndexRequest indexRequest = new IndexRequest(
                ConfigKeys.ES_INDEX, ConfigKeys.ES_TYPE, searchDocument.getDocumentId()
        );
        Map<String, String> payload = Maps.newHashMap();
        payload.put(FieldKeys.ES_ID, searchDocument.getDocumentId());
        payload.put(FieldKeys.ES_TITLE, searchDocument.getTitle());
        if (StringUtils.isNotEmpty(searchDocument.getDescription()))
            payload.put(FieldKeys.ES_DESCRIPTION, searchDocument.getDescription().substring(0, Math.min(searchDocument.getDescription().length(), 100)));
        payload.put(FieldKeys.ES_TIMESTAMP, String.valueOf(searchDocument.getCreatedAt()));
        payload.put(FieldKeys.ES_ENTITY_TYPE, searchDocument.getEntityType().name());
        payload.put(FieldKeys.USER_ID, sessionManager.getUserIdFromSession());

        indexRequest.source(new Gson().toJson(payload));
        return ESBootstrap.getClient().index(indexRequest).actionGet();
    }

    public DeleteResponse delete(String documentId) {

        return ESBootstrap.getClient().prepareDelete(ConfigKeys.ES_INDEX,
                ConfigKeys.ES_TYPE, documentId).execute().actionGet();
    }

    public List<SearchDocument> search(SearchContext searchContext) {

        List<SearchDocument> searchDocuments = Lists.newArrayList();

        SearchResponse searchResponse = ESBootstrap.getClient()
                .prepareSearch(ConfigKeys.ES_INDEX)
                .setTypes(ConfigKeys.ES_TYPE)
                .setQuery(getQueryBuilder(searchContext))
                .setFrom(Constants.ES_OFFSET)
                .setSize(Constants.ES_LIMIT)
                .addSort(SortBuilders.fieldSort(FieldKeys.ES_TIMESTAMP).order(SortOrder.DESC))
                .execute().actionGet();

        if (searchResponse.getHits().getTotalHits() > 0) {
            SearchHit[] searchHits = searchResponse.getHits().getHits();
            for (SearchHit searchHit : searchHits) {
                SearchDocument searchDocument = new SearchDocument();
                searchDocument.setDocumentId(searchHit.getId());
                searchDocument.setTitle(searchHit.getSource().get(FieldKeys.ES_TITLE).toString());
                searchDocument.setEntityType(EntityType.valueOf(
                        searchHit.getSource().get(FieldKeys.ES_ENTITY_TYPE).toString()
                ));
                if (searchHit.getSource().containsKey(FieldKeys.ES_DESCRIPTION)) {
                    searchDocument.setDescription(searchHit.getSource().get(FieldKeys.ES_DESCRIPTION).toString());
                }
                searchDocument.setCreatedAt(Long.valueOf(searchHit.getSource().get(FieldKeys.ES_TIMESTAMP).toString()));
                searchDocuments.add(searchDocument);
            }
        }

        return searchDocuments;
    }

    private BoolQueryBuilder getQueryBuilder(SearchContext searchContext) {

        BoolQueryBuilder searchQueryBuilder = new BoolQueryBuilder();

        BoolQueryBuilder userIdQueryBuilder = new BoolQueryBuilder();
        userIdQueryBuilder.must(QueryBuilders.termQuery(FieldKeys.USER_ID, sessionManager.getUserIdFromSession()));
        searchQueryBuilder.must(userIdQueryBuilder);

        if (searchContext.getEntityTypes() != null && searchContext.getEntityTypes().size() > 0) {
            BoolQueryBuilder entityTypeQueryBuilder = new BoolQueryBuilder();
            for (EntityType entityType : searchContext.getEntityTypes()) {
                entityTypeQueryBuilder.should(QueryBuilders.matchQuery(FieldKeys.ES_ENTITY_TYPE, entityType.name()));
            }
            searchQueryBuilder.must(entityTypeQueryBuilder);
        }

        if (searchContext.getTitles() != null && searchContext.getTitles().size() > 0) {
            BoolQueryBuilder titleQueryBuilder = new BoolQueryBuilder();
            for (String title : searchContext.getTitles()) {
                titleQueryBuilder.should(QueryBuilders.fuzzyQuery(FieldKeys.ES_TITLE, title));
            }
            searchQueryBuilder.must(titleQueryBuilder);
        }

        if (searchContext.getKeywords() != null && searchContext.getKeywords().size() > 0) {
            BoolQueryBuilder keywordQueryBuilder = new BoolQueryBuilder();
            for (String keyword : searchContext.getKeywords()) {
                keywordQueryBuilder.should(QueryBuilders.fuzzyQuery(FieldKeys.ES_DESCRIPTION, keyword));
            }
            searchQueryBuilder.must(keywordQueryBuilder);
        }

        if (searchContext.getTags() != null && searchContext.getTags().size() > 0) {
            BoolQueryBuilder tagQueryBuilder = new BoolQueryBuilder();
            for (String tag : searchContext.getTags()) {
                tagQueryBuilder.should(QueryBuilders.termQuery(FieldKeys.ES_TAG, tag));
            }
            searchQueryBuilder.must(tagQueryBuilder);
        }

        return searchQueryBuilder;
    }
}
