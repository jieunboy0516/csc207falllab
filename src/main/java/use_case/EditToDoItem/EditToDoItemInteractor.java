package use_case.EditToDoItem;

import entity.ToDoItem;
import use_case.AddItem.ToDoItemDataAccessInterface;

public class EditToDoItemInteractor implements EditToDoItemInputBoundary {
    private final ToDoItemDataAccessInterface toDoDataAccess;
    private final EditToDoItemOutputBoundary outputBoundary;

    public EditToDoItemInteractor(ToDoItemDataAccessInterface toDoDataAccess, EditToDoItemOutputBoundary outputBoundary) {
        this.toDoDataAccess = toDoDataAccess;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(EditToDoItemInputData inputData) {
        if (!toDoDataAccess.existsByTitle(inputData.getOriginalTitle())) {
            outputBoundary.presentEditToDoItem(new EditToDoItemOutputData(false, "Item not found.", "nth"));
            return;
        }

        // Delete the old item with the original title
        toDoDataAccess.delete(inputData.getOriginalTitle());

        // Create and save the updated item
        ToDoItem updatedItem = new ToDoItem(inputData.getNewTitle(), inputData.getDescription(), inputData.getDueDate(), inputData.getPriority());
        updatedItem.setCompleted(inputData.isCompleted());
        toDoDataAccess.save(updatedItem);

        // Present the updated view
        outputBoundary.presentEditToDoItem(new EditToDoItemOutputData(true, "Item updated successfully.", inputData.getNewTitle()));
    }


    public void openEditView(String title) {
        if (toDoDataAccess.existsByTitle(title)) {
            ToDoItem item = toDoDataAccess.get(title);
            EditToDoItemInputData inputData = new EditToDoItemInputData(title, item.getTitle(), item.getDescription(), item.getDueDate(), item.getPriority(), item.isCompleted());
            outputBoundary.presentEditToDoItem(new EditToDoItemOutputData(true, "Edit view opened.", item.getTitle()));
        } else {
            outputBoundary.presentEditToDoItem(new EditToDoItemOutputData(false, "Item not found.", title));
        }
    }
}
