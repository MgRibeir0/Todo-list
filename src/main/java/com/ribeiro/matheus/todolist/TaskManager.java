package com.ribeiro.matheus.todolist;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskManager {
    private List<Task> tasks;

    public TaskManager(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    public void addTask(String title, String description) {
        Task task = new Task(title, description);
        tasks.add(task);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }

    public Task getTaskById(UUID taskId) {
        for (Task task : tasks) {
            if (task.getId().equals(taskId)) {
                return task;
            }
        }
        return null;
    }

    public List<Task> getTasks() {
        return tasks;
    }
}
