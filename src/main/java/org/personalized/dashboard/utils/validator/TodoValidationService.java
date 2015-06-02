package org.personalized.dashboard.utils.validator;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.personalized.dashboard.model.Task;
import org.personalized.dashboard.model.Todo;
import org.personalized.dashboard.utils.Constants;
import org.springframework.util.CollectionUtils;

import java.text.MessageFormat;
import java.util.List;

/**
 * Created by sudan on 31/5/15.
 */
public class TodoValidationService implements ValidationService<Todo> {

    @Override
    public List<ErrorEntity> validate(Todo todo) {
        List<ErrorEntity> errorEntities = Lists.newArrayList();
        validateTasks(todo, errorEntities);
        validateName(todo, errorEntities);
        return errorEntities;
    }

    private void validateTasks(Todo todo, List<ErrorEntity> errorEntities) {
        if(CollectionUtils.isEmpty(todo.getTasks()) || (todo.getTasks().size() == 1 && StringUtils.isEmpty(todo.getTasks().get(0).getName()))) {
            ErrorEntity errorEntity = new ErrorEntity(ErrorCodes.EMPTY_TASK_LIST.name(), ErrorCodes.EMPTY_TASK_LIST.getDescription());
            errorEntities.add(errorEntity);
        }
        else if(todo.getTasks().size() > Constants.MAX_TASK_SIZE) {
            ErrorEntity errorEntity = new ErrorEntity(ErrorCodes.TASKS_LENGTH_EXCEEDED.name(),
                    MessageFormat.format(ErrorCodes.TASKS_LENGTH_EXCEEDED.getDescription(), Constants.MAX_TASK_SIZE));
            errorEntities.add(errorEntity);
        }
        else {
            boolean hasError = false;
            for(Task task : todo.getTasks()) {

                if(StringUtils.isEmpty(task.getName())) {
                    ErrorEntity errorEntity = new ErrorEntity(ErrorCodes.EMPTY_TASK_NAME.name(), ErrorCodes.EMPTY_TASK_NAME.getDescription());
                    errorEntities.add(errorEntity);
                    hasError = true;
                }
                else if(task.getName().length() > Constants.TITLE_MAX_LENGTH) {
                    ErrorEntity errorEntity = new ErrorEntity(ErrorCodes.TASK_NAME_LENGTH_EXCEEDED.name(),
                            MessageFormat.format(ErrorCodes.TASK_NAME_LENGTH_EXCEEDED.getDescription(), Constants.TITLE_MAX_LENGTH));
                    errorEntities.add(errorEntity);
                    hasError = true;
                }

                if(StringUtils.isEmpty(task.getTask())) {
                    ErrorEntity errorEntity = new ErrorEntity(ErrorCodes.EMPTY_TASK_CONTENT.name(), ErrorCodes.EMPTY_TASK_CONTENT.getDescription());
                    errorEntities.add(errorEntity);
                    hasError = true;
                }
                else if(task.getTask().length() > Constants.CONTENT_MAX_LENGTH) {
                    ErrorEntity errorEntity = new ErrorEntity(ErrorCodes.TASK_CONTENT_LENGTH_EXCEEDED.name(),
                            MessageFormat.format(ErrorCodes.TASK_CONTENT_LENGTH_EXCEEDED.getDescription(), Constants.CONTENT_MAX_LENGTH));
                    errorEntities.add(errorEntity);
                    hasError = true;
                }
                if(hasError)
                    break;
            }
        }
    }

    private void validateName(Todo todo, List<ErrorEntity> errorEntities) {
        if(StringUtils.isEmpty(todo.getName())) {
            ErrorEntity errorEntity = new ErrorEntity(ErrorCodes.EMPTY_TODO_NAME.name(), ErrorCodes.EMPTY_TODO_NAME.getDescription());
            errorEntities.add(errorEntity);
        }
        else if(todo.getName().length() > Constants.TITLE_MAX_LENGTH) {
            ErrorEntity errorEntity = new ErrorEntity(ErrorCodes.TODO_NAME_LENGTH_EXCEEDED.name(),
                    MessageFormat.format(ErrorCodes.TODO_NAME_LENGTH_EXCEEDED.getDescription(), Constants.TITLE_MAX_LENGTH));
            errorEntities.add(errorEntity);
        }
    }
}
