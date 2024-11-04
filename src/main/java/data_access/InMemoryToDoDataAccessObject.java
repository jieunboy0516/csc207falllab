package data_access;

import entity.ToDoItem;
import use_case.AddItem.ToDoItemDataAccessInterface;

import java.util.HashMap;
import java.util.Map;

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
        return new HashMap<>(toDoItems); // Return a copy to prevent external modification
    }
}
