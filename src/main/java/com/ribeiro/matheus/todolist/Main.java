package com.ribeiro.matheus.todolist;

import com.ribeiro.matheus.todolist.util.FileUtil;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static TaskManager taskManager;

    public static void main(String[] args) {
        List<Task> tasks = FileUtil.loadTasks();
        taskManager = new TaskManager(tasks);

        boolean exit = false;
        while (!exit) {
            displayMenu();
            int choice = readChoice();
            switch (choice) {
                case 1:
                    addTask();
                    break;
                case 2:
                    editTask();
                    break;
                case 3:
                    markTaskAsCompleted();
                    break;
                case 4:
                    removeTask();
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }

        FileUtil.saveTasks(taskManager.getTasks());
        System.out.println("Tarefas salvas. Encerrando o programa.");
    }

    private static void displayMenu() {
        System.out.println("===== TO-DO List =====");
        System.out.println("1. Adicionar tarefa");
        System.out.println("2. Editar tarefa");
        System.out.println("3. Marcar tarefa como concluída");
        System.out.println("4. Remover tarefa");
        System.out.println("5. Sair");
        System.out.println("======================");
    }

    private static int readChoice() {
        System.out.print("Escolha uma opção: ");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void addTask() {
        System.out.print("Digite o título da tarefa: ");
        String title = scanner.nextLine();
        System.out.print("Digite a descrição da tarefa: ");
        String description = scanner.nextLine();
        taskManager.addTask(title, description);
        System.out.println("Tarefa adicionada com sucesso!");
    }

    private static void editTask() {
        displayTasks();
        System.out.print("Digite o ID da tarefa que deseja editar: ");
        String taskIdStr = scanner.nextLine();
        try {
            Task task = getTaskById(taskIdStr);
            if (task != null) {
                System.out.print("Digite o novo título da tarefa: ");
                String newTitle = scanner.nextLine();
                System.out.print("Digite a nova descrição da tarefa: ");
                String newDescription = scanner.nextLine();
                task.setTitle(newTitle);
                task.setDescription(newDescription);
                System.out.println("Tarefa atualizada com sucesso!");
            } else {
                System.out.println("ID da tarefa não encontrado.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("ID da tarefa inválido.");
        }
    }

    private static void markTaskAsCompleted() {
        displayTasks();
        System.out.print("Digite o ID da tarefa que deseja marcar como concluída: ");
        String taskIdStr = scanner.nextLine();
        try {
            Task task = getTaskById(taskIdStr);
            if (task != null) {
                task.setCompleted(true);
                System.out.println("Tarefa marcada como concluída com sucesso!");
            } else {
                System.out.println("ID da tarefa não encontrado.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("ID da tarefa inválido.");
        }
    }

    private static void removeTask() {
        displayTasks();
        System.out.print("Digite o ID da tarefa que deseja remover: ");
        String taskIdStr = scanner.nextLine();
        try {
            Task task = getTaskById(taskIdStr);
            if (task != null) {
                taskManager.removeTask(task);
                System.out.println("Tarefa removida com sucesso!");
            } else {
                System.out.println("ID da tarefa não encontrado.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("ID da tarefa inválido.");
        }
    }

    private static Task getTaskById(String taskIdStr) {
        try {
            UUID taskId = UUID.fromString(taskIdStr);
            return taskManager.getTaskById(taskId);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("ID da tarefa inválido.");
        }
    }

    private static void displayTasks() {
        System.out.println("===== Tarefas =====");
        List<Task> tasks = taskManager.getTasks();
        for (Task task : tasks) {
            System.out.println("ID: " + task.getId());
            System.out.println("Título: " + task.getTitle());
            System.out.println("Descrição: " + task.getDescription());
            System.out.println("Concluída: " + task.isCompleted());
            System.out.println("--------------------");
        }
        System.out.println("====================");
    }
}
