package org.personalized.dashboard.elasticsearch;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.personalized.dashboard.bootstrap.ConfigManager;
import org.personalized.dashboard.bootstrap.ESBootstrap;
import org.personalized.dashboard.model.ESDocument;

import java.util.Map;

/**
 * Created by sudan on 5/7/15.
 */
public class ElasticsearchClient {

    public IndexResponse insertOrUpdate(ESDocument esDocument) {
        esDocument.setCreatedAt(System.currentTimeMillis());
        IndexRequest indexRequest = new IndexRequest(
                ConfigManager.getValue("elasticsearch.index"),
                ConfigManager.getValue("elasticsearch.type"),
                esDocument.getDocumentId()
        );
        Map<String, String> payload = Maps.newHashMap();
        payload.put("id", esDocument.getDocumentId());
        payload.put("title", esDocument.getTitle());
        if(StringUtils.isNotEmpty(esDocument.getDescription()))
            payload.put("description", esDocument.getDescription().substring(0, Math.min(esDocument.getDescription().length(), 100)));
        payload.put("timestamp", String.valueOf(esDocument.getCreatedAt()));
        payload.put("entity_type", esDocument.getEntityType().name());

        indexRequest.source(new Gson().toJson(payload));
        return ESBootstrap.getClient().index(indexRequest).actionGet();
    }

    public DeleteResponse delete(String documentId) {

        return  ESBootstrap.getClient().prepareDelete(ConfigManager.getValue("elasticsearch.index"),
                ConfigManager.getValue("elasticsearch.type"), documentId).execute().actionGet();
    }
}
