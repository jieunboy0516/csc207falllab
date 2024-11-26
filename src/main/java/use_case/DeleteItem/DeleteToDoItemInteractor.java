package use_case.DeleteItem;

import use_case.AddItem.ToDoItemDataAccessInterface;

/**
 * Interface for the Delete ToDoItem use case.
 */
public class DeleteToDoItemInteractor implements DeleteToDoItemInputBoundary {
    private final ToDoItemDataAccessInterface toDoDataAccess;

    public DeleteToDoItemInteractor(ToDoItemDataAccessInterface toDoDataAccess) {
        this.toDoDataAccess = toDoDataAccess;
    }

    @Override
    public void execute(DeleteToDoItemInputData inputData) {
        if (toDoDataAccess.existsByTitle(inputData.getTitle())) {
            toDoDataAccess.delete(inputData.getTitle());
        }
    }
}
