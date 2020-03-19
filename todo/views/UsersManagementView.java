package academy.todo.views;

import academy.todo.entities.User;
import academy.todo.repos.UsersRepository;
import academy.todo.services.AuthenticationService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class UsersManagementView {

    private static final String USER_INPUT_NEGATIVE_ANSWER = "no";

    public void run() throws IOException {

        while(true) {
            MenuEnumeration choice = RenderMenu();

            switch(choice){

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
                case VIEW: {
                    view();
                    break;
                }
                case EXIT: {
                    exit();
                    break;
                }
            }
        }
    }

    private MenuEnumeration RenderMenu() {

        Scanner scanner = new Scanner(System.in);

        while(true){
            System.out.println("---USERS MANAGEMENT MENU---");
            System.out.println("[1] List Users");
            System.out.println("[2] Add user");
            System.out.println("[3] Edit User");
            System.out.println("[4] Delete User");
            System.out.println("[5] View User");
            System.out.println("[6] Exit");
            System.out.println();
            System.out.print("What do you want to do? ");

            String choice = scanner.nextLine();

            switch(choice){
                case "1":{
                    return MenuEnumeration.LIST;
                }
                case "2":{
                    return MenuEnumeration.ADD;
                }
                case "3":{
                    return MenuEnumeration.EDIT;
                }
                case "4":{
                    return MenuEnumeration.DELETE;
                }
                case "6":{
                    return MenuEnumeration.EXIT;
                }
                case "5":{
                    return MenuEnumeration.VIEW;
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
        System.out.println("---------USERS---------");

        UsersRepository usersRepository = new UsersRepository();
        ArrayList<User> users = usersRepository.listUsers();

        for (User currentUser : users) {

            System.out.println("User ID: " + currentUser.getId());
            System.out.println("Username: " + currentUser.getUsername());
            System.out.println("Password: " + currentUser.getPassword());
            System.out.println("First name: " + currentUser.getFirstName());
            System.out.println("Last name: " + currentUser.getLastName());
            System.out.println("Creation date: " + currentUser.getCreationDate());
            System.out.println("Creator ID: " + currentUser.getCreatorId());
            System.out.println("Last modification date: " + currentUser.getLastModificationDate());
            System.out.println("Last modifier ID: " + currentUser.getLastModifierId());
            System.out.print("Has admin rights: ");

            if (currentUser.isAdmin()) {
                System.out.println("yes");
            } else {
                System.out.println("no");
            }

            System.out.println();
            System.out.println();
        }
    }

    private void edit() throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("------Edit user------");

        UsersRepository usersRepository = new UsersRepository();
        ArrayList<User> users = usersRepository.listUsers();

        for (User currentUser : users) {
            System.out.println(currentUser.getUsername() + "(" + currentUser.getId() + ")");
        }
        System.out.println();

        System.out.print("Enter the id of the person you want to edit: ");

        int id = Integer.parseInt(scanner.nextLine());

        User user = usersRepository.findUserById(id);

        if(user != null) {
            System.out.println("---EDIT---");

            System.out.print("Enter new username: ");
            user.setUsername(scanner.nextLine());

            System.out.print("Enter new password: ");
            user.setPassword(scanner.nextLine());

            System.out.print("Enter new first name: ");
            user.setFirstName(scanner.nextLine());

            System.out.print("Enter new last name: ");
            user.setLastName(scanner.nextLine());

            user.setLastModificationDate(LocalDate.now());
            user.setLastModifierId(AuthenticationService.getCurrentLoggedUser().getId());

            usersRepository.editUser(user);

            System.out.println("User edited successfully");
            System.out.println();
            System.out.println();
        } else {
            System.out.println("ID doesn't exist, going back to menu...");
            System.out.println();
        }
    }

    private void add() throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("---ADD USER---");

        UsersRepository usersRepository = new UsersRepository();

        User user = new User();

        user.setId(usersRepository.getNextId());

        System.out.print("Enter username: ");
        user.setUsername(scanner.nextLine());

        System.out.print("Enter password: ");
        user.setPassword(scanner.nextLine());

        System.out.print("Enter first name: ");
        user.setFirstName(scanner.nextLine());

        System.out.print("Enter last name: ");
        user.setLastName(scanner.nextLine());

        user.setCreationDate(LocalDate.now());

        user.setCreatorId(AuthenticationService.getCurrentLoggedUser().getId());

        user.setLastModificationDate(LocalDate.now());

        user.setLastModifierId(AuthenticationService.getCurrentLoggedUser().getId());

        System.out.print("Give admin rights? (yes/no): ");
        String choice = scanner.nextLine();

        if (USER_INPUT_NEGATIVE_ANSWER.equals(choice)) {
            user.setAdmin(false);
        } else {
            user.setAdmin(true);
        }

        usersRepository.addUser(user);

        System.out.println("User added successfully!");
        System.out.println();
        System.out.println();
    }

    private void delete() throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("---DELETE---");

        UsersRepository usersRepository = new UsersRepository();
        ArrayList<User> users = usersRepository.listUsers();

        for (User currentUser : users) {
            System.out.println(currentUser.getUsername() + "(" + currentUser.getId() + ")");
        }

        System.out.print("Enter the ID of the user you want to delete: ");

        int id = Integer.parseInt(scanner.nextLine());

        User user = usersRepository.findUserById(id);

        if(user != null) {
            usersRepository.deleteUser(user);
            System.out.println("User deleted successfully!");
            System.out.println();
            System.out.println();
        } else {
            System.out.println("ID doesn't exist, going back to menu...");
            System.out.println();
        }
    }

    private void exit() {
        System.out.println("Exiting...");
        System.exit(0);
    }

    private void view() throws IOException {
        Scanner scanner = new Scanner(System.in);

        UsersRepository usersRepository = new UsersRepository();
        ArrayList<User> users = usersRepository.listUsers();

        System.out.println("Enter the ID of the user you want to see data about: ");
        for(User currentUser : users) {
            System.out.println(currentUser.getUsername() + "(" + currentUser.getId() + ")");
        }

        int id = Integer.parseInt(scanner.nextLine());

        User user = usersRepository.findUserById(id);

        if(user != null) {
            System.out.println("User ID: " + user.getId());
            System.out.println("Username: " + user.getUsername());
            System.out.println("Password: " + user.getPassword());
            System.out.println("First name: " + user.getFirstName());
            System.out.println("Last name: " + user.getLastName());
            System.out.println("Creation date: " + user.getCreationDate());
            System.out.println("Creator ID: " + user.getCreatorId());
            System.out.println("Last modification date: " + user.getLastModificationDate());
            System.out.println("Last modifier ID: " + user.getLastModifierId());
            System.out.print("Has admin rights: ");

            if (user.isAdmin()) {
                System.out.println("yes");
            } else {
                System.out.println("no");
            }

            System.out.println();
            System.out.println();
        } else {
            System.out.println("ID doesn't exist, going back to menu...");
            System.out.println();
        }
    }
}

