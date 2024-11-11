package use_case.DeleteItem;

import entity.ToDoItem;
import java.util.Optional;

public interface DeleteToDoItemDataAccessInterface {
    boolean existsByTitle(String title);
    Optional<ToDoItem> findByTitle(String title);
    void delete(String title);
}
