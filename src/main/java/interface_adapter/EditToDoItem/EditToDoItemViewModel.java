package interface_adapter.EditToDoItem;

import interface_adapter.ViewModel;

import java.time.LocalDate;

public class EditToDoItemViewModel extends ViewModel<EditToDoItemState> {

    private EditToDoItemController editToDoItemController;

    public EditToDoItemViewModel(String viewName) {
        super(viewName);
    }

    /**
     * Loads an item for editing with its current details, including the original title.
     *
     * @param originalTitle The original title of the item to be edited (used for identification)
     * @param newTitle      The new title of the item
     * @param description   The description of the item
     * @param dueDate       The due date of the item
     * @param priority      The priority level of the item
     * @param isCompleted   The completion status of the item
     */
    public void loadItem(String originalTitle, String newTitle, String description, LocalDate dueDate, int priority, boolean isCompleted) {
        EditToDoItemState state = new EditToDoItemState();
        state.setOriginalTitle(originalTitle);
        state.setNewTitle(newTitle);
        state.setDescription(description);
        state.setDueDate(dueDate);
        state.setPriority(priority);
        state.setCompleted(isCompleted);
        setState(state);
        firePropertyChanged("state");
    }

    /**
     * Updates the completion status of the item.
     *
     * @param completed The new completion status of the item
     */
    public void setCompleted(boolean completed) {
        EditToDoItemState state = getState();
        if (state != null) {
            state.setCompleted(completed);
            firePropertyChanged("completed");
        }
    }

    /**
     * Updates the details of the item in the current state.
     *
     * @param newTitle    The new title of the item
     * @param description The updated description of the item
     * @param dueDate     The updated due date of the item
     * @param priority    The updated priority level of the item
     */
    public void updateItemDetails(String newTitle, String description, LocalDate dueDate, int priority) {
        EditToDoItemState state = getState();
        if (state != null) {
            state.setNewTitle(newTitle);
            state.setDescription(description);
            state.setDueDate(dueDate);
            state.setPriority(priority);
            firePropertyChanged("state");
        }
    }

    /**
     * Sets an error message in the state.
     *
     * @param errorMessage The error message to be displayed
     */
    public void setErrorMessage(String errorMessage) {
        EditToDoItemState state = getState();
        if (state != null) {
            state.setErrorMessage(errorMessage);
            firePropertyChanged("errorMessage");
        }
    }

    /**
     * Sets the controller responsible for handling edit actions.
     *
     * @param editToDoItemController The controller to handle edit operations
     */
    public void setEditToDoItemController(EditToDoItemController editToDoItemController) {
        this.editToDoItemController = editToDoItemController;
    }


    /**
     * Retrieves the controller responsible for handling edit actions.
     *
     * @return The EditToDoItemController instance
     */
    public EditToDoItemController getController() {
        return this.editToDoItemController;
    }


}
