package use_case.EditToDoItem;

public interface EditToDoItemInputBoundary {
    void execute(EditToDoItemInputData inputData);
    void openEditView(String title);
}
