package entity;

import java.time.LocalDate;

/**
 * A simple implementation of a ToDo item.
 */
public class ToDoItem {

    private final String title;
    private final String description;
    private final LocalDate dueDate;
    private final int priority; // e.g., 1 = High, 2 = Medium, 3 = Low
    private boolean isCompleted;

    /**
     * Constructs a ToDoItem with the specified title, description, due date, and priority.
     *
     * @param title       the title of the to-do item
     * @param description the description of the to-do item
     * @param dueDate     the due date of the to-do item
     * @param priority    the priority of the to-do item
     */
    public ToDoItem(String title, String description, LocalDate dueDate, int priority) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.isCompleted = false; // default to false when a to-do item is created
    }

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

    // Setters
    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    @Override
    public String toString() {
        return "ToDoItem{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", dueDate=" + dueDate +
                ", priority=" + priority +
                ", isCompleted=" + isCompleted +
                '}';
    }
}
