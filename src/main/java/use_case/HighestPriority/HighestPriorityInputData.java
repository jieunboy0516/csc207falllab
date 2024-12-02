package use_case.HighestPriority;

import java.util.List;
import entity.ToDoItem;

public class HighestPriorityInputData {
    private final List<ToDoItem> tasks;

    public HighestPriorityInputData(List<ToDoItem> tasks) {
        this.tasks = tasks;
    }

    public List<ToDoItem> getTasks() {
        return tasks;
    }
}
