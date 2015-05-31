package org.personalized.dashboard.dao.impl;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.personalized.dashboard.bootstrap.MongoBootstrap;
import org.personalized.dashboard.dao.api.TodoDao;
import org.personalized.dashboard.model.Priority;
import org.personalized.dashboard.model.Task;
import org.personalized.dashboard.model.Todo;
import org.personalized.dashboard.utils.Constants;
import org.personalized.dashboard.utils.generator.IdGenerator;

import java.util.List;

import static com.mongodb.client.model.Filters.*;

/**
 * Created by sudan on 3/4/15.
 */
public class TodoDaoImpl implements TodoDao {

    private IdGenerator idGenerator;

    @Inject
    public TodoDaoImpl(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public String create(Todo todo, String userId) {
        MongoCollection<Document> collection = MongoBootstrap.getMongoDatabase().getCollection(Constants.TODOS);
        String todoId = idGenerator.generateId(Constants.TODO_PREFIX, Constants.ID_LENGTH);

        List<Document> tasks = Lists.newArrayList();
        for(Task task : todo.getTasks()) {
            String taskId = idGenerator.generateId(Constants.TASK_PREFIX, Constants.ID_LENGTH);
            Document document = new Document()
                    .append(Constants.PRIMARY_KEY, taskId)
                    .append(Constants.TASK_NAME, task.getName())
                    .append(Constants.TASK_DESC, task.getTask())
                    .append(Constants.TASK_PERCENT_COMPLETION, task.getPercentCompletion())
                    .append(Constants.TASK_PRIORITY, task.getPriority().name());
            tasks.add(document);
        }
        Document document = new Document()
                .append(Constants.PRIMARY_KEY, todoId)
                .append(Constants.TODO_NAME, todo.getName())
                .append(Constants.TASKS, tasks)
                .append(Constants.USER_ID, userId)
                .append(Constants.CREATED_ON, System.currentTimeMillis())
                .append(Constants.MODIFIED_AT, System.currentTimeMillis());
        collection.insertOne(document);
        return todoId;
    }

    @Override
    public Todo get(String todoId, String userId) {
        MongoCollection<Document> collection = MongoBootstrap.getMongoDatabase().getCollection(Constants.TODOS);
        Document document = collection.find(and
                        (
                                eq(Constants.PRIMARY_KEY, todoId),
                                eq(Constants.USER_ID, userId),
                                ne(Constants.IS_DELETED, true)
                        )
        ).first();

        if(document != null) {
            Todo todo = new Todo();
            todo.setTodoId(document.getString(Constants.PRIMARY_KEY));
            todo.setName(document.getString(Constants.TODO_NAME));
            todo.setCreatedOn(document.getLong(Constants.CREATED_ON));
            todo.setModifiedAt(document.getLong(Constants.MODIFIED_AT));
            List<Document> tasksDocuments = (List<Document>) document.get(Constants.TASKS);
            List<Task> tasks = Lists.newArrayList();
            for(Document taskDocument : tasksDocuments) {
                Task task = new Task();
                task.setTaskId(taskDocument.getString(Constants.PRIMARY_KEY));
                task.setName(taskDocument.getString(Constants.TODO_NAME));
                task.setTask(taskDocument.getString(Constants.TASK_DESC));
                task.setPriority(Priority.valueOf(taskDocument.getString(Constants.TASK_PRIORITY)));
                task.setPercentCompletion(taskDocument.getInteger(Constants.TASK_PERCENT_COMPLETION));
                tasks.add(task);
            }
            todo.setTasks(tasks);
            return todo;

        }
        return null;


    }

    @Override
    public Long update(Todo todo, String userId) {
        MongoCollection<Document> collection = MongoBootstrap.getMongoDatabase().getCollection(Constants.TODOS);

        List<Document> tasks = Lists.newArrayList();
        for(Task task : todo.getTasks()) {
            String taskId = idGenerator.generateId(Constants.TASK_PREFIX, Constants.ID_LENGTH);
            Document document = new Document()
                    .append(Constants.PRIMARY_KEY, taskId)
                    .append(Constants.TASK_NAME, task.getName())
                    .append(Constants.TASK_DESC, task.getTask())
                    .append(Constants.TASK_PERCENT_COMPLETION, task.getPercentCompletion())
                    .append(Constants.TASK_PRIORITY, task.getPriority().name());
            tasks.add(document);
        }
        Document document = new Document()
                .append(Constants.TODO_NAME, todo.getName())
                .append(Constants.TASKS, tasks)
                .append(Constants.MODIFIED_AT, System.currentTimeMillis());

        UpdateResult updateResult = collection.updateOne(
                and(
                        eq(Constants.PRIMARY_KEY, todo.getTodoId()),
                        eq(Constants.USER_ID, userId),
                        ne(Constants.IS_DELETED, true)
                ),
                new Document(Constants.SET_OPERATION, document)
        );
        return updateResult.getModifiedCount();
    }

    @Override
    public void delete(String todoId, String userId) {

    }

    @Override
    public Long count(String userId) {
        return null;
    }

    @Override
    public List<Todo> get(int limit, int offset, String userId) {
        return null;
    }
}
