package interface_adapter.addItem;

import java.time.LocalDate;

/**
 * The state for the ToDo Item in the ToDo View Model.
 */
public class AddToDoItemState {

    private String title = "";
    private String description = "";
    private LocalDate dueDate;
    private int priority = 0;
    private boolean isCompleted = false;
    private String errorMessage = "";

    // Getters
    public String getTitle() {
        return title;
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
    public void setTitle(String title) {
        this.title = title;
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
