package use_case.DeleteItem;

/**
 * Interface for the inputBoundary of the Delete ToDoItem use case.
 */
public interface DeleteToDoItemInputBoundary {
    /**
     * Executes the delete ToDoPtem use case.
     * @param inputData the input data required to delete the ToDoItem
     */
    void execute(DeleteToDoItemInputData inputData);
}
