package org.personalized.dashboard.model;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Iterator;
import java.util.List;

/**
 * Created by sudan on 3/4/15.
 */
public class Todo {

    private String todoId;
    private String name;
    private List<Task> tasks = Lists.newArrayList();
    private Long createdOn;
    private Long modifiedAt;

    public Todo(){

    }

    public Todo(String todoId, String name, List<Task> tasks){
        this.todoId = todoId;
        this.name = name;
        this.tasks = tasks;
    }

    public String getTodoId() {
        return todoId;
    }

    public void setTodoId(String todoId) {
        this.todoId = todoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Long getCreatedOn() {
        return createdOn;
    }

    public Long getModifiedAt() {
        return modifiedAt;
    }

    public void addTask(Task task){
        this.tasks.add(task);
    }

    public void deleteTask(Task task){
        Iterator iterator = tasks.iterator();
        while (iterator.hasNext()){
            Task t = (Task)iterator.next();
            if(t.getTaskId().equals(task.getTaskId()))
                iterator.remove();
        }
    }

    @Override
    public String toString(){
        return new ToStringBuilder(this)
                .append("todoId",todoId)
                .append("name", name)
                .append("tasks",tasks)
                .append("createdOn", createdOn)
                .append("modifiedAt", modifiedAt)
                .toString();
    }
}
