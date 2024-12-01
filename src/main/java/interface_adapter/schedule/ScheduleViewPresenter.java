package interface_adapter.schedule;

import entity.ToDoItem;
import java.util.List;

/**
 * Presenter to format weekly tasks for display in the view.
 */
public class ScheduleViewPresenter {

    /**
     * Formats tasks grouped by day for display.
     *
     * @param tasksByDay A list of 7 lists, each containing tasks for a specific day of the week (Sunday to Saturday).
     * @return A formatted string displaying tasks for each day of the week.
     */
    public String formatTasksForDisplay(List<List<ToDoItem>> tasksByDay) {
        StringBuilder output = new StringBuilder();
        String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

        for (int i = 0; i < days.length; i++) {
            output.append(days[i]).append(":\n");
            for (ToDoItem task : tasksByDay.get(i)) {
                output.append("- ")
                        .append(task.getTitle())
                        .append(" (Due: ").append(task.getDueDate()).append(")\n");
            }
            output.append("\n");
        }

        return output.toString();
    }
}
