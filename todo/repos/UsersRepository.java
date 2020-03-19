package academy.todo.repos;

import academy.todo.entities.User;
import academy.todo.exceptions.DataAccessException;

import java.util.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

public class UsersRepository {

    private static final String USERS_COMPLETE_DATA_FILE_NAME = "usersCompleteData.txt";
    private static final String TEMP_FILE_NAME_PREFIX = "temp_";

    public UsersRepository() throws IOException {

        Path completeDataFile = Paths.get(USERS_COMPLETE_DATA_FILE_NAME);

        if (!Files.exists(completeDataFile)) {
            Files.createFile(completeDataFile);

            User admin = new User();

            admin.setId(1);
            admin.setUsername("admin");
            admin.setPassword("adminpass");
            admin.setFirstName("Administrator");
            admin.setLastName("Administrator");
            admin.setCreationDate(LocalDate.now());
            admin.setCreatorId(0);
            admin.setLastModificationDate(LocalDate.now());
            admin.setLastModifierId(1);
            admin.setAdmin(true);

            addUser(admin);
        }
    }

    public void addUser(User user) {

        try(FileWriter fileWriter = new FileWriter(USERS_COMPLETE_DATA_FILE_NAME, true);
            PrintWriter printWriter = new PrintWriter(fileWriter)) {

            printWriter.println(user.getId());
            printWriter.println(user.getUsername());
            printWriter.println(user.getPassword());
            printWriter.println(user.getFirstName());
            printWriter.println(user.getLastName());
            printWriter.println(user.getCreationDate());
            printWriter.println(user.getCreatorId());
            printWriter.println(user.getLastModificationDate());
            printWriter.println(user.getLastModifierId());
            printWriter.println(user.isAdmin());
        } catch(Exception ex) {
            throw new DataAccessException(ex);
        }
    }

