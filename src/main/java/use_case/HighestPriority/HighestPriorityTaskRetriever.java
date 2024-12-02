package use_case.HighestPriority;

import entity.ToDoItem;

import java.util.List;
import java.util.Optional;

public class HighestPriorityTaskRetriever {

    /**
     * Retrieves the highest-priority task that is due the earliest and not completed.
     *
     * @param tasks List of ToDoItem objects
     * @return An Optional containing the highest-priority ToDoItem, or an empty Optional if none exist
     */
    public Optional<ToDoItem> getHighestPriorityTask(List<ToDoItem> tasks) {
        return tasks.stream()
                .filter(task -> !task.isCompleted()) // Only include incomplete tasks
                .sorted((task1, task2) -> {
                    // Compare by priority (lower number = higher priority)
                    int priorityComparison = comparePriority(task1.getPriority(), task2.getPriority());
                    if (priorityComparison != 0) {
                        return priorityComparison;
                    }
                    // If priority is the same, compare by due date
                    return task1.getDueDate().compareTo(task2.getDueDate());
                })
                .findFirst(); // Return the first task after sorting
    }

    private int comparePriority(int priority1, int priority2) {
        // Lower numbers indicate higher priority
        return Integer.compare(priority1, priority2);
    }


    /**
     * Compares two priority levels.
     *
     * @param priority1 The first priority as a String
     * @param priority2 The second priority as a String
     * @return An integer representing the comparison result
     */
    private int comparePriority(String priority1, String priority2) {
        List<String> priorityOrder = List.of("high", "medium", "low");
        return Integer.compare(priorityOrder.indexOf(priority1), priorityOrder.indexOf(priority2));
    }
}
