package com.ribeiro.matheus.todolist.util;

import com.ribeiro.matheus.todolist.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    private static final String FILE_PATH = "tasks.txt";

    public static void saveTasks(List<Task> tasks) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            for (Task task : tasks) {
                String taskLine = task.getId() + "|" + task.getTitle() + "|" + task.getDescription() + "|" + task.isCompleted();
                writer.write(taskLine + System.lineSeparator());
            }
            System.out.println("Tarefas salvas com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar as tarefas: " + e.getMessage());
        }
    }

    public static List<Task> loadTasks() {
        List<Task> tasks = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("\\|");
                    if (parts.length == 4) {
                        String idStr = parts[0];
                        String title = parts[1];
                        String description = parts[2];
                        boolean completed = Boolean.parseBoolean(parts[3]);

                        Task task = new Task(title, description);
                        task.setCompleted(completed);

                        tasks.add(task);
                    }
                }
                System.out.println("Tarefas carregadas com sucesso!");
            } catch (IOException e) {
                System.out.println("Erro ao carregar as tarefas: " + e.getMessage());
            }
        }
        return tasks;
    }
}
