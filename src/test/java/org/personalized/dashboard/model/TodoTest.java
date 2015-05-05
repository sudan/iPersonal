package org.personalized.dashboard.model;

import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

/**
 * Created by sudan on 10/4/15.
 */
@ActiveProfiles("test")
public class TodoTest {

    @Test
    public void todoEntityTest(){

        Task task1 = new Task("TASA23456789", Priority.HIGH, "name1","task1");
        Task task2 = new Task("TASB23456789", Priority.LOW, "name2","task2");
        Task task3 = new Task("TASC23456789", Priority.MEDIUM, "name3","task3");
        Task task4 = new Task("TASD23456789", Priority.MEDIUM, "name4","task4", 50);

        List<Task> tasks = Lists.newArrayList();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        tasks.add(task4);

        Todo todo = new Todo("TODO123456789", "todo1", tasks);

        Assert.assertTrue("Number of tasks in TODO123456789 is 4", todo.getTasks().size() == 4);
        Assert.assertEquals("TODO id is TODO123456789", "TODO123456789", todo.getTodoId());
        Assert.assertEquals("TODO name is todo1", "todo1", todo.getName());

        Assert.assertEquals("Task1 ID is TASA23456789","TASA23456789", todo.getTasks().get(0).getTaskId());
        Assert.assertEquals("Task2 ID is TASB23456789", "TASB23456789", todo.getTasks().get(1).getTaskId());
        Assert.assertEquals("Task3 ID is TASC23456789", "TASC23456789", todo.getTasks().get(2).getTaskId());
        Assert.assertEquals("Task4 ID is TASD23456789", "TASD23456789", todo.getTasks().get(3).getTaskId());

        Assert.assertEquals("Task1 priority is HIGH", Priority.HIGH, todo.getTasks().get(0).getPriority());
        Assert.assertEquals("Task2 priority is LOW", Priority.LOW, todo.getTasks().get(1).getPriority());
        Assert.assertEquals("Task3 priority is MEDIUM", Priority.MEDIUM, todo.getTasks().get(2).getPriority());
        Assert.assertEquals("Task4 priority is MEDIUM", Priority.MEDIUM, todo.getTasks().get(3).getPriority());

        Assert.assertEquals("Task1 name is name1", "name1", todo.getTasks().get(0).getName());
        Assert.assertEquals("Task2 name is name2", "name2", todo.getTasks().get(1).getName());
        Assert.assertEquals("Task3 name is name3", "name3", todo.getTasks().get(2).getName());
        Assert.assertEquals("Task4 name is name4", "name4", todo.getTasks().get(3).getName());

        Assert.assertEquals("Task1 task is task1", "task1", todo.getTasks().get(0).getTask());
        Assert.assertEquals("Task2 task is task2", "task2", todo.getTasks().get(1).getTask());
        Assert.assertEquals("Task3 task is task3", "task3", todo.getTasks().get(2).getTask());
        Assert.assertEquals("Task4 task is task4", "task4", todo.getTasks().get(3).getTask());

        Assert.assertEquals("Task1 percent completion is 0", 0, todo.getTasks().get(0).getPercentCompletion());
        Assert.assertEquals("Task2 percent completion is 0", 0, todo.getTasks().get(1).getPercentCompletion());
        Assert.assertEquals("Task3 percent completion is 0", 0, todo.getTasks().get(2).getPercentCompletion());
        Assert.assertEquals("Task4 percent completion is 50", 50, todo.getTasks().get(3).getPercentCompletion());

        todo.getTasks().get(0).setTaskId("TASD23456789");
        todo.getTasks().get(0).setName("name4");
        todo.getTasks().get(0).setPriority(Priority.MEDIUM);
        todo.getTasks().get(0).setTask("task4");
        todo.getTasks().get(0).setPercentCompletion(10);

        Assert.assertEquals("Task1 ID is TASD23456789", "TASD23456789", todo.getTasks().get(0).getTaskId());
        Assert.assertEquals("Task1 priority is MEDIUM", Priority.MEDIUM, todo.getTasks().get(0).getPriority());
        Assert.assertEquals("Task1 name is name4", "name4", todo.getTasks().get(0).getName());
        Assert.assertEquals("Task1 task is task4", "task4", todo.getTasks().get(0).getTask());
        Assert.assertEquals("Task1 percent completion is 10", 10, todo.getTasks().get(0).getPercentCompletion());
    }
}
