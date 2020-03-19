package academy.todo.views;

import academy.todo.entities.Task;
import academy.todo.entities.TaskList;
import academy.todo.repos.TaskListsRepository;
import academy.todo.repos.TasksRepository;
import academy.todo.services.AuthenticationService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class TasksManagementView {

    private static final String USER_INPUT_NEGATIVE_ANSWER = "no";
    private static final String USER_INPUT_POSITIVE_ANSWER = "yes";

    public void run(int listId) throws IOException {
        while (true) {
            MenuEnumeration choice = RenderMenu();

            switch (choice) {

                case LIST: {
                    list(listId);
                    break;
                }

                case ADD: {
                    add(listId);
                    break;
                } /*
                case EDIT: {
                    edit();
                    break;
                }
                case DELETE: {
                    delete();
                    break;
                }
                case EXIT: {
                    exit();
                    break;
                }
                case COMPLETE: {
                    complete();
                    break;
                } */
            }
        }
    }

    private MenuEnumeration RenderMenu() {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("---TASKS MANAGEMENT MENU---");
            System.out.println("[1] List Tasks");
            System.out.println("[2] Add Task");
            System.out.println("[3] Edit Task");
            System.out.println("[4] Delete Task");
            System.out.println("[5] Complete Task");
            System.out.println("[6] Exit");
            System.out.println();
            System.out.print("What do you want to do? ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1": {
                    return MenuEnumeration.LIST;
                }
                case "2": {
                    return MenuEnumeration.ADD;
                }
                case "3": {
                    return MenuEnumeration.EDIT;
                }
                case "4": {
                    return MenuEnumeration.DELETE;
                }
                case "5": {
                    return MenuEnumeration.COMPLETE;
                }
                case "6": {
                    return MenuEnumeration.EXIT;
                }
                default: {
                    System.out.println("Invalid choice!");
                    System.out.println();
                    System.out.println();
                    break;
                }
            }
        }
    }

    private void list(int listID) throws IOException {
        System.out.println("---TASKS---");

        TasksRepository tasksRepository = new TasksRepository();
        ArrayList<Task> tasks = tasksRepository.listTasks();

        TaskListsRepository taskListsRepository = new TaskListsRepository();
        TaskList taskList = taskListsRepository.findTaskListById(listID);


        for (Task task : tasks) {
            if ((AuthenticationService.getCurrentLoggedUser().getId() == taskList.getCreatorId() ||
                    taskList.getSharedWithUsers().contains(AuthenticationService.getCurrentLoggedUser().getId()))
                    && task.getListId() == taskList.getId()) {
                System.out.println("Task ID: " + task.getId());
                System.out.println("List ID: " + task.getListId());
                System.out.println("Task title: " + task.getTitle());
                System.out.println("Description: " + task.getDescription());
                System.out.println("Is the task complete: ");

                if (task.isComplete()) {
                    System.out.println("yes");
                } else {
                    System.out.println("no");
                }

                System.out.println("Creation date: " + task.getCreationDate());
                System.out.println("Creator ID: " + task.getCreatorId());
                System.out.println("Last edit date: " + task.getLastEditDate());
                System.out.println("Last editor ID: " + task.getLastEditorId());

                System.out.print("Assigned to users (id): ");
                if (!task.getAssignees().isEmpty()) {
                    for (Integer id : task.getAssignees()) {
                        System.out.print(id + "; ");
                    }
                }
                System.out.println();
                System.out.println();
            }
        }
        System.out.println();

    }

    private void add(int listID) throws IOException {
        System.out.println("---ADD TASK---");

        Scanner scanner = new Scanner(System.in);

        TasksRepository tasksRepository = new TasksRepository();
        Task task = new Task();

        task.setId(tasksRepository.getNextId());

        task.setListId(listID);

        System.out.println("Enter task title: ");
        task.setTitle(scanner.nextLine());

        System.out.println("Enter task description: ");
        task.setDescription(scanner.nextLine());

        task.setComplete(false);

        task.setCreationDate(LocalDate.now());
        task.setCreatorId(AuthenticationService.getCurrentLoggedUser().getId());

        task.setLastEditDate(LocalDate.now());
        task.setLastEditorId(AuthenticationService.getCurrentLoggedUser().getId());

        tasksRepository.addTask(task);
    }

    private void edit(int listID) throws IOException {
        System.out.println("--- EDIT TASK---");

        Scanner scanner = new Scanner(System.in);

        TasksRepository tasksRepository = new TasksRepository();
        Task task = new Task();

        //TODO: finish edit implementation and implement other methods

    }
}
