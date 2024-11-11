package interface_adapter.addItem;

import interface_adapter.ViewModel;

public class AddToDoItemViewModel extends ViewModel<AddToDoItemState> {

    public AddToDoItemViewModel(String viewName) {
        super(viewName);
    }

    public void addToDoItem(String title, String description, int priority) {
        AddToDoItemState state = new AddToDoItemState();
        state.setTitle(title);
        state.setDescription(description);
        state.setPriority(priority);
        setState(state);
        firePropertyChanged("state");
    }

    public void markItemAsCompleted() {
        AddToDoItemState state = getState();
        if (state != null) {
            state.setCompleted(true);
            firePropertyChanged("completed");
        }
    }
}
