package org.personalized.dashboard.utils.validator;

import com.google.common.collect.Lists;
import org.hibernate.validator.constraints.NotEmpty;
import org.personalized.dashboard.model.Todo;
import org.personalized.dashboard.utils.Constants;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.text.MessageFormat;
import java.util.List;
import java.util.Set;

/**
 * Created by sudan on 31/5/15.
 */
public class TodoValidationService implements ValidationService<Todo> {


    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();

    @Override
    public List<ErrorEntity> validate(Todo todo) {
        List<ErrorEntity> errorEntities = Lists.newArrayList();
        validateConstraints(todo, errorEntities);
        validateTasks(todo, errorEntities);
        return errorEntities;
    }

    private void validateTasks(Todo todo, List<ErrorEntity> errorEntities) {
        if(todo.getTasks().size() > Constants.MAX_TASK_SIZE) {
            ErrorEntity errorEntity = new ErrorEntity(ErrorCodes.TASKS_LENGTH_EXCEEDED.name(),
                    MessageFormat.format(ErrorCodes.TASKS_LENGTH_EXCEEDED.getDescription(), Constants.MAX_TASK_SIZE));
            errorEntities.add(errorEntity);
        }
    }


    private void validateConstraints(Todo todo, List<ErrorEntity> errorEntities) {
        Set<ConstraintViolation<Todo>> constraintViolations = validator.validate(todo);
        for(ConstraintViolation<Todo> constraintViolation : constraintViolations) {

            if(constraintViolation.getConstraintDescriptor().getAnnotation().annotationType() == NotEmpty.class ||
                    constraintViolation.getConstraintDescriptor().getAnnotation().annotationType() == NotNull.class) {
                ErrorEntity errorEntity = new ErrorEntity(ErrorCodes.EMPTY_FIELD.name(),
                        MessageFormat.format(ErrorCodes.EMPTY_FIELD.getDescription(), constraintViolation.getPropertyPath().toString()));
                errorEntities.add(errorEntity);
            }
            else if(constraintViolation.getConstraintDescriptor().getAnnotation().annotationType() == Size.class) {
                ErrorEntity errorEntity = new ErrorEntity(ErrorCodes.LENGTH_EXCEEDED.name(),
                        MessageFormat.format(ErrorCodes.LENGTH_EXCEEDED.getDescription(), constraintViolation.getPropertyPath().toString(),
                                constraintViolation.getConstraintDescriptor().getAttributes().get("max")));
                errorEntities.add(errorEntity);
            }
        }
    }

}
