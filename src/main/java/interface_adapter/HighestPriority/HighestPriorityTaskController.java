package interface_adapter.HighestPriority;

import use_case.HighestPriority.HighestPriorityTaskRetriever;
import entity.ToDoItem;

import java.util.List;

public class HighestPriorityTaskController {
    private final HighestPriorityTaskRetriever retriever;
    private final HighestPriorityTaskPresenter presenter;

    public HighestPriorityTaskController(HighestPriorityTaskRetriever retriever, HighestPriorityTaskPresenter presenter) {
        this.retriever = retriever;
        this.presenter = presenter;
    }

    public void findAndDisplayHighestPriorityTask(List<ToDoItem> tasks) {
        var task = retriever.getHighestPriorityTask(tasks);
        presenter.presentHighestPriorityTask(task);
    }
}
