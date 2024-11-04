package use_case.AddItem;

import entity.ToDoItem;

public interface ToDoItemDataAccessInterface {
    void save(ToDoItem toDoItem);
}
