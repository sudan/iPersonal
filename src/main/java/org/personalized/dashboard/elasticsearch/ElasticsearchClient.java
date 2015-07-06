package org.personalized.dashboard.elasticsearch;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.personalized.dashboard.bootstrap.ConfigManager;
import org.personalized.dashboard.bootstrap.ESBootstrap;
import org.personalized.dashboard.model.ESDocument;
import org.personalized.dashboard.model.SearchContext;
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

    public IndexResponse insertOrUpdate(ESDocument esDocument) {
        esDocument.setCreatedAt(System.currentTimeMillis());
        IndexRequest indexRequest = new IndexRequest(
                ConfigManager.getValue("elasticsearch.index"),
                ConfigManager.getValue("elasticsearch.type"),
                esDocument.getDocumentId()
        );
        Map<String, String> payload = Maps.newHashMap();
        payload.put(FieldKeys.ES_ID, esDocument.getDocumentId());
        payload.put(FieldKeys.ES_TITLE, esDocument.getTitle());
        if (StringUtils.isNotEmpty(esDocument.getDescription()))
            payload.put(FieldKeys.ES_DESCRIPTION, esDocument.getDescription().substring(0, Math.min(esDocument.getDescription().length(), 100)));
        payload.put(FieldKeys.ES_TIMESTAMP, String.valueOf(esDocument.getCreatedAt()));
        payload.put(FieldKeys.ES_ENTITY_TYPE, esDocument.getEntityType().name());
        payload.put(FieldKeys.USER_ID, sessionManager.getUserIdFromSession());

        indexRequest.source(new Gson().toJson(payload));
        return ESBootstrap.getClient().index(indexRequest).actionGet();
    }

    public DeleteResponse delete(String documentId) {

        return ESBootstrap.getClient().prepareDelete(ConfigManager.getValue("elasticsearch.index"),
                ConfigManager.getValue("elasticsearch.type"), documentId).execute().actionGet();
    }

    public List<ESDocument> search(SearchContext searchContext) {
        return null;
    }
}
