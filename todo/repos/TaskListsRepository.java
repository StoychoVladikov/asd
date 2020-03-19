package academy.todo.repos;

import academy.todo.entities.Task;
import academy.todo.entities.TaskList;
import academy.todo.entities.User;
import academy.todo.exceptions.DataAccessException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

public class TaskListsRepository {

    private static final String LISTS_COMPLETE_DATA_FILE_NAME = "listsCompleteData.txt";
    private static final String TEMP_FILE_NAME_PREFIX = "temp_";
    private static final String NULL_STRING = "null";

    public TaskListsRepository() throws IOException {
        Path listsCompleteData = Paths.get(LISTS_COMPLETE_DATA_FILE_NAME);

        if (!Files.exists(listsCompleteData)) {
            Files.createFile(listsCompleteData);
        }
    }

    public void addTaskList(TaskList taskList) {
        try (FileWriter fileWriter = new FileWriter(LISTS_COMPLETE_DATA_FILE_NAME, true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {

            printWriter.println(taskList.getId());
            printWriter.println(taskList.getTitle());
            printWriter.println(taskList.getCreationDate());
            printWriter.println(taskList.getCreatorId());
            printWriter.println(taskList.getModificationDate());
            printWriter.println(taskList.getModifierId());
            printWriter.println(taskList.isShareable());
            printWriter.println(taskList.getSharedWithUsers());

        } catch (Exception ex) {
            throw new DataAccessException(ex);
        }
    }

    public ArrayList<TaskList> listTaskLists() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(LISTS_COMPLETE_DATA_FILE_NAME))) {
            ArrayList<TaskList> listedTaskLists = new ArrayList<>();
            String line = "";

            while ((line = bufferedReader.readLine()) != null) {

                TaskList taskList = new TaskList();

                taskList.setId(Integer.parseInt(line));
                taskList.setTitle(bufferedReader.readLine());
                taskList.setCreationDate(LocalDate.parse(bufferedReader.readLine()));
                taskList.setCreatorId(Integer.parseInt(bufferedReader.readLine()));
                taskList.setModificationDate(LocalDate.parse(bufferedReader.readLine()));
                taskList.setModifierId(Integer.parseInt(bufferedReader.readLine()));
                taskList.setShareable(Boolean.parseBoolean(bufferedReader.readLine()));

                HashSet<Integer> userIds = new HashSet<>();
                String userIdsString = bufferedReader.readLine().replaceAll("[\\[\\]]", "");

                if(userIdsString.length() != 0 && !NULL_STRING.equals(userIdsString)) {

                    String[] userIdsStringArray = userIdsString.split("[ ,]+");

                    for (int i = 0; i < userIdsStringArray.length; i++) {
                        userIds.add(Integer.parseInt(userIdsStringArray[i]));
                    }
                }

                taskList.setSharedWithUsers(userIds);


                listedTaskLists.add(taskList);
            }

            return listedTaskLists;

        } catch (Exception ex) {
            throw new DataAccessException(ex);
        }
    }

    public void deleteTaskList(TaskList taskList) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(LISTS_COMPLETE_DATA_FILE_NAME));
             PrintWriter printWriter = new PrintWriter(new FileWriter(TEMP_FILE_NAME_PREFIX + LISTS_COMPLETE_DATA_FILE_NAME, true))) {
            String value = "";
            while ((value = bufferedReader.readLine()) != null) {
                TaskList currentTaskList = new TaskList();

                currentTaskList.setId(Integer.parseInt(value));
                currentTaskList.setTitle(bufferedReader.readLine());
                currentTaskList.setCreationDate(LocalDate.parse(bufferedReader.readLine()));
                currentTaskList.setCreatorId(Integer.parseInt(bufferedReader.readLine()));
                currentTaskList.setModificationDate(LocalDate.parse(bufferedReader.readLine()));
                currentTaskList.setModifierId(Integer.parseInt(bufferedReader.readLine()));
                currentTaskList.setShareable(Boolean.parseBoolean(bufferedReader.readLine()));

                HashSet<Integer> userIds = new HashSet<>();
                String userIdsString = bufferedReader.readLine().replaceAll("[\\[\\]]", "");

                if(userIdsString.length() != 0 && !NULL_STRING.equals(userIdsString)) {

                    String[] userIdsStringArray = userIdsString.split("[ ,]+");

                    for (int i = 0; i < userIdsStringArray.length; i++) {
                        userIds.add(Integer.parseInt(userIdsStringArray[i]));
                    }
                }

                taskList.setSharedWithUsers(userIds);

                if (currentTaskList.getId() != taskList.getId()) {
                    printWriter.println(currentTaskList.getId());
                    printWriter.println(currentTaskList.getTitle());
                    printWriter.println(currentTaskList.getCreationDate());
                    printWriter.println(currentTaskList.getCreatorId());
                    printWriter.println(currentTaskList.getModificationDate());
                    printWriter.println(currentTaskList.getModifierId());
                    printWriter.println(currentTaskList.isShareable());
                    printWriter.println(currentTaskList.getSharedWithUsers());
                }
            }

            bufferedReader.close();
            printWriter.close();

            Files.delete(Paths.get(LISTS_COMPLETE_DATA_FILE_NAME));
            Files.move(Paths.get(TEMP_FILE_NAME_PREFIX + LISTS_COMPLETE_DATA_FILE_NAME), Paths.get(LISTS_COMPLETE_DATA_FILE_NAME));

        } catch (Exception ex) {
            throw new DataAccessException(ex);
        }
    }

    public void editTaskList(TaskList taskList) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(LISTS_COMPLETE_DATA_FILE_NAME));
             PrintWriter printWriter = new PrintWriter(new FileWriter(TEMP_FILE_NAME_PREFIX + LISTS_COMPLETE_DATA_FILE_NAME, true))) {
            String value = "";

            while ((value = bufferedReader.readLine()) != null) {
                TaskList currentTaskList = new TaskList();

                currentTaskList.setId(Integer.parseInt(value));
                currentTaskList.setTitle(bufferedReader.readLine());
                currentTaskList.setCreationDate(LocalDate.parse(bufferedReader.readLine()));
                currentTaskList.setCreatorId(Integer.parseInt(bufferedReader.readLine()));
                currentTaskList.setModificationDate(LocalDate.parse(bufferedReader.readLine()));
                currentTaskList.setModifierId(Integer.parseInt(bufferedReader.readLine()));
                currentTaskList.setShareable(Boolean.parseBoolean(bufferedReader.readLine()));

                HashSet<Integer> userIds = new HashSet<>();
                String userIdsString = bufferedReader.readLine().replaceAll("[\\[\\]]", "");

                if(userIdsString.length() != 0 && !NULL_STRING.equals(userIdsString)) {

                    String[] userIdsStringArray = userIdsString.split("[ ,]+");

                    for (int i = 0; i < userIdsStringArray.length; i++) {
                        userIds.add(Integer.parseInt(userIdsStringArray[i]));
                    }
                }

                taskList.setSharedWithUsers(userIds);


                if (taskList.getId() != currentTaskList.getId()) {
                    printWriter.println(currentTaskList.getId());
                    printWriter.println(currentTaskList.getTitle());
                    printWriter.println(currentTaskList.getCreationDate());
                    printWriter.println(currentTaskList.getCreatorId());
                    printWriter.println(currentTaskList.getModificationDate());
                    printWriter.println(currentTaskList.getModifierId());
                    printWriter.println(currentTaskList.isShareable());
                    printWriter.println(currentTaskList.getSharedWithUsers());
                } else {
                    printWriter.println(taskList.getId());
                    printWriter.println(taskList.getTitle());
                    printWriter.println(taskList.getCreationDate());
                    printWriter.println(taskList.getCreatorId());
                    printWriter.println(taskList.getModificationDate());
                    printWriter.println(taskList.getModifierId());
                    printWriter.println(taskList.isShareable());
                    printWriter.println(taskList.getSharedWithUsers());
                }
            }

            printWriter.close();
            bufferedReader.close();

            Files.delete(Paths.get(LISTS_COMPLETE_DATA_FILE_NAME));
            Files.move(Paths.get(TEMP_FILE_NAME_PREFIX + LISTS_COMPLETE_DATA_FILE_NAME), Paths.get(LISTS_COMPLETE_DATA_FILE_NAME));

        } catch (Exception ex) {
            throw new DataAccessException(ex);
        }
    }

    public TaskList findTaskListById(int id) {
        TaskList foundTaskList = null;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(LISTS_COMPLETE_DATA_FILE_NAME))) {
            String value = "";
            while ((value = bufferedReader.readLine()) != null) {

                TaskList taskList = new TaskList();

                taskList.setId(Integer.parseInt(value));
                taskList.setTitle(bufferedReader.readLine());
                taskList.setCreationDate(LocalDate.parse(bufferedReader.readLine()));
                taskList.setCreatorId(Integer.parseInt(bufferedReader.readLine()));
                taskList.setModificationDate(LocalDate.parse(bufferedReader.readLine()));
                taskList.setModifierId(Integer.parseInt(bufferedReader.readLine()));
                taskList.setShareable(Boolean.parseBoolean(bufferedReader.readLine()));

                HashSet<Integer> userIds = new HashSet<>();
                String userIdsString = bufferedReader.readLine().replaceAll("[\\[\\]]", "");

                if(userIdsString.length() != 0 && !NULL_STRING.equals(userIdsString)) {

                    String[] userIdsStringArray = userIdsString.split("[ ,]+");

                    for (int i = 0; i < userIdsStringArray.length; i++) {
                        userIds.add(Integer.parseInt(userIdsStringArray[i]));
                    }
                }

                taskList.setSharedWithUsers(userIds);

                if (taskList.getId() == id) {
                    foundTaskList = taskList;
                    break;
                }
            }
            return foundTaskList;
        } catch (Exception ex) {
            throw new DataAccessException(ex);
        }
    }

    public int getNextId() {

        int nextId = 0;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(LISTS_COMPLETE_DATA_FILE_NAME))) {

            String value = "";
            while ((value = bufferedReader.readLine()) != null) {

                TaskList taskList = new TaskList();
                taskList.setId(Integer.parseInt(value));
                taskList.setTitle(bufferedReader.readLine());
                taskList.setCreationDate(LocalDate.parse(bufferedReader.readLine()));
                taskList.setCreatorId(Integer.parseInt(bufferedReader.readLine()));
                taskList.setModificationDate(LocalDate.parse(bufferedReader.readLine()));
                taskList.setModifierId(Integer.parseInt(bufferedReader.readLine()));
                taskList.setShareable(Boolean.parseBoolean(bufferedReader.readLine()));

                HashSet<Integer> userIds = new HashSet<>();
                String userIdsString = bufferedReader.readLine().replaceAll("[\\[\\]]", "");

                if(userIdsString.length() != 0 && !NULL_STRING.equals(userIdsString)) {

                    String[] userIdsStringArray = userIdsString.split("[ ,]+");

                    for (int i = 0; i < userIdsStringArray.length; i++) {
                        userIds.add(Integer.parseInt(userIdsStringArray[i]));
                    }
                }

                taskList.setSharedWithUsers(userIds);

                if (nextId < taskList.getId()) {
                    nextId = taskList.getId();
                }
            }

            return nextId + 1;
        } catch(Exception ex) {
            throw new DataAccessException(ex);
        }
    }

    public void shareWith(int userId, TaskList taskList) throws IOException {
        TaskListsRepository taskListsRepository = new TaskListsRepository();

        taskListsRepository.deleteTaskList(taskList);
        taskList.getSharedWithUsers().add(userId);
        taskListsRepository.addTaskList(taskList);
    }
}
