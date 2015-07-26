package org.personalized.dashboard.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.personalized.dashboard.bootstrap.ConfigManager;
import org.personalized.dashboard.bootstrap.MongoBootstrap;
import org.personalized.dashboard.dao.api.DiaryDao;
import org.personalized.dashboard.dao.impl.DiaryDaoImpl;
import org.personalized.dashboard.model.Page;
import org.personalized.dashboard.utils.ConfigKeys;
import org.personalized.dashboard.utils.Constants;
import org.personalized.dashboard.utils.generator.IdGenerator;
import org.personalized.dashboard.utils.htmltidy.DOMParser;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by sudan on 18/7/15.
 */
@ActiveProfiles("test")
public class DiaryDaoTest {

    private DiaryDao diaryDao;

    @Before
    public void initialize() throws IOException {
        ConfigManager.init();
        this.diaryDao = new DiaryDaoImpl(new IdGenerator());
    }

    @Test
    public void testBookmarkDao() {

        Boolean isDebugMode = Boolean.valueOf(ConfigKeys.MONGO_DEBUG_FLAG);

        /*
            To run these test cases enable isDebugMode in config.properties

         */
        if (isDebugMode) {
            MongoBootstrap.init();
            MongoBootstrap.getMongoDatabase().getCollection(Constants.DIARIES).drop();

            Page page1 = new Page();
            page1.setTitle("title1");
            String content1 = "<html><head><script></script></head><body><ul><li>Hello</li></ul></body></html>";
            DOMParser domParser = new DOMParser();
            page1.setContent(domParser.removeMalformedTags(content1));
            page1.setMonth(11);
            page1.setDate(12);
            page1.setSummary(domParser.removeHtmlTags(content1));
            String pageId1 = diaryDao.create(page1, 2015, "1");


            Page page2 = new Page();
            page2.setTitle("title2");
            String content2 = "<html><head><script></script></head><body><div><p>Halo</p></div></body></html>";
            page2.setContent(domParser.removeMalformedTags(content2));
            page2.setMonth(10);
            page2.setDate(21);
            page2.setSummary(domParser.removeHtmlTags(content2));
            String pageId2 = diaryDao.create(page2, 2016, "1");

            Page pageRead1 = diaryDao.get(pageId1, 2015, "1");
            Page pageRead2 = diaryDao.get(pageId2, 2016, "1");
            Page pageRead3 = diaryDao.get(pageId1, 2017, "1");

            Assert.assertEquals("PageId match", pageId1, pageRead1.getPageId());
            Assert.assertEquals("Title match", "title1", pageRead1.getTitle());
            Assert.assertEquals("Desc match", "<ul><li>Hello</li></ul>", pageRead1.getContent());
            Assert.assertEquals("Summary match", "Hello", pageRead1.getSummary());
            Assert.assertEquals("Month match", 11, pageRead1.getMonth());
            Assert.assertEquals("Date match", 12, pageRead1.getDate());
            Assert.assertNotNull("CreatedOn match", pageRead1.getCreatedOn());
            Assert.assertNotNull("ModifiedOn match", pageRead1.getModifiedAt());

            Assert.assertEquals("PageId match", pageId2, pageRead2.getPageId());
            Assert.assertEquals("Title match", "title2", pageRead2.getTitle());
            Assert.assertEquals("Desc match", "<div><p>Halo</p></div>", pageRead2.getContent());
            Assert.assertEquals("Summary match", "Halo", pageRead2.getSummary());
            Assert.assertEquals("Month match", 10, pageRead2.getMonth());
            Assert.assertEquals("Date match", 21, pageRead2.getDate());
            Assert.assertNotNull("CreatedOn match", pageRead2.getCreatedOn());
            Assert.assertNotNull("ModifiedOn match", pageRead2.getModifiedAt());

            Assert.assertNull("Null match", pageRead3);

            page2.setPageId(pageId2);
            page2.setTitle("titlechanged");
            content2 = "<html><head><script></script></head><body><div><p>Halo good boy</p></div></body></html>";
            page2.setContent(domParser.removeMalformedTags(content2));
            page2.setSummary(domParser.removeHtmlTags(content2));
            page2.setMonth(11);
            page2.setDate(30);
            diaryDao.update(page2, 2017, "1");

            Page pageRead4 = diaryDao.get(pageId2, 2016, "1");
            Page pageRead5 = diaryDao.get(pageId2, 2017, "1");

            Assert.assertNull("Null match", pageRead4);

            Assert.assertEquals("PageId match", pageId2, pageRead5.getPageId());
            Assert.assertEquals("Title match", "titlechanged", pageRead5.getTitle());
            Assert.assertEquals("Desc match", "<div><p>Halo good boy</p></div>", pageRead5.getContent());
            Assert.assertEquals("Summary match", "Halo good boy", pageRead5.getSummary());
            Assert.assertEquals("Month match", 11, pageRead5.getMonth());
            Assert.assertEquals("Date match", 30, pageRead5.getDate());
            Assert.assertNotNull("CreatedOn match", pageRead5.getCreatedOn());
            Assert.assertNotNull("ModifiedOn match", pageRead5.getModifiedAt());

            Long count = diaryDao.count("1");
            Assert.assertEquals("Count match", 2L, (long) count);

            Map<Integer, List<Page>> pageMap = diaryDao.getAll(10, 0, "1");

            List<Page> pageList1 = pageMap.get(2015);
            List<Page> pageList2 = pageMap.get(2017);

            Assert.assertNull("Null match", pageMap.get(2016));

            Assert.assertEquals("List size match", 1, pageList1.size());
            Assert.assertEquals("List size match", 1, pageList2.size());

            Assert.assertEquals("PageId match", pageId1, pageList1.get(0).getPageId());
            Assert.assertEquals("Title match", "title1", pageList1.get(0).getTitle());
            Assert.assertNull("Desc match", pageList1.get(0).getContent());
            Assert.assertEquals("Summary match", "Hello", pageList1.get(0).getSummary());
            Assert.assertEquals("Month match", 11, pageList1.get(0).getMonth());
            Assert.assertEquals("Date match", 12, pageList1.get(0).getDate());

            Assert.assertEquals("PageId match", pageId2, pageList2.get(0).getPageId());
            Assert.assertEquals("Title match", "titlechanged", pageList2.get(0).getTitle());
            Assert.assertNull("Desc match", pageList2.get(0).getContent());
            Assert.assertEquals("Summary match", "Halo good boy", pageList2.get(0).getSummary());
            Assert.assertEquals("Month match", 11, pageList2.get(0).getMonth());
            Assert.assertEquals("Date match", 30, pageList2.get(0).getDate());

            diaryDao.delete(pageId2, 2017, "1");
            count = diaryDao.count("1");

            Assert.assertEquals("Count match", 1, (long) count);

            Page pageRead6 = diaryDao.get(pageId1, 2015, "1");
            Page pageRead7 = diaryDao.get(pageId2, 2017, "1");

            Assert.assertNull("null match", pageRead7);
            Assert.assertNotNull("not Null match", pageRead6);
        }
    }
}