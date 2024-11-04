package use_case.AddItem;

import java.time.LocalDate;

public class AddToDoItemInputData {
    private final String title;
    private final String description;
    private final LocalDate dueDate;
    private final int priority;

    public AddToDoItemInputData(String title, String description, LocalDate dueDate, int priority) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
    }

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
}
