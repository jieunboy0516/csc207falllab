package interface_adapter.HighestPriority;

import entity.ToDoItem;

import java.util.Optional;

public class HighestPriorityTaskPresenter {
    private final HighestPriorityTaskViewModel viewModel;

    public HighestPriorityTaskPresenter(HighestPriorityTaskViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void presentHighestPriorityTask(Optional<ToDoItem> task) {
        if (task.isPresent()) {
            ToDoItem highestTask = task.get();
            viewModel.setTaskTitle(highestTask.getTitle());
            viewModel.setTaskDueDate(highestTask.getDueDate().toString());
            viewModel.setMessage("Top priority task found!");
        } else {
            viewModel.setMessage("No incomplete tasks available.");
        }
    }
}
