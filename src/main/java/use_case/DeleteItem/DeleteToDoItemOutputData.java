package use_case.DeleteItem;

/**
 * Output data for the Delete ToDoItem use case.
 */
public class DeleteToDoItemOutputData {
    private final boolean success;
    private final String message;

    public DeleteToDoItemOutputData(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
