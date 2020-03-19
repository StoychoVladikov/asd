package academy.todo.repos;

import academy.todo.entities.Task;
import academy.todo.exceptions.DataAccessException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

public class TasksRepository {

    private static final String TASKS_COMPLETE_DATA_FILE_NAME = "tasksCompleteData.txt";
    private static final String TEMP_FILE_NAME_PREFIX = "temp_";
    private static final String NULL_STRING = "null";

    public TasksRepository() throws IOException {
        Path tasksCompleteData = Paths.get(TASKS_COMPLETE_DATA_FILE_NAME);

        if(!Files.exists(tasksCompleteData)) {
            Files.createFile(tasksCompleteData);
        }
    }

    public void addTask(Task task) {
        try (FileWriter fileWriter = new FileWriter(TASKS_COMPLETE_DATA_FILE_NAME, true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {

            printWriter.println(task.getId());
            printWriter.println(task.getListId());
            printWriter.println(task.getTitle());
            printWriter.println(task.getDescription());
            printWriter.println(task.isComplete());
            printWriter.println(task.getCreationDate());
            printWriter.println(task.getCreatorId());
            printWriter.println(task.getLastEditDate());
            printWriter.println(task.getLastEditorId());
            printWriter.println(task.getAssignees());

        } catch (Exception ex) {
            throw new DataAccessException(ex);
        }
    }

    public ArrayList<Task> listTasks() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(TASKS_COMPLETE_DATA_FILE_NAME))) {
            ArrayList<Task> listedTasks = new ArrayList<>();
            String line = "";

            while ((line = bufferedReader.readLine()) != null) {

                Task task = new Task();

                task.setId(Integer.parseInt(line));
                task.setListId(Integer.parseInt(bufferedReader.readLine()));
                task.setTitle(bufferedReader.readLine());
                task.setDescription(bufferedReader.readLine());
                task.setComplete(Boolean.parseBoolean(bufferedReader.readLine()));
                task.setCreationDate(LocalDate.parse(bufferedReader.readLine()));
                task.setCreatorId(Integer.parseInt(bufferedReader.readLine()));
                task.setLastEditDate(LocalDate.parse(bufferedReader.readLine()));
                task.setLastEditorId(Integer.parseInt(bufferedReader.readLine()));

                HashSet<Integer> userIds = new HashSet<>();
                String userIdsString = bufferedReader.readLine().replaceAll("[\\[\\]]", "");

                if(userIdsString.length() != 0 && !NULL_STRING.equals(userIdsString)) {

                    String[] userIdsStringArray = userIdsString.split("[ ,]+");

                    for (int i = 0; i < userIdsStringArray.length; i++) {
                        userIds.add(Integer.parseInt(userIdsStringArray[i]));
                    }
                }

                task.setAssignees(userIds);

                listedTasks.add(task);
            }

            return listedTasks;

        } catch (Exception ex) {
            throw new DataAccessException(ex);
        }
    }

    public void deleteTask(Task task) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(TASKS_COMPLETE_DATA_FILE_NAME));
             PrintWriter printWriter = new PrintWriter(new FileWriter(TEMP_FILE_NAME_PREFIX + TASKS_COMPLETE_DATA_FILE_NAME, true))) {
            String value = "";
            while ((value = bufferedReader.readLine()) != null) {
                Task currentTask = new Task();

                currentTask.setId(Integer.parseInt(value));
                currentTask.setListId(Integer.parseInt(bufferedReader.readLine()));
                currentTask.setTitle(bufferedReader.readLine());
                currentTask.setDescription(bufferedReader.readLine());
                currentTask.setComplete(Boolean.parseBoolean(bufferedReader.readLine()));
                currentTask.setCreationDate(LocalDate.parse(bufferedReader.readLine()));
                currentTask.setCreatorId(Integer.parseInt(bufferedReader.readLine()));
                currentTask.setLastEditDate(LocalDate.parse(bufferedReader.readLine()));
                currentTask.setLastEditorId(Integer.parseInt(bufferedReader.readLine()));

                HashSet<Integer> userIds = new HashSet<>();
                String userIdsString = bufferedReader.readLine().replaceAll("[\\[\\]]", "");

                if(userIdsString.length() != 0 && !NULL_STRING.equals(userIdsString)) {

                    String[] userIdsStringArray = userIdsString.split("[ ,]+");

                    for (int i = 0; i < userIdsStringArray.length; i++) {
                        userIds.add(Integer.parseInt(userIdsStringArray[i]));
                    }
                }

                task.setAssignees(userIds);

                if (currentTask.getId() != task.getId()) {
                    printWriter.println(currentTask.getId());
                    printWriter.println(currentTask.getListId());
                    printWriter.println(currentTask.getTitle());
                    printWriter.println(currentTask.getDescription());
                    printWriter.println(currentTask.isComplete());
                    printWriter.println(currentTask.getCreationDate());
                    printWriter.println(currentTask.getCreatorId());
                    printWriter.println(currentTask.getLastEditDate());
                    printWriter.println(currentTask.getLastEditorId());
                    printWriter.println(currentTask.getAssignees());
                }
            }

            bufferedReader.close();
            printWriter.close();

            Files.delete(Paths.get(TASKS_COMPLETE_DATA_FILE_NAME));
            Files.move(Paths.get(TEMP_FILE_NAME_PREFIX + TASKS_COMPLETE_DATA_FILE_NAME), Paths.get(TASKS_COMPLETE_DATA_FILE_NAME));

        } catch (Exception ex) {
            throw new DataAccessException(ex);
        }
    }

    public void editTask(Task task) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(TASKS_COMPLETE_DATA_FILE_NAME));
             PrintWriter printWriter = new PrintWriter(new FileWriter(TEMP_FILE_NAME_PREFIX + TASKS_COMPLETE_DATA_FILE_NAME, true))) {
            String value = "";

            while ((value = bufferedReader.readLine()) != null) {
                Task currentTask = new Task();

                currentTask.setId(Integer.parseInt(value));
                currentTask.setListId(Integer.parseInt(bufferedReader.readLine()));
                currentTask.setTitle(bufferedReader.readLine());
                currentTask.setDescription(bufferedReader.readLine());
                currentTask.setComplete(Boolean.parseBoolean(bufferedReader.readLine()));
                currentTask.setCreationDate(LocalDate.parse(bufferedReader.readLine()));
                currentTask.setCreatorId(Integer.parseInt(bufferedReader.readLine()));
                currentTask.setLastEditDate(LocalDate.parse(bufferedReader.readLine()));
                currentTask.setLastEditorId(Integer.parseInt(bufferedReader.readLine()));

                HashSet<Integer> userIds = new HashSet<>();
                String userIdsString = bufferedReader.readLine().replaceAll("[\\[\\]]", "");

                if(userIdsString.length() != 0 && !NULL_STRING.equals(userIdsString)) {

                    String[] userIdsStringArray = userIdsString.split("[ ,]+");

                    for (int i = 0; i < userIdsStringArray.length; i++) {
                        userIds.add(Integer.parseInt(userIdsStringArray[i]));
                    }
                }

                task.setAssignees(userIds);


                if (task.getId() != currentTask.getId()) {
                    printWriter.println(currentTask.getId());
                    printWriter.println(currentTask.getListId());
                    printWriter.println(currentTask.getTitle());
                    printWriter.println(currentTask.getDescription());
                    printWriter.println(currentTask.isComplete());
                    printWriter.println(currentTask.getCreationDate());
                    printWriter.println(currentTask.getCreatorId());
                    printWriter.println(currentTask.getLastEditDate());
                    printWriter.println(currentTask.getLastEditorId());
                    printWriter.println(currentTask.getAssignees());
                } else {
                    printWriter.println(task.getId());
                    printWriter.println(task.getListId());
                    printWriter.println(task.getTitle());
                    printWriter.println(task.getDescription());
                    printWriter.println(task.isComplete());
                    printWriter.println(task.getCreationDate());
                    printWriter.println(task.getCreatorId());
                    printWriter.println(task.getLastEditDate());
                    printWriter.println(task.getLastEditorId());
                    printWriter.println(task.getAssignees());
                }
            }

            printWriter.close();
            bufferedReader.close();

            Files.delete(Paths.get(TASKS_COMPLETE_DATA_FILE_NAME));
            Files.move(Paths.get(TEMP_FILE_NAME_PREFIX + TASKS_COMPLETE_DATA_FILE_NAME), Paths.get(TASKS_COMPLETE_DATA_FILE_NAME));

        } catch (Exception ex) {
            throw new DataAccessException(ex);
        }
    }

    public Task findTaskById(int id) {
        Task foundTask = null;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(TASKS_COMPLETE_DATA_FILE_NAME))) {
            String value = "";
            while ((value = bufferedReader.readLine()) != null) {

                Task task = new Task();

                task.setId(Integer.parseInt(value));
                task.setListId(Integer.parseInt(bufferedReader.readLine()));
                task.setTitle(bufferedReader.readLine());
                task.setDescription(bufferedReader.readLine());
                task.setComplete(Boolean.parseBoolean(bufferedReader.readLine()));
                task.setCreationDate(LocalDate.parse(bufferedReader.readLine()));
                task.setCreatorId(Integer.parseInt(bufferedReader.readLine()));
                task.setLastEditDate(LocalDate.parse(bufferedReader.readLine()));
                task.setLastEditorId(Integer.parseInt(bufferedReader.readLine()));

                HashSet<Integer> userIds = new HashSet<>();
                String userIdsString = bufferedReader.readLine().replaceAll("[\\[\\]]", "");

                if(userIdsString.length() != 0 && !NULL_STRING.equals(userIdsString)) {

                    String[] userIdsStringArray = userIdsString.split("[ ,]+");

                    for (int i = 0; i < userIdsStringArray.length; i++) {
                        userIds.add(Integer.parseInt(userIdsStringArray[i]));
                    }
                }

                task.setAssignees(userIds);

                if (task.getId() == id) {
                    foundTask = task;
                    break;
                }
            }
            return foundTask;
        } catch (Exception ex) {
            throw new DataAccessException(ex);
        }
    }

    public int getNextId() {

        int nextId = 0;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(TASKS_COMPLETE_DATA_FILE_NAME))) {

            String value = "";
            while ((value = bufferedReader.readLine()) != null) {

                Task task = new Task();

                task.setId(Integer.parseInt(value));
                task.setListId(Integer.parseInt(bufferedReader.readLine()));
                task.setTitle(bufferedReader.readLine());
                task.setDescription(bufferedReader.readLine());
                task.setComplete(Boolean.parseBoolean(bufferedReader.readLine()));
                task.setCreationDate(LocalDate.parse(bufferedReader.readLine()));
                task.setCreatorId(Integer.parseInt(bufferedReader.readLine()));
                task.setLastEditDate(LocalDate.parse(bufferedReader.readLine()));
                task.setLastEditorId(Integer.parseInt(bufferedReader.readLine()));

                HashSet<Integer> userIds = new HashSet<>();
                String userIdsString = bufferedReader.readLine().replaceAll("[\\[\\]]", "");

                if(userIdsString.length() != 0 && !NULL_STRING.equals(userIdsString)) {

                    String[] userIdsStringArray = userIdsString.split("[ ,]+");

                    for (int i = 0; i < userIdsStringArray.length; i++) {
                        userIds.add(Integer.parseInt(userIdsStringArray[i]));
                    }
                }

                task.setAssignees(userIds);

                if (nextId < task.getId()) {
                    nextId = task.getId();
                }
            }

            return nextId + 1;

        } catch(Exception ex) {
            throw new DataAccessException(ex);
        }
    }
}
