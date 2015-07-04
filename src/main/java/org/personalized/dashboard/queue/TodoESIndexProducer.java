package org.personalized.dashboard.queue;

import org.personalized.dashboard.model.EntityType;
import org.personalized.dashboard.model.OperationType;
import org.personalized.dashboard.model.Todo;

/**
 * Created by sudan on 4/7/15.
 */
public class TodoESIndexProducer implements ESIndexProducer<Todo> {

    @Override
    public void enqueue(Todo obj, EntityType entityType, OperationType operationType, String entityId) {

    }
}
