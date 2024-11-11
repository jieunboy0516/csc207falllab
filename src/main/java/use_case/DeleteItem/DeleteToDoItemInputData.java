package use_case.DeleteItem;

public class DeleteToDoItemInputData {
    private final String title;

    public DeleteToDoItemInputData(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