    public User searchByUsernameAndPassword(String username, String password) {

        User foundUser = null;

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(USERS_COMPLETE_DATA_FILE_NAME))) {

            String value = "";
            while ((value = bufferedReader.readLine()) != null) {

                User user = new User();

                user.setId(Integer.parseInt(value));
                user.setUsername(bufferedReader.readLine());
                user.setPassword(bufferedReader.readLine());
                user.setFirstName(bufferedReader.readLine());
                user.setLastName(bufferedReader.readLine());
                user.setCreationDate(LocalDate.parse(bufferedReader.readLine()));
                user.setCreatorId(Integer.parseInt(bufferedReader.readLine()));
                user.setLastModificationDate(LocalDate.parse(bufferedReader.readLine()));
                user.setLastModifierId(Integer.parseInt(bufferedReader.readLine()));
                user.setAdmin(Boolean.parseBoolean(bufferedReader.readLine()));

                if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                    foundUser = user;
                    break;
                }
            }
            return foundUser;
        } catch (Exception ex) {
            throw new DataAccessException(ex);
        }
    }

    public int getNextId() {

        int nextId = 0;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(USERS_COMPLETE_DATA_FILE_NAME))) {

            String value = "";
            while ((value = bufferedReader.readLine()) != null) {

                User user = new User();
                user.setId(Integer.parseInt(value));

                user.setUsername(bufferedReader.readLine());
                user.setPassword(bufferedReader.readLine());
                user.setFirstName(bufferedReader.readLine());
                user.setLastName(bufferedReader.readLine());
                user.setCreationDate(LocalDate.parse(bufferedReader.readLine()));
                user.setCreatorId(Integer.parseInt(bufferedReader.readLine()));
                user.setLastModificationDate(LocalDate.parse(bufferedReader.readLine()));
                user.setLastModifierId(Integer.parseInt(bufferedReader.readLine()));
                user.setAdmin(Boolean.parseBoolean(bufferedReader.readLine()));

                if (nextId < user.getId()) {
                    nextId = user.getId();
                }
            }

            return nextId + 1;
        } catch(Exception ex) {
            throw new DataAccessException(ex);
        }
    }

    public ArrayList<User> listUsers() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(USERS_COMPLETE_DATA_FILE_NAME))) {
            ArrayList<User> listedUsers = new ArrayList<>();
            String line = "";

            while ((line = bufferedReader.readLine()) != null) {

                User user = new User();

                user.setId(Integer.parseInt(line));
                user.setUsername(bufferedReader.readLine());
                user.setPassword(bufferedReader.readLine());
                user.setFirstName(bufferedReader.readLine());
                user.setLastName(bufferedReader.readLine());
                user.setCreationDate(LocalDate.parse(bufferedReader.readLine()));
                user.setCreatorId(Integer.parseInt(bufferedReader.readLine()));
                user.setLastModificationDate(LocalDate.parse(bufferedReader.readLine()));
                user.setLastModifierId(Integer.parseInt(bufferedReader.readLine()));
                user.setAdmin(Boolean.parseBoolean(bufferedReader.readLine()));

                listedUsers.add(user);
            }

            return listedUsers;
            
        } catch(Exception ex) {
            throw new DataAccessException(ex);
        }
    }

    public void deleteUser(User user) {

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(USERS_COMPLETE_DATA_FILE_NAME));
             PrintWriter printWriter = new PrintWriter(new FileWriter(TEMP_FILE_NAME_PREFIX + USERS_COMPLETE_DATA_FILE_NAME, true))) {

            String value = "";
            while ((value = bufferedReader.readLine()) != null) {

                User currentUser = new User();
                currentUser.setId(Integer.parseInt(value));

                currentUser.setUsername(bufferedReader.readLine());
                currentUser.setPassword(bufferedReader.readLine());
                currentUser.setFirstName(bufferedReader.readLine());
                currentUser.setLastName(bufferedReader.readLine());
                currentUser.setCreationDate(LocalDate.parse(bufferedReader.readLine()));
                currentUser.setCreatorId(Integer.parseInt(bufferedReader.readLine()));
                currentUser.setLastModificationDate(LocalDate.parse(bufferedReader.readLine()));
                currentUser.setLastModifierId(Integer.parseInt(bufferedReader.readLine()));
                currentUser.setAdmin(Boolean.parseBoolean(bufferedReader.readLine()));

                if (currentUser.getId() != user.getId()) {
                    printWriter.println(currentUser.getId());
                    printWriter.println(currentUser.getUsername());
                    printWriter.println(currentUser.getPassword());
                    printWriter.println(currentUser.getFirstName());
                    printWriter.println(currentUser.getLastName());
                    printWriter.println(currentUser.getCreationDate());
                    printWriter.println(currentUser.getCreatorId());
                    printWriter.println(currentUser.getLastModificationDate());
                    printWriter.println(currentUser.getLastModifierId());
                    printWriter.println(currentUser.isAdmin());
                }

            }
            bufferedReader.close();
            printWriter.close();

            Files.delete(Paths.get(USERS_COMPLETE_DATA_FILE_NAME));
            Files.move(Paths.get(TEMP_FILE_NAME_PREFIX + USERS_COMPLETE_DATA_FILE_NAME), Paths.get(USERS_COMPLETE_DATA_FILE_NAME));

        } catch (Exception ex) {
            throw new DataAccessException(ex);
        }
    }

    public void editUser(User user) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(USERS_COMPLETE_DATA_FILE_NAME));
             PrintWriter printWriter = new PrintWriter(new FileWriter(TEMP_FILE_NAME_PREFIX + USERS_COMPLETE_DATA_FILE_NAME, true));
             ) {

            String value = "";
            while((value = bufferedReader.readLine()) != null) {

                User currentUser = new User();

                currentUser.setId(Integer.parseInt(value));
                currentUser.setUsername(bufferedReader.readLine());
                currentUser.setPassword(bufferedReader.readLine());
                currentUser.setFirstName(bufferedReader.readLine());
                currentUser.setLastName(bufferedReader.readLine());
                currentUser.setCreationDate(LocalDate.parse(bufferedReader.readLine()));
                currentUser.setCreatorId(Integer.parseInt(bufferedReader.readLine()));
                currentUser.setLastModificationDate(LocalDate.parse(bufferedReader.readLine()));
                currentUser.setLastModifierId(Integer.parseInt(bufferedReader.readLine()));
                currentUser.setAdmin(Boolean.parseBoolean(bufferedReader.readLine()));

                if(user.getId() != currentUser.getId()) {
                    printWriter.println(currentUser.getId());
                    printWriter.println(currentUser.getUsername());
                    printWriter.println(currentUser.getPassword());
                    printWriter.println(currentUser.getFirstName());
                    printWriter.println(currentUser.getLastName());
                    printWriter.println(currentUser.getCreationDate());
                    printWriter.println(currentUser.getCreatorId());
                    printWriter.println(currentUser.getLastModificationDate());
                    printWriter.println(currentUser.getLastModifierId());
                    printWriter.println(currentUser.isAdmin());
                } else {
                    printWriter.println(user.getId());
                    printWriter.println(user.getUsername());
                    printWriter.println(user.getPassword());
                    printWriter.println(user.getFirstName());
                    printWriter.println(user.getLastName());
                    printWriter.println(user.getCreationDate());
                    printWriter.println(user.getCreatorId());
                    printWriter.println(user.getLastModificationDate());
                    printWriter.println(user.getLastModifierId());
                    printWriter.println(user.isAdmin());
                }
            }

            printWriter.close();
            bufferedReader.close();

            Files.delete(Paths.get(USERS_COMPLETE_DATA_FILE_NAME));
            Files.move(Paths.get(TEMP_FILE_NAME_PREFIX + USERS_COMPLETE_DATA_FILE_NAME), Paths.get(USERS_COMPLETE_DATA_FILE_NAME));


        } catch (Exception ex) {
            throw new DataAccessException(ex);
        }
    }

    public User findUserById(int id) {
        User foundUser = null;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(USERS_COMPLETE_DATA_FILE_NAME))) {
            String value = "";
            while ((value = bufferedReader.readLine()) != null) {

                User user = new User();
                user.setId(Integer.parseInt(value));

                user.setUsername(bufferedReader.readLine());
                user.setPassword(bufferedReader.readLine());
                user.setFirstName(bufferedReader.readLine());
                user.setLastName(bufferedReader.readLine());
                user.setCreationDate(LocalDate.parse(bufferedReader.readLine()));
                user.setCreatorId(Integer.parseInt(bufferedReader.readLine()));
                user.setLastModificationDate(LocalDate.parse(bufferedReader.readLine()));
                user.setLastModifierId(Integer.parseInt(bufferedReader.readLine()));
                user.setAdmin(Boolean.parseBoolean(bufferedReader.readLine()));

                if (user.getId() == id) {
                    foundUser = user;
                    break;
                }
            }
            return foundUser;
        } catch (Exception ex) {
            throw new DataAccessException(ex);
        }
    }
}
