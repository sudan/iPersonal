package org.personalized.dashboard.model;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

/**
 * Created by sudan on 30/5/15.
 */

@ActiveProfiles("test")
public class BatchSizeTest {

    @Test
    public void testBatchSizeEntity() {

        BatchSize batchSize = new BatchSize(10, 0);
        Assert.assertEquals("Batch size limit is 10", 10, batchSize.getLimit());
        Assert.assertEquals("Batch size offset is 0", 0, batchSize.getOffset());

        batchSize.setLimit(20);
        batchSize.setOffset(1);

        Assert.assertEquals("Batch size limit is 20", 20, batchSize.getLimit());
        Assert.assertEquals("Batch size offset is 1", 1, batchSize.getOffset());
    }
}
