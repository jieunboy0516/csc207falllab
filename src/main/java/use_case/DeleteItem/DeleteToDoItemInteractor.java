package use_case.DeleteItem;

import entity.ToDoItem;
import java.util.Optional;


public class DeleteToDoItemInteractor implements DeleteToDoItemInputBoundary {

    private final DeleteToDoItemOutputBoundary outputBoundary;
    private final DeleteToDoItemDataAccessInterface dataAccess;

    public DeleteToDoItemInteractor(DeleteToDoItemOutputBoundary outputBoundary,
                                    DeleteToDoItemDataAccessInterface dataAccess) {
        this.outputBoundary = outputBoundary;
        this.dataAccess = dataAccess;
    }


    public void execute(DeleteToDoItemInputData inputData) {
        Optional<ToDoItem> toDoItem = dataAccess.findByTitle(inputData.getTitle());

        if (toDoItem.isPresent()) {
            dataAccess.delete(inputData.getTitle());
            outputBoundary.presentDeleteToDoItem(new DeleteToDoItemOutputData(true, "Item deleted successfully."));
        } else {
            outputBoundary.presentDeleteToDoItem(new DeleteToDoItemOutputData(false, "Item not found with title: " + inputData.getTitle()));
        }
    }
}
