package org.personalized.dashboard.queue;

import org.personalized.dashboard.model.Bookmark;
import org.personalized.dashboard.model.EntityType;
import org.personalized.dashboard.model.OperationType;

/**
 * Created by sudan on 4/7/15.
 */
public class BookmarkESIndexProducer implements ESIndexProducer<Bookmark> {

    @Override
    public void enqueue(Bookmark obj, EntityType entityType, OperationType operationType, String entityId) {
    }
}
