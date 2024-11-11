package interface_adapter.addItem;

import use_case.AddItem.AddToDoItemInputBoundary;
import use_case.AddItem.AddToDoItemInputData;

import java.time.LocalDate;

public class AddToDoItemController {

    private final AddToDoItemInputBoundary interactor;

    public AddToDoItemController(AddToDoItemInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void addToDoItem(String title, String description, LocalDate dueDate, int priority) {
        AddToDoItemInputData inputData = new AddToDoItemInputData(title, description, dueDate, priority);
        interactor.execute(inputData);
    }
}
