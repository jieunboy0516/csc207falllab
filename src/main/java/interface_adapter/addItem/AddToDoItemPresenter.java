package interface_adapter.addItem;

import entity.ToDoItem;
import interface_adapter.ViewManagerModel;
import interface_adapter.addItem.AddToDoItemViewModel;
import use_case.AddItem.AddToDoItemOutputBoundary;
import use_case.AddItem.AddToDoItemOutputData;
import view.ToDoListView;
import view.ViewManager;

/**
 * The Presenter for the AddToDoItem Use Case.
 */
public class AddToDoItemPresenter implements AddToDoItemOutputBoundary {

    private final AddToDoItemViewModel addToDoItemViewModel;
    private final ViewManagerModel viewManagerModel;
    private final ToDoListView toDoListView;


    public AddToDoItemPresenter(ViewManagerModel viewManagerModel, AddToDoItemViewModel addToDoItemViewModel, ToDoListView toDoListView) {
        this.viewManagerModel = viewManagerModel;
        this.addToDoItemViewModel = addToDoItemViewModel;
        this.toDoListView = toDoListView;
    }

    @Override
    public void presentAddToDoItem(AddToDoItemOutputData outputData) {
        // Retrieve details from the ToDoItem contained within outputData
        ToDoItem toDoItem = outputData.getToDoItem();

        addToDoItemViewModel.addToDoItem(
                toDoItem.getTitle(),
                toDoItem.getDescription(),
                toDoItem.getPriority()
        );
        addToDoItemViewModel.firePropertyChanged();

        toDoListView.updateToDoListDisplay();

        // Optionally, switch to a specific view if needed
//        viewManagerModel.setState(addToDoItemViewModel.getViewName());
//        viewManagerModel.firePropertyChanged();
    }

}
