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
        if(CollectionUtils.isEmpty(todo.getTasks()) || todo.getTasks().size() == 0) {
            ErrorEntity errorEntity = new ErrorEntity(ErrorCodes.EMPTY_TASK_LIST.name(), ErrorCodes.EMPTY_TASK_LIST.getDescription());
            errorEntities.add(errorEntity);
        }
        else if(todo.getTasks().size() > Constants.MAX_TASK_SIZE) {
            ErrorEntity errorEntity = new ErrorEntity(ErrorCodes.TASKS_LENGTH_EXCEEDED.name(),
                    MessageFormat.format(ErrorCodes.TASKS_LENGTH_EXCEEDED.getDescription(), Constants.MAX_TASK_SIZE));
            errorEntities.add(errorEntity);
        }
    }

    private void validateName(Todo todo, List<ErrorEntity> errorEntities) {
        if(!CollectionUtils.isEmpty(todo.getTasks())){
            for(Task task : todo.getTasks()) {
                if(StringUtils.isEmpty(task.getName())){
                    ErrorEntity errorEntity = new ErrorEntity(ErrorCodes.EMPTY_TASK_NAME.name(), ErrorCodes.EMPTY_TASK_NAME.getDescription());
                    errorEntities.add(errorEntity);
                }
            }
        }
    }
}
