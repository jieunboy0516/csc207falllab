package use_case.HighestPriority;

import entity.ToDoItem;

public class HighestPriorityOutputData {
    private final ToDoItem highestPriorityTask;
    private final String message;

    public HighestPriorityOutputData(ToDoItem highestPriorityTask, String message) {
        this.highestPriorityTask = highestPriorityTask;
        this.message = message;
    }

    public ToDoItem getHighestPriorityTask() {
        return highestPriorityTask;
    }

    public String getMessage() {
        return message;
    }
}
