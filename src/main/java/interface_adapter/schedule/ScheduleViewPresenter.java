package interface_adapter.schedule;

import entity.ToDoItem;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Presenter class to format tasks for the schedule view.
 */
public class ScheduleViewPresenter {

    /**
     * Formats a list of tasks grouped by days into a user-readable format.
     *
     * @param tasksByDay A list of lists where each sublist contains tasks for a particular day of the week.
     * @return A string representing the formatted tasks for display.
     */
    public String formatTasksForDisplay(List<List<ToDoItem>> tasksByDay) {
        StringBuilder formattedOutput = new StringBuilder();

        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        for (int i = 0; i < tasksByDay.size(); i++) {
            formattedOutput.append(days[i]).append(":\n");

            List<ToDoItem> tasks = tasksByDay.get(i);
            if (tasks.isEmpty()) {
                formattedOutput.append("  No tasks for this day.\n");
            } else {
                for (ToDoItem task : tasks) {
                    formattedOutput.append("  - ").append(task.getTitle())
                            .append(" (Priority: ").append(task.getPriority()).append(")\n");
                }
            }
            formattedOutput.append("\n");
        }

        return formattedOutput.toString().trim();
    }
}
