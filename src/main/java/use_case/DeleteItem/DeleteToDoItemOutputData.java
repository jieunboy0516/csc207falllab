package use_case.DeleteItem;

import entity.ToDoItem;

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
