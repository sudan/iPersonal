package org.personalized.dashboard.utils.validator;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.personalized.dashboard.model.Diary;
import org.personalized.dashboard.model.Page;
import org.personalized.dashboard.utils.FieldKeys;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

/**
 * Created by sudan on 17/7/15.
 */
public class DiaryValidationService implements ValidationService<Diary> {

    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();
    private final ConstraintValidationService constraintValidationService;

    @Inject
    public DiaryValidationService(ConstraintValidationService constraintValidationService) {
        this.constraintValidationService = constraintValidationService;
    }

    @Override
    public List<ErrorEntity> validate(Diary diary) {
        List<ErrorEntity> errorEntities = Lists.newArrayList();
        validateConstraints(diary, errorEntities);
        validatePage(diary, errorEntities);
        validateDate(diary, errorEntities);
        return errorEntities;
    }


    private void validateConstraints(Diary diary, List<ErrorEntity> errorEntities) {

        Field fields[] = Diary.class.getDeclaredFields();
        for (Field field : fields) {
            Set<ConstraintViolation<Diary>> constraintViolations = validator.validateProperty
                    (diary, field.getName());
            for (ConstraintViolation<Diary> constraintViolation : constraintViolations) {
                constraintValidationService.validateConstraints(field, constraintViolation,
                        errorEntities);
            }
        }

        for (Page page : diary.getPages()) {
            Field pageFields[] = Page.class.getDeclaredFields();
            for (Field pageField : pageFields) {
                Set<ConstraintViolation<Page>> constraintViolations = validator.validateProperty
                        (page, pageField.getName());
                for (ConstraintViolation<Page> constraintViolation : constraintViolations) {
                    constraintValidationService.validateConstraints(pageField,
                            constraintViolation, errorEntities);
                }
            }
        }
    }

    private void validatePage(Diary diary, List<ErrorEntity> errorEntities) {

        if (diary.getPages().size() > 1) {
            ErrorEntity errorEntity = new ErrorEntity(ErrorCodes.BULK_SUBMIT_NOT_ALLOWED.name(),
                    ErrorCodes.BULK_SUBMIT_NOT_ALLOWED.getDescription(), FieldKeys.DIARY_PAGES);
            errorEntities.add(errorEntity);
        }
    }

    private void validateDate(Diary diary, List<ErrorEntity> errorEntities) {

        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);

            if (!CollectionUtils.isEmpty(diary.getPages())) {
                String date = diary.getYear() + "-" + diary.getPages().get(0).getMonth() + "-" +
                        diary.getPages().get(0).getDate();
                dateFormat.parse(date);
            }
        } catch (ParseException e) {
            ErrorEntity errorEntity = new ErrorEntity(ErrorCodes.INVALID_VALUE.name(),
                    ErrorCodes.INVALID_VALUE.getDescription(), FieldKeys.PAGE_DATE);
            errorEntities.add(errorEntity);
        }
    }
}
