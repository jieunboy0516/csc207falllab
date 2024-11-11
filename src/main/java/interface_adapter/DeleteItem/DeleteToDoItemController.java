package interface_adapter.DeleteItem;

import use_case.DeleteItem.DeleteToDoItemInputBoundary;
import use_case.DeleteItem.DeleteToDoItemInputData;

public class DeleteToDoItemController {
    private final DeleteToDoItemInputBoundary interactor;

    public DeleteToDoItemController(DeleteToDoItemInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void deleteToDoItem(String title) {
        DeleteToDoItemInputData inputData = new DeleteToDoItemInputData(title);
        interactor.execute(inputData);
    }
}
