package use_case.EditToDoItem;

import entity.ToDoItem;
import java.util.Map;

public interface EditToDoItemDataAccessInterface {
    void save(ToDoItem toDoItem);
    boolean existsByTitle(String title);
    ToDoItem get(String title);
    void delete(String title);
    void update(ToDoItem toDoItem); // Added for edit functionality
    Map<String, ToDoItem> getAllToDoItems();
}
