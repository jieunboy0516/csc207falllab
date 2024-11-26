package use_case.DeleteItem;

/**
 * Data class for input to the Delete ToDoItem use case.
 */
public class DeleteToDoItemInputData {
    private final String title;

    public DeleteToDoItemInputData(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
