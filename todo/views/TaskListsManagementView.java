package academy.todo.views;

import academy.todo.entities.Task;
import academy.todo.entities.TaskList;
import academy.todo.entities.User;
import academy.todo.repos.TaskListsRepository;
import academy.todo.repos.TasksRepository;
import academy.todo.repos.UsersRepository;
import academy.todo.services.AuthenticationService;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

public class TaskListsManagementView {

    private static final String USER_INPUT_NEGATIVE_ANSWER = "no";
    private static final String USER_INPUT_POSITIVE_ANSWER = "yes";

    public void run() throws IOException {
        while (true) {
            MenuEnumeration choice = RenderMenu();

            switch (choice) {

                case LIST: {
                    list();
                    break;
                }
                case ADD: {
                    add();
                    break;
                }
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
                case SHARE: {
                    share();
                    break;
                }
                case OPEN: {
                    open();
                    break;
                }
            }
        }
    }

    private MenuEnumeration RenderMenu() {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("---TASKLISTS MANAGEMENT MENU---");
            System.out.println("[1] List TaskLists");
            System.out.println("[2] Add TaskList");
            System.out.println("[3] Edit TaskList");
            System.out.println("[4] Delete TaskList");
            System.out.println("[5] Share TaskList");
            System.out.println("[6] Open TaskList)");
            System.out.println("[7] Exit");
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
                    return MenuEnumeration.SHARE;
                }
                case "6": {
                    return MenuEnumeration.OPEN;
                }
                case "7": {
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

    private void list() throws IOException {
        System.out.println("---TASKLISTS---");

        TaskListsRepository taskListsRepository = new TaskListsRepository();
        ArrayList<TaskList> taskLists = taskListsRepository.listTaskLists();

        for (TaskList taskList : taskLists) {
            if (AuthenticationService.getCurrentLoggedUser().getId() == taskList.getCreatorId() ||
                    taskList.getSharedWithUsers().contains(AuthenticationService.getCurrentLoggedUser().getId())) {
                System.out.println("TaskList ID: " + taskList.getId());
                System.out.println("Title: " + taskList.getTitle());
                System.out.println("Creation Date: " + taskList.getCreationDate());
                System.out.println("Creator ID: " + taskList.getCreatorId());
                System.out.println("Last Modification Date: " + taskList.getModificationDate());
                System.out.println("Last Modifier ID:  " + taskList.getModifierId());
                System.out.print("Is it shareable? ");

                if (taskList.isShareable()) {
                    System.out.println("yes");
                } else {
                    System.out.println("no");
                }

                System.out.print("Shared with users (id): ");
                if (taskList.isShareable() && !taskList.getSharedWithUsers().isEmpty()) {
                    for (Integer id : taskList.getSharedWithUsers()) {
                        System.out.print(id + "; ");
                    }
                }
                System.out.println();
                System.out.println();
            }
        }
        System.out.println();

    }

    private void edit() throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("---EDIT TASKLIST---");

        TaskListsRepository taskListsRepository = new TaskListsRepository();
        ArrayList<TaskList> taskLists = taskListsRepository.listTaskLists();

        for (TaskList taskList : taskLists) {
            if (AuthenticationService.getCurrentLoggedUser().getId() == taskList.getCreatorId()) {
                System.out.println(taskList.getTitle() + "(" + taskList.getId() + ")");
            }
        }

        System.out.print("Enter the ID of the TaskList you want to edit: ");
        int id = Integer.parseInt(scanner.nextLine());

        TaskList taskList = taskListsRepository.findTaskListById(id);

        if (taskList != null) {
            if (AuthenticationService.getCurrentLoggedUser().getId() == taskList.getCreatorId()) {
                System.out.println("---EDIT TASKLIST---");

                System.out.println("Enter new tasklist title: ");
                taskList.setTitle(scanner.nextLine());

                taskList.setModifierId(AuthenticationService.getCurrentLoggedUser().getId());

                taskList.setModificationDate(LocalDate.now());

                taskListsRepository.editTaskList(taskList);

                System.out.println("TaskList edited successfully!");
                System.out.println();
                System.out.println();
            } else {
                System.out.println("This tasklist is not yours, you can't edit it!");
                System.out.println();
            }
        } else {
            System.out.println("Invalid ID, going back to menu....");
            System.out.println();
            System.out.println();
        }
    }

    private void add() throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("---ADD TASKLIST---");

        TaskListsRepository taskListsRepository = new TaskListsRepository();
        TaskList taskList = new TaskList();

        taskList.setId(taskListsRepository.getNextId());

        System.out.println("Enter tasklist title: ");
        taskList.setTitle(scanner.nextLine());

        taskList.setCreationDate(LocalDate.now());

        taskList.setCreatorId(AuthenticationService.getCurrentLoggedUser().getId());

        taskList.setModificationDate(LocalDate.now());

        taskList.setModifierId(AuthenticationService.getCurrentLoggedUser().getId());

        System.out.println("Do you want to make the tasklist shareable? (yes/no)");

