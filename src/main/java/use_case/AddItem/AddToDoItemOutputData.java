package use_case.AddItem;

import entity.ToDoItem;

public class AddToDoItemOutputData {
    private final ToDoItem toDoItem;

    public AddToDoItemOutputData(ToDoItem toDoItem) {
        this.toDoItem = toDoItem;
    }

    public ToDoItem getToDoItem() {
        return toDoItem;
    }
}
