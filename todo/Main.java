package academy.todo;

import academy.todo.entities.User;
import academy.todo.repos.UsersRepository;
import academy.todo.services.AuthenticationService;
import academy.todo.views.AuthenticationView;
import academy.todo.views.UsersManagementView;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        AuthenticationView authenticationView = new AuthenticationView();
        authenticationView.run();


    }
}
