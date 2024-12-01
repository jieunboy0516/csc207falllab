package use_case.schedule;

import entity.ToDoItem;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Interactor to retrieve tasks for the current week.
 */
public class GetTasksForWeekInteractor {
    public List<List<ToDoItem>> getTasksForCurrentWeek(List<ToDoItem> allTasks, LocalDate weekStart) {
        // Initialize a list to hold tasks for each day of the week
        List<List<ToDoItem>> tasksByDay = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            tasksByDay.add(new ArrayList<>());
        }

        // Add tasks to the appropriate day
        for (ToDoItem task : allTasks) {
            LocalDate dueDate = task.getDueDate();
            if (!dueDate.isBefore(weekStart) && !dueDate.isAfter(weekStart.plusDays(6))) {
                int dayIndex = (int) java.time.temporal.ChronoUnit.DAYS.between(weekStart, dueDate);
                tasksByDay.get(dayIndex).add(task);
            }
        }

        return tasksByDay;
    }

}
