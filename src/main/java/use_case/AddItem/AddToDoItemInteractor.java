package use_case.AddItem;

import entity.ToDoItemFactory;
import entity.TodoItem

public class AddToDoItemInteractor implements AddToDoItemInputBoundary {

    private final AddToDoItemOutputBoundary outputBoundary;
    private final ToDoItemDataAccessInterface dataAccess;
    private final ToDoItemFactory toDoItemFactory;

    public AddToDoItemInteractor(AddToDoItemOutputBoundary outputBoundary,
                                 ToDoItemDataAccessInterface dataAccess,
                                 ToDoItemFactory toDoItemFactory) {
        this.outputBoundary = outputBoundary;
        this.dataAccess = dataAccess;
        this.toDoItemFactory = toDoItemFactory;
    }

    @Override
    public void addToDoItem(AddToDoItemInputData inputData) {
        var toDoItem = toDoItemFactory.create(inputData.getTitle(), inputData.getDescription(),
                inputData.getDueDate(), inputData.getPriority());
        dataAccess.save(toDoItem);
        outputBoundary.presentAddToDoItem(new AddToDoItemOutputData(toDoItem));
    }
}
