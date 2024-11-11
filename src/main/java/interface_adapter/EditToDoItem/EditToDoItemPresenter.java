package interface_adapter.EditToDoItem;

import entity.ToDoItem;
import use_case.EditToDoItem.EditToDoItemOutputBoundary;
import use_case.EditToDoItem.EditToDoItemOutputData;

public class EditToDoItemPresenter implements EditToDoItemOutputBoundary {

    private final EditToDoItemViewModel viewModel;

    public EditToDoItemPresenter(EditToDoItemViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentEditToDoItem(EditToDoItemOutputData outputData) {
        if (outputData.isSuccess()) {
            viewModel.loadItem(outputData.getTitle(), outputData.getTitle(), "", null, 0, false); // Load only title
        } else {
            viewModel.setErrorMessage(outputData.getMessage());
        }
        viewModel.firePropertyChanged("editToDoItemView");
    }

    @Override
    public void presentUpdatedItem(ToDoItem updatedItem) {
        viewModel.updateItemDetails(
                updatedItem.getTitle(),
                updatedItem.getDescription(),
                updatedItem.getDueDate(),
                updatedItem.getPriority()
        );
        viewModel.firePropertyChanged("toDoListUpdated");
    }
}
