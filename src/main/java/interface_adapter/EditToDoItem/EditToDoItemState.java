package interface_adapter.EditToDoItem;

import java.time.LocalDate;

/**
 * The state for the ToDo Item in the EditToDoItem View Model.
 */
public class EditToDoItemState {

    private String originalTitle = ""; // Original title for identification
    private String newTitle = "";      // Updated title of the item
    private String description = "";
    private LocalDate dueDate;
    private int priority = 0;
    private boolean isCompleted = false;
    private String errorMessage = "";

    // Getters
    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getNewTitle() {
        return newTitle;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public int getPriority() {
        return priority;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    // Setters
    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public void setNewTitle(String newTitle) {
        this.newTitle = newTitle;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
