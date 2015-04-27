package org.personalized.dashboard.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by sudan on 27/4/15.
 */
@ActiveProfiles("test")
public class DiaryTest {

    @Test
    public void testDiaryEntity(){
        Map<String, String> placeholder1 = Maps.newHashMap();
        Map<String, String> placeholder2 = Maps.newHashMap();

        placeholder1.put("PLA123456789", "diary1-placeholder1");
        placeholder1.put("PLA223456789", "diary1-placeholder2");
        placeholder1.put("PLA323456789", "diary1-placeholder3");

        placeholder2.put("PLB123456789", "diary2-placeholder1");
        placeholder2.put("PLB223456789", "diary2-placeholder2");

        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.YEAR,2015);
        calendar1.set(Calendar.MONTH, 10);
        calendar1.set(Calendar.DATE, 15);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.YEAR,2014);
        calendar2.set(Calendar.MONTH, 10);
        calendar2.set(Calendar.DATE, 14);

        Page page1 = new Page("PAG123456789", "page1", "template1", placeholder1,calendar1);
        Page page2 = new Page("PAG123456709", "page2", "template2", placeholder2, calendar2);

        List<Page> pageList1 = Lists.newArrayList();
        pageList1.add(page1);
        pageList1.add(page2);

        Diary diary = new Diary("DIA123456789", pageList1, "USR123456789");

        Assert.assertEquals("Diary ID is DIA123456789", "DIA123456789", diary.getDiaryId());
        Assert.assertEquals("Diary UserID is USR123456789", "USR123456789", diary.getUserId());
        Assert.assertNull("CreatedOn is null on creation.Hence only Data Layer can set it",diary.getCreatedOn());
        Assert.assertNull("modifiedAt is null.Hence only data layer can set it", diary.getModifiedAt());
        Assert.assertEquals("Total number of pages in Diary is 2", 2, diary.getPages().size());

        Assert.assertEquals("Diary page1 ID is PAG123456789", "PAG123456789",diary.getPages().get(0).getPageId());
        Assert.assertEquals("Diary page2 ID is PAG123456709", "PAG123456709",diary.getPages().get(1).getPageId());

        Assert.assertEquals("Diary page1 title is page1", "page1",diary.getPages().get(0).getTitle());
        Assert.assertEquals("Diary page2 title is page2", "page2",diary.getPages().get(1).getTitle());

        Assert.assertEquals("Diary page1 template is template1", "template1",diary.getPages().get(0).getTemplate());
        Assert.assertEquals("Diary page2 template is template2", "template2",diary.getPages().get(1).getTemplate());

        Assert.assertEquals("Diary page1 placeholder1 is diary1-placeholder1", "diary1-placeholder1", diary.getPages().get(0).getPlaceholders().get("PLA123456789"));
        Assert.assertEquals("Diary page1 placeholder2 is diary1-placeholder2", "diary1-placeholder2", diary.getPages().get(0).getPlaceholders().get("PLA223456789"));
        Assert.assertEquals("Diary page1 placeholder3 is diary1-placeholder3", "diary1-placeholder3", diary.getPages().get(0).getPlaceholders().get("PLA323456789"));

        Assert.assertEquals("Diary page2 placeholder1 is diary2-placeholder1", "diary2-placeholder1", diary.getPages().get(1).getPlaceholders().get("PLB123456789"));
        Assert.assertEquals("Diary page2 placeholder2 is diary2-placeholder2", "diary2-placeholder2", diary.getPages().get(1).getPlaceholders().get("PLB223456789"));

        Assert.assertEquals("Diary page1 year is 2015", 2015, diary.getPages().get(0).getDate().get(Calendar.YEAR));
        Assert.assertEquals("Diary page1 month is 10", 10, diary.getPages().get(0).getDate().get(Calendar.MONTH));
        Assert.assertEquals("Diary page1 date is 15", 15, diary.getPages().get(0).getDate().get(Calendar.DATE));

        Assert.assertEquals("Diary page2 year is 2014", 2014, diary.getPages().get(1).getDate().get(Calendar.YEAR));
        Assert.assertEquals("Diary page2 month is 10", 10, diary.getPages().get(1).getDate().get(Calendar.MONTH));
        Assert.assertEquals("Diary page2 date is 14", 14, diary.getPages().get(1).getDate().get(Calendar.DATE));

        diary.setDiaryId("DIA123456790");
        diary.setUserId("USR123456790");
        diary.getPages().get(0).setPageId("PAG123412345");
        diary.getPages().get(0).setTitle("titlechanged");
        diary.getPages().get(0).setTemplate("templatechanged");
        diary.getPages().get(0).getPlaceholders().put("PLA123456789","placeholderchanged");

        Assert.assertEquals("Diary ID is DIA123456790", "DIA123456790", diary.getDiaryId());
        Assert.assertEquals("Diary UserID is USR123456790", "USR123456790", diary.getUserId());
        Assert.assertEquals("Diary Page1 pageID is PAG123412345","PAG123412345", diary.getPages().get(0).getPageId());
        Assert.assertEquals("Diary page1 title is titlechanged", "titlechanged", diary.getPages().get(0).getTitle());
        Assert.assertEquals("Diary page1 template is templatechanged", "templatechanged", diary.getPages().get(0).getTemplate());
        Assert.assertEquals("Diary page1 placeholder for PLA123456789 is placeholderchanged", "placeholderchanged", diary.getPages().get(0).getPlaceholders().get("PLA123456789"));

        Page page3 = new Page("PAG523456789", "page3", "template3", placeholder1,calendar1);
        diary.addPage(page3);

        Assert.assertEquals("Diary page3 ID is PAG523456789", "PAG523456789",diary.getPages().get(2).getPageId());
        Assert.assertEquals("Diary page3 title is page3", "page3",diary.getPages().get(2).getTitle());
        Assert.assertEquals("Diary page3 template is template3", "template3",diary.getPages().get(2).getTemplate());

        Assert.assertEquals("Diary page3 placeholder1 is placeholderchanged", "placeholderchanged", diary.getPages().get(2).getPlaceholders().get("PLA123456789"));
        Assert.assertEquals("Diary page3 placeholder2 is diary1-placeholder2", "diary1-placeholder2", diary.getPages().get(2).getPlaceholders().get("PLA223456789"));
        Assert.assertEquals("Diary page3 placeholder3 is diary1-placeholder3", "diary1-placeholder3", diary.getPages().get(2).getPlaceholders().get("PLA323456789"));

        Assert.assertEquals("Diary page3 year is 2015", 2015, diary.getPages().get(2).getDate().get(Calendar.YEAR));
        Assert.assertEquals("Diary page3 month is 10", 10, diary.getPages().get(2).getDate().get(Calendar.MONTH));
        Assert.assertEquals("Diary page3 date is 15", 15, diary.getPages().get(2).getDate().get(Calendar.DATE));

        diary.deletePage(page1);

        Assert.assertEquals("Diary page1 ID is PAG123456709", "PAG123456709",diary.getPages().get(0).getPageId());
        Assert.assertEquals("Diary page1 title is page2", "page2",diary.getPages().get(0).getTitle());
        Assert.assertEquals("Diary page1 template is template2", "template2",diary.getPages().get(0).getTemplate());

        Assert.assertEquals("Diary page1 placeholder1 is diary2-placeholder1", "diary2-placeholder1", diary.getPages().get(0).getPlaceholders().get("PLB123456789"));
        Assert.assertEquals("Diary page1 placeholder2 is diary2-placeholder2", "diary2-placeholder2", diary.getPages().get(0).getPlaceholders().get("PLB223456789"));

        Assert.assertEquals("Diary page1 year is 2014", 2014, diary.getPages().get(0).getDate().get(Calendar.YEAR));
        Assert.assertEquals("Diary page1 month is 10", 10, diary.getPages().get(0).getDate().get(Calendar.MONTH));
        Assert.assertEquals("Diary page1 date is 14", 14, diary.getPages().get(0).getDate().get(Calendar.DATE));

    }
}