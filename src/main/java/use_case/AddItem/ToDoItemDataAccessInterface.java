package use_case.AddItem;

import entity.ToDoItem;
import java.util.Map;

public interface ToDoItemDataAccessInterface {

    /**
     * Save a new ToDo item.
     *
     * @param toDoItem The ToDo item to be saved.
     */
    void save(ToDoItem toDoItem);

    /**
     * Check if a ToDo item exists by its title.
     *
     * @param title The title of the ToDo item.
     * @return true if the ToDo item exists, false otherwise.
     */
    boolean existsByTitle(String title);

    /**
     * Get a ToDo item by its title.
     *
     * @param title The title of the ToDo item.
     * @return The ToDo item, or null if not found.
     */
    ToDoItem get(String title);

    /**
     * Delete a ToDo item by its title.
     *
     * @param title The title of the ToDo item.
     */
    void delete(String title);

    /**
     * Update an existing ToDo item.
     *
     * @param toDoItem The ToDo item to be updated.
     */
    void update(ToDoItem toDoItem);

    /**
     * Get all ToDo items in a map.
     *
     * @return A map of all ToDo items, with titles as keys and items as values.
     */
    Map<String, ToDoItem> getAllToDoItems();
}
