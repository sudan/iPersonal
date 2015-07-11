package org.personalized.dashboard.model;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

/**
 * Created by sudan on 11/7/15.
 */
@ActiveProfiles("test")
public class SearchDocumentTest {

    @Test
    public void testSearchDocumentEntity() {

        SearchDocument searchDocument = new SearchDocument("BOK123456789", EntityType.NOTE, "title", "description");
        Assert.assertEquals("DocumentId is BOK123456789", "BOK123456789", searchDocument.getDocumentId());
        Assert.assertEquals("EntityType is NOTE", EntityType.NOTE.name(), searchDocument.getEntityType().name());
        Assert.assertEquals("Title is title", "title", searchDocument.getTitle());
        Assert.assertEquals("Description is description", "description", searchDocument.getDescription());

        searchDocument.setDocumentId("BOK2345678901");
        searchDocument.setTitle("title1");
        searchDocument.setDescription("description1");
        searchDocument.setEntityType(EntityType.BOOKMARK);

        Assert.assertEquals("DocumentId is BOK2345678901", "BOK2345678901", searchDocument.getDocumentId());
        Assert.assertEquals("EntityType is BOOKMARK", EntityType.BOOKMARK.name(), searchDocument.getEntityType().name());
        Assert.assertEquals("Title is title1", "title1", searchDocument.getTitle());
        Assert.assertEquals("Description is description1", "description1", searchDocument.getDescription());

    }
}
