package data_access;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.ToDoItem;
import use_case.AddItem.ToDoItemDataAccessInterface;

/**
 * In-memory implementation of the DAO for storing ToDo items.
 * This implementation does NOT persist data between runs of the program.
 */
public class InMemoryToDoDataAccessObject implements ToDoItemDataAccessInterface {

    private final Map<String, ToDoItem> toDoItems = new HashMap<>();

    @Override
    public boolean existsByTitle(String title) {
        return toDoItems.containsKey(title);
    }

    @Override
    public void save(ToDoItem toDoItem) {
        toDoItems.put(toDoItem.getTitle(), toDoItem);
    }

    @Override
    public ToDoItem get(String title) {
        return toDoItems.get(title);
    }

    @Override
    public void delete(String title) {
        toDoItems.remove(title);
    }

    @Override
    public void update(ToDoItem toDoItem) {
        toDoItems.put(toDoItem.getTitle(), toDoItem);
    }

    @Override
    public Map<String, ToDoItem> getAllToDoItems() {
        // Return a copy to prevent external modification
        return new HashMap<>(toDoItems);
    }

    /**
     * Returns all the non-completed and upcoming tasks.
     * @param date this is the date
     * @return string
     */
    public List<String> getUpcomingReminders(LocalDate date) {
        final List<String> reminders = new ArrayList<>();

        // Example logic to get reminders based on the given date and check if the item is not completed
        for (ToDoItem item : toDoItems.values()) {
            if (item.getDueDate().isAfter(date) && !item.isCompleted()) {
                reminders.add(item.getTitle());
            }
        }

        return reminders;
    }

}
