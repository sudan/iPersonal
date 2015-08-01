package org.personalized.dashboard.dao.impl;

import com.google.common.collect.Lists;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.personalized.dashboard.bootstrap.MongoBootstrap;
import org.personalized.dashboard.dao.api.ExpenseCategoryDao;
import org.personalized.dashboard.utils.Constants;
import org.personalized.dashboard.utils.FieldKeys;

import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

/**
 * Created by sudan on 2/8/15.
 */
public class ExpenseCategoryDaoImpl implements ExpenseCategoryDao {

    @Override
    public List<String> get(String userId) {
        MongoCollection<Document> collection = MongoBootstrap.getMongoDatabase().getCollection(Constants.EXPENSE_CATEGORIES);
        Document document = collection.find(and(
                eq(FieldKeys.PRIMARY_KEY, userId)
        )).first();

        if (document == null) {
            return Lists.newArrayList();
        } else {
            return (List<String>) document.get(FieldKeys.EXPENSE_CATEGORIES);
        }
    }
}
