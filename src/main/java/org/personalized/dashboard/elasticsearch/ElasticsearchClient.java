package org.personalized.dashboard.elasticsearch;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.personalized.dashboard.bootstrap.ConfigManager;
import org.personalized.dashboard.bootstrap.ESBootstrap;
import org.personalized.dashboard.model.EntityType;
import org.personalized.dashboard.model.SearchContext;
import org.personalized.dashboard.model.SearchDocument;
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
                ConfigManager.getValue("elasticsearch.index"),
                ConfigManager.getValue("elasticsearch.type"),
                searchDocument.getDocumentId()
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

        return ESBootstrap.getClient().prepareDelete(ConfigManager.getValue("elasticsearch.index"),
                ConfigManager.getValue("elasticsearch.type"), documentId).execute().actionGet();
    }

    public List<SearchDocument> search(SearchContext searchContext) {
        // TODO return dummy for testing
        List<SearchDocument> searchDocuments = Lists.newArrayList();
        SearchDocument searchDocument = new SearchDocument();
        searchDocument.setDocumentId("12345");
        searchDocument.setEntityType(EntityType.BOOKMARK);
        searchDocument.setTitle("sudan");
        searchDocument.setDescription("desc1");
        searchDocuments.add(searchDocument);

        searchDocument = new SearchDocument();
        searchDocument.setDocumentId("12345");
        searchDocument.setEntityType(EntityType.BOOKMARK);
        searchDocument.setTitle("sudan");
        searchDocument.setDescription("desc1");
        searchDocuments.add(searchDocument);
        return searchDocuments;
    }
}