        String choice = scanner.nextLine();
        if (USER_INPUT_NEGATIVE_ANSWER.equals(choice)) {
            taskList.setShareable(false);
        } else if (USER_INPUT_POSITIVE_ANSWER.equals(choice)) {
            taskList.setShareable(true);
        }

        taskList.setSharedWithUsers(new HashSet<>());

        taskListsRepository.addTaskList(taskList);

        System.out.println("TaskList added successfully!");
        System.out.println();
        System.out.println();
    }

    private void delete() throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("---DELETE TASKLIST---");

        TaskListsRepository taskListsRepository = new TaskListsRepository();
        ArrayList<TaskList> taskLists = taskListsRepository.listTaskLists();

        for (TaskList taskList : taskLists) {
            if (AuthenticationService.getCurrentLoggedUser().getId() == taskList.getCreatorId()
                    || taskList.getSharedWithUsers().contains(AuthenticationService.getCurrentLoggedUser().getId())) {
                System.out.println(taskList.getTitle() + "(" + taskList.getId() + ")");
            }
        }

        System.out.println("Enter ID of the tasklist you want to delete: ");
        int id = Integer.parseInt(scanner.nextLine());

        TaskList taskList = taskListsRepository.findTaskListById(id);

        if (taskList != null) {
            if (AuthenticationService.getCurrentLoggedUser().getId() == taskList.getCreatorId()) {
                taskListsRepository.deleteTaskList(taskList);
                System.out.println("TaskList deleted successfully!");
                System.out.println();
                System.out.println();

            } else if (taskList.getSharedWithUsers().contains(AuthenticationService.getCurrentLoggedUser().getId())) {
                taskListsRepository.deleteTaskList(taskList);
                taskList.getSharedWithUsers().remove(AuthenticationService.getCurrentLoggedUser().getId());
                taskListsRepository.addTaskList(taskList);
                System.out.println("Your share has been deleted!");
                System.out.println();
            } else {
                System.out.println("You can't delete this tasklist!");
                System.out.println();
            }
        } else {
            System.out.println("Invalid ID, going back to menu...");
            System.out.println();
            System.out.println();
        }
    }

    private void exit() {
        System.out.println("Exiting...");
        System.exit(0);
    }

    private void share() throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("---SHARE TASKLIST---");

        TaskListsRepository taskListsRepository = new TaskListsRepository();
        ArrayList<TaskList> taskLists = taskListsRepository.listTaskLists();

        for (TaskList taskList : taskLists) {
            if (AuthenticationService.getCurrentLoggedUser().getId() == taskList.getCreatorId()) {
                System.out.println(taskList.getTitle() + "(" + taskList.getId() + ")");
            }
        }

        System.out.println("Enter id of the tasklist you want to share: ");

        int taskId = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter id of the user you want to share the tasklist with: ");

        int userId = Integer.parseInt(scanner.nextLine());

        TaskList taskList = taskListsRepository.findTaskListById(taskId);

        UsersRepository usersRepository = new UsersRepository();
        User user = usersRepository.findUserById(userId);

        if (taskList != null) {
            if(taskList.isShareable()) {
                if(user != null) {
                    if(userId != AuthenticationService.getCurrentLoggedUser().getId()) {
                        taskListsRepository.shareWith(userId, taskList);
                        System.out.println("Successfully shared!");
                        System.out.println();
                    } else {
                        System.out.println("You can't share the list with yourself!");
                        System.out.println();
                    }
                } else {
                    System.out.println("User doesn't exist");
                    System.out.println();
                }
            } else {
                System.out.println("Tasklist is not shareable!");
                System.out.println();
            }
        } else {
            System.out.println("Tasklist doesn't exist!");
            System.out.println();
        }
    }

    private void open() throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("---OPEN TASKLIST---");

        TaskListsRepository taskListsRepository = new TaskListsRepository();
        ArrayList<TaskList> taskLists = taskListsRepository.listTaskLists();

        for (TaskList taskList : taskLists) {
            if (AuthenticationService.getCurrentLoggedUser().getId() == taskList.getCreatorId()
                    || taskList.getSharedWithUsers().contains(AuthenticationService.getCurrentLoggedUser().getId())) {
                System.out.println(taskList.getTitle() + "(" + taskList.getId() + ")");
            }
        }

        System.out.println("Enter ID of the tasklist you want to open: ");
        int id = Integer.parseInt(scanner.nextLine());

        TaskList taskList = taskListsRepository.findTaskListById(id);

        if(taskList != null) {
            if(taskList.getCreatorId() == AuthenticationService.getCurrentLoggedUser().getId()
                    || taskList.getSharedWithUsers().contains(AuthenticationService.getCurrentLoggedUser().getId())) {
                TasksRepository tasksRepository = new TasksRepository();
                ArrayList<Task> tasks = tasksRepository.listTasks();

                TasksManagementView tasksManagementView = new TasksManagementView();

                tasksManagementView.run(taskList.getId());
            } else {
                System.out.println("This tasklist wasn't created by or shared with you!");
                System.out.println();
            }
        } else {
            System.out.println("This tasklist doesn't exist");
            System.out.println();
        }
    }
}
