package use_case.EditToDoItem;

import java.time.LocalDate;

public class EditToDoItemInputData {
    private final String originalTitle; // Title of the item to edit
    private final String newTitle;
    private final String description;
    private final LocalDate dueDate;
    private final int priority;
    private final boolean isCompleted;

    public EditToDoItemInputData(String originalTitle, String newTitle, String description, LocalDate dueDate, int priority, boolean isCompleted) {
        this.originalTitle = originalTitle;
        this.newTitle = newTitle;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.isCompleted = isCompleted;
    }

    // Getters
    public String getOriginalTitle() { return originalTitle; }
    public String getNewTitle() { return newTitle; }
    public String getDescription() { return description; }
    public LocalDate getDueDate() { return dueDate; }
    public int getPriority() { return priority; }
    public boolean isCompleted() { return isCompleted; }
}
