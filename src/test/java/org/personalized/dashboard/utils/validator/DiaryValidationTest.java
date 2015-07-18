package org.personalized.dashboard.utils.validator;

import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.personalized.dashboard.model.Diary;
import org.personalized.dashboard.model.Note;
import org.personalized.dashboard.model.Page;
import org.personalized.dashboard.utils.Constants;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sudan on 18/7/15.
 */
@ActiveProfiles("test")
public class DiaryValidationTest {


    private ValidationService diaryValidationService;

    @Before
    public void initialize() {
        this.diaryValidationService = new DiaryValidationService(new
                ConstraintValidationService<Note>());
    }

    @Test
    public void testDiaryValidation() {

        Diary diary = new Diary();
        diary.setYear(2017);
        diary.setPages(new ArrayList<Page>());
        List<ErrorEntity> errorEntities = diaryValidationService.validate(diary);

        Assert.assertEquals("Error count is 1", 1 , errorEntities.size());

        Assert.assertEquals("Error name match", ErrorCodes.EMPTY_FIELD.name(), errorEntities.get(0).getName());
        Assert.assertEquals("Error desc match", "pages cannot be empty", errorEntities.get(0).getDescription());
        Assert.assertEquals("Error field match", "pages", errorEntities.get(0).getField());

        Page page1 = new Page();
        page1.setTitle("one");
        page1.setContent("two");
        page1.setDate(20);
        page1.setMonth(11);

        Page page2 = new Page();
        page2.setTitle("one");
        page2.setContent("two");
        page2.setDate(20);
        page2.setMonth(11);

        List<Page> pages = Lists.newArrayList();
        pages.add(page1);
        pages.add(page2);
        diary.setPages(pages);

        errorEntities = diaryValidationService.validate(diary);

        Assert.assertEquals("Error count is 1", 1 , errorEntities.size());

        Assert.assertEquals("Error name match", ErrorCodes.BULK_SUBMIT_NOT_ALLOWED.name(),
                errorEntities.get(0).getName());
        Assert.assertEquals("Error desc match", "Bulk submit not allowed", errorEntities.get(0).getDescription());
        Assert.assertEquals("Error field match", "pages", errorEntities.get(0).getField());

        Page page = new Page();
        page.setMonth(10);
        page.setDate(20);
        pages = Lists.newArrayList();
        pages.add(page);
        diary.setPages(pages);

        errorEntities = diaryValidationService.validate(diary);

        Assert.assertEquals("Error count is 2", 2, errorEntities.size());

        Assert.assertEquals("Error name match", ErrorCodes.EMPTY_FIELD.name(), errorEntities.get(0).getName());
        Assert.assertEquals("Error desc match", "title cannot be empty", errorEntities.get(0).getDescription());
        Assert.assertEquals("Error field match", "title", errorEntities.get(0).getField());

        Assert.assertEquals("Error name match", ErrorCodes.EMPTY_FIELD.name(), errorEntities.get(1).getName());
        Assert.assertEquals("Error desc match", "description cannot be empty", errorEntities.get(1).getDescription());
        Assert.assertEquals("Error field match", "description", errorEntities.get(1).getField());

        StringBuilder invalidTitle = new StringBuilder();
        for (int i = 0; i < Constants.TITLE_MAX_LENGTH + 1; i++) {
            invalidTitle.append("a");
        }

        StringBuilder invalidDescription = new StringBuilder();
        for (int i = 0; i < Constants.TEXT_MAX_LENGTH + 1; i++) {
            invalidDescription.append("a");
        }

        page = new Page();
        page.setTitle(invalidTitle.toString());
        page.setContent(invalidDescription.toString());
        page.setMonth(10);
        page.setDate(21);
        pages = Lists.newArrayList();
        pages.add(page);
        diary.setPages(pages);

        errorEntities = diaryValidationService.validate(diary);

        Assert.assertEquals("Error count is 2", 2 , errorEntities.size());


        Assert.assertEquals("Error name match", ErrorCodes.LENGTH_EXCEEDED.name(), errorEntities.get(0).getName());
        Assert.assertEquals("Error desc match", "title cannot exceed 50 characters", errorEntities.get(0).getDescription());
        Assert.assertEquals("Error field match", "title", errorEntities.get(0).getField());

        Assert.assertEquals("Error name match", ErrorCodes.LENGTH_EXCEEDED.name(), errorEntities.get(1).getName());
        Assert.assertEquals("Error desc match",
                "description cannot exceed 3,000 characters", errorEntities.get(1).getDescription());
        Assert.assertEquals("Error field match", "description", errorEntities.get(1).getField());

        diary = new Diary();
        diary.setYear(2015);
        page = new Page();
        page.setTitle("title");
        page.setContent("content");
        page.setDate(31);
        page.setMonth(2);
        pages = Lists.newArrayList();
        pages.add(page);
        diary.setPages(pages);

        errorEntities = diaryValidationService.validate(diary);

        Assert.assertEquals("Error count is 1", 1, errorEntities.size());

        Assert.assertEquals("Error name match", ErrorCodes.INVALID_VALUE.name(), errorEntities.get(0).getName());
        Assert.assertEquals("Error desc match", "Invalid value", errorEntities.get(0).getDescription());
        Assert.assertEquals("Error field match", "date", errorEntities.get(0).getField());
    }
}
