package use_case.DeleteItem;

/**
 * Interface for the output of the Delete ToDoItem use case.
 */
public interface DeleteToDoItemOutputBoundary {
    /**
     * Presents the output of the Delete ToDoItem use case.
     * @param outputData the output data of the use case
     */
    void presentDeleteToDoItem(DeleteToDoItemOutputData outputData);
}
