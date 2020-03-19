package academy.todo.services;

import academy.todo.entities.User;
import academy.todo.repos.UsersRepository;

import java.io.IOException;

public class AuthenticationService {

    private static User loggedUser;

    public boolean authenticate(String username, String password) throws IOException {
        UsersRepository usersRepository = new UsersRepository();
        if(usersRepository.searchByUsernameAndPassword(username, password) == null) {
            return false;
        } else {
            loggedUser = usersRepository.searchByUsernameAndPassword(username, password);
            return true;
        }
    }

    public static User getCurrentLoggedUser() {
        return loggedUser;
    }
}
