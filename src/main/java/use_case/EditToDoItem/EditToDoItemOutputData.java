package use_case.EditToDoItem;

public class EditToDoItemOutputData {
    private final boolean success;
    private final String message;
    private final String title;

    public EditToDoItemOutputData(boolean success, String message, String title) {
        this.success = success;
        this.message = message;
        this.title = title;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getTitle() {
        return title;
    }
}
