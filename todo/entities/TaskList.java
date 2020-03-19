package academy.todo.entities;

import java.time.LocalDate;
import java.util.HashSet;

public class TaskList {
    private int id;
    private String title;
    private LocalDate creationDate;
    private int creatorId;
    private LocalDate lastModificationDate;
    private int lastModifierId;
    private boolean shareable;
    private HashSet<Integer> sharedWithUsers;

    public HashSet<Integer> getSharedWithUsers() {
        return sharedWithUsers;
    }

    public void setSharedWithUsers(HashSet<Integer> sharedWithUsers) {
        this.sharedWithUsers = sharedWithUsers;
    }

    public boolean isShareable() {
        return shareable;
    }

    public void setShareable(boolean shareable) {
        this.shareable = shareable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public LocalDate getModificationDate() {
        return lastModificationDate;
    }

    public void setModificationDate(LocalDate modificationDate) {
        this.lastModificationDate = modificationDate;
    }

    public int getModifierId() {
        return lastModifierId;
    }

    public void setModifierId(int modifierId) {
        this.lastModifierId = modifierId;
    }
}
