package entity;

import java.time.LocalDate;

/**
 * Factory for creating ToDoItem objects.
 */
public class ToDoItemFactory {

    /**
     * Creates a new ToDoItem with the specified title, description, due date, and priority.
     *
     * @param title       the title of the to-do item
     * @param description the description of the to-do item
     * @param dueDate     the due date of the to-do item
     * @param priority    the priority of the to-do item
     * @return a new ToDoItem instance
     */
    public ToDoItem create(String title, String description, LocalDate dueDate, int priority) {
        return new ToDoItem(title, description, dueDate, priority);
    }
}
