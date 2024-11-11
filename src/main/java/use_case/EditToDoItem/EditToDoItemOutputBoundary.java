package use_case.EditToDoItem;

import entity.ToDoItem;

public interface EditToDoItemOutputBoundary {
    void presentEditToDoItem(EditToDoItemOutputData outputData);

    void presentUpdatedItem(ToDoItem updatedItem);
}
