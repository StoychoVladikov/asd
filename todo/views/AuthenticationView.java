package academy.todo.views;

import academy.todo.repos.TaskListsRepository;
import academy.todo.services.AuthenticationService;

import java.io.IOException;
import java.util.Scanner;

public class AuthenticationView {
    public void run() throws IOException {
        Scanner input = new Scanner(System.in);

        System.out.print("Username: ");
        String username = input.nextLine();
        System.out.print("Password: ");
        String password = input.nextLine();

        System.out.println();

        AuthenticationService authenticationService = new AuthenticationService();

        if(authenticationService.authenticate(username, password)) {
            System.out.println("Successful login!");
            System.out.println();

            if(AuthenticationService.getCurrentLoggedUser().isAdmin()) {
                UsersManagementView usersManagementView = new UsersManagementView();
                usersManagementView.run();

            } else {
                TaskListsManagementView taskListsManagementView = new TaskListsManagementView();
                taskListsManagementView.run();
            }

        } else {
            System.out.println("Wrong credentials!");
            System.out.println("Exiting....");
            System.out.println();
            System.exit(0);
        }
    }
}
