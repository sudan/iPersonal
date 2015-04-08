package org.personalized.dashboard.model;


import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
/**
 * Created by sudan on 9/4/15.
 */

@ActiveProfiles("test")
public class BookmarkTest {

    @Test
    public void bookmarkEntityTest(){
        Bookmark bookmark = new Bookmark("BOO12345", "google", "search engine", "http://www.google.com", "USR12345");
        Assert.assertEquals("BookmarkID is BOO12345", "BOO12345", bookmark.getBookmarkId());
        Assert.assertEquals("Name given to bookmark is google", "google", bookmark.getName());
        Assert.assertEquals("Description given to bookmark is search engine", "search engine", bookmark.getDescription());
        Assert.assertEquals("URL of the bookmark is http://www.google.com", "http://www.google.com", bookmark.getUrl());
        Assert.assertEquals("UserID of bookmark is USR12345", "USR12345", bookmark.getUserId());
        Assert.assertNull("CreatedOn is null on creation.Hence only Data Layer can set it",bookmark.getCreatedOn());
        Assert.assertNull("modifiedAt is null.Hence only data layer can set it", bookmark.getModifiedAt());

        bookmark.setBookmarkId("BOO123456");
        bookmark.setName("yahoo");
        bookmark.setDescription("yahoo engine");
        bookmark.setUrl("http://www.yahoo.com");
        bookmark.setUserId("USR12346");

        Assert.assertEquals("BookmarkID is BOO123456", "BOO123456", bookmark.getBookmarkId());
        Assert.assertEquals("Name given to bookmark is yahoo", "yahoo", bookmark.getName());
        Assert.assertEquals("Description given to bookmark is yahoo engine", "yahoo engine", bookmark.getDescription());
        Assert.assertEquals("URL of the bookmark is http://www.yahoo.com", "http://www.yahoo.com", bookmark.getUrl());
        Assert.assertEquals("UserID of bookmark is USR12346", "USR12346", bookmark.getUserId());


    }
}
