package org.personalized.dashboard.queue;

import org.personalized.dashboard.model.EntityType;
import org.personalized.dashboard.model.OperationType;
import org.personalized.dashboard.model.Pin;

/**
 * Created by sudan on 4/7/15.
 */
public class PinESIndexProducer implements ESIndexProducer<Pin> {

    @Override
    public void enqueue(Pin obj, EntityType entityType, OperationType operationType, String entityId) {

    }
}
