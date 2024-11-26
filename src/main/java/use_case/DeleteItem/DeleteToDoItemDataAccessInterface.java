package use_case.DeleteItem;

import java.util.Optional;

import entity.ToDoItem;

/**
 * Interface for accessing and managing ToDoItem for deletion.
 */
public interface DeleteToDoItemDataAccessInterface {
    /**
     * Checks if a ToDoItem exists by its title.
     * @param title the title of the ToDoItem
     * @return true if the item exists, false otherwise
     */
    boolean existsByTitle(String title);
    /**
     * Finds a ToDoItem by its title.
     * @param title the title of the ToDoItem
     * @return an Optional containing the ToDoItem if found, or an empty Optional
     */

    Optional<ToDoItem> findByTitle(String title);

    /**
     * Deletes a ToDoItem by its title.
     *
     * @param title the title of the ToDoItem to delete
     */
    void delete(String title);
}
