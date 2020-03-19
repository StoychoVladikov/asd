package academy.todo.entities;
import java.time.LocalDate;
import java.util.HashSet;

public class Task {
    private int id;
    private int listId;
    private String title;
    private String description;
    private boolean isComplete;
    private LocalDate creationDate;
    private int creatorId;
    private LocalDate lastEditDate;
    private int lastEditorId;
    private HashSet<Integer> assignees;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public LocalDate getLastEditDate() {
        return lastEditDate;
    }

    public void setLastEditDate(LocalDate lastEditDate) {
        this.lastEditDate = lastEditDate;
    }

    public int getLastEditorId() {
        return lastEditorId;
    }

    public void setLastEditorId(int lastEditorId) {
        this.lastEditorId = lastEditorId;
    }

    public HashSet<Integer> getAssignees() {
        return assignees;
    }

    public void setAssignees(HashSet<Integer> assignees) {
        this.assignees = assignees;
    }
}
