package org.personalized.dashboard.elasticsearch;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.personalized.dashboard.model.EntityType;
import org.personalized.dashboard.model.SearchContext;
import org.personalized.dashboard.utils.FieldKeys;

/**
 * Created by sudan on 11/7/15.
 */
public class ESQueryBuilder {

    public BoolQueryBuilder getQueryBuilder(SearchContext searchContext, String userId) {

        BoolQueryBuilder searchQueryBuilder = new BoolQueryBuilder();

        BoolQueryBuilder userIdQueryBuilder = new BoolQueryBuilder();
        userIdQueryBuilder.must(QueryBuilders.termQuery(FieldKeys.USER_ID, userId));
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
