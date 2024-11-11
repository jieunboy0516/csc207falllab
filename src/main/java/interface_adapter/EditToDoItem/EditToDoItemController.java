package interface_adapter.EditToDoItem;

import data_access.InMemoryToDoDataAccessObject;
import entity.ToDoItem;
import use_case.EditToDoItem.EditToDoItemInputBoundary;
import use_case.EditToDoItem.EditToDoItemInputData;
import view.EditToDoItemView;
import view.ToDoListView;

import javax.swing.*;
import java.time.LocalDate;

public class EditToDoItemController {
    private final EditToDoItemInputBoundary interactor;
    private String originalTitle; // Store the original title
    private EditToDoItemView editView; // Reference to the edit view
    private InMemoryToDoDataAccessObject toDoDataAccess ;
    private ToDoListView toDoListView;

    public EditToDoItemController(EditToDoItemInputBoundary interactor, InMemoryToDoDataAccessObject toDoDataAccess, ToDoListView toDoListView) {
        this.interactor = interactor;
        this.toDoDataAccess = toDoDataAccess;
        this.toDoListView = toDoListView;
    }

    // Method to open the edit view in a new window with the original title
    public void openEditView(String title) {
        this.originalTitle = title; // Save the original title

        // Initialize the edit view in a new JFrame
        JFrame frame = new JFrame("Edit To-Do Item");
        editView = new EditToDoItemView(new EditToDoItemViewModel("Edit Item view"));
        editView.setController(this);
        ToDoItem item = toDoDataAccess.get(title);
        editView.populateFields(
                item.getTitle(),
                item.getDescription(),
                item.getDueDate(),
                item.getPriority(),
                item.isCompleted()
        );


        frame.setContentPane(editView);
        frame.pack();
        frame.setVisible(true);
    }

    // Execute edit with the stored original title and updated item details
    public void executeEdit(String newTitle, String description, LocalDate dueDate, int priority, boolean isCompleted) {
        EditToDoItemInputData inputData = new EditToDoItemInputData(
                originalTitle, newTitle, description, dueDate, priority, isCompleted
        );
        interactor.execute(inputData);

        toDoListView.updateToDoListDisplay();


    }
}
