package org.personalized.dashboard.model;

import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

/**
 * Created by sudan on 11/7/15.
 */
@ActiveProfiles("test")
public class SearchContextTest {

    @Test
    public void testSearchContextEntity() {

        List<EntityType> entityTypes = Lists.newArrayList();
        entityTypes.add(EntityType.NOTE);
        entityTypes.add(EntityType.BOOKMARK);

        List<String> keywords = Lists.newArrayList();
        keywords.add("one");
        keywords.add("two");

        List<String> titles = Lists.newArrayList();
        titles.add("title1");
        titles.add("title2");

        List<String> tags = Lists.newArrayList();
        tags.add("tag1");
        tags.add("tag2");

        SearchContext searchContext = new SearchContext(entityTypes, titles, tags, keywords);

        Assert.assertEquals("Entity one match", EntityType.NOTE.name(), searchContext.getEntityTypes().get(0).name());
        Assert.assertEquals("Entity two match", EntityType.BOOKMARK.name(), searchContext.getEntityTypes().get(1).name());

        Assert.assertEquals("Keyword one match", "one", searchContext.getKeywords().get(0));
        Assert.assertEquals("Keyword two match", "two", searchContext.getKeywords().get(1));

        Assert.assertEquals("Title one match", "title1", searchContext.getTitles().get(0));
        Assert.assertEquals("Title two match", "title2", searchContext.getTitles().get(1));

        Assert.assertEquals("tag one match", "tag1", searchContext.getTags().get(0));
        Assert.assertEquals("tag two match", "tag2", searchContext.getTags().get(1));

        entityTypes.add(EntityType.DIARY);
        keywords.add("three");
        titles.add("title3");
        tags.add("tag3");

        searchContext = new SearchContext(entityTypes, titles, tags, keywords);

        Assert.assertEquals("Entity one match", EntityType.NOTE.name(), searchContext.getEntityTypes().get(0).name());
        Assert.assertEquals("Entity two match", EntityType.BOOKMARK.name(), searchContext.getEntityTypes().get(1).name());
        Assert.assertEquals("Entity three match", EntityType.DIARY.name(), searchContext.getEntityTypes().get(2).name());

        Assert.assertEquals("Keyword one match", "one", searchContext.getKeywords().get(0));
        Assert.assertEquals("Keyword two match", "two", searchContext.getKeywords().get(1));
        Assert.assertEquals("Keyword three match", "three", searchContext.getKeywords().get(2));

        Assert.assertEquals("Title one match", "title1", searchContext.getTitles().get(0));
        Assert.assertEquals("Title two match", "title2", searchContext.getTitles().get(1));
        Assert.assertEquals("Title three match", "title3", searchContext.getTitles().get(2));

        Assert.assertEquals("tag one match", "tag1", searchContext.getTags().get(0));
        Assert.assertEquals("tag two match", "tag2", searchContext.getTags().get(1));
        Assert.assertEquals("tag three match", "tag3", searchContext.getTags().get(2));

    }

}
