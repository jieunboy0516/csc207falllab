package use_case.schedule;

import entity.ToDoItem;
import interface_adapter.schedule.ScheduleViewPresenter;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for ScheduleViewPresenter.
 */
public class ScheduleViewPresenterTest {

    @Test
    public void testFormatTasksForDisplay() {
        // Arrange
        ScheduleViewPresenter presenter = new ScheduleViewPresenter();

        // Create sample tasks
        ToDoItem task1 = new ToDoItem("Task 1", "Description 1", LocalDate.of(2024, 12, 4), 1); // Wednesday
        ToDoItem task2 = new ToDoItem("Task 2", "Description 2", LocalDate.of(2024, 12, 6), 2); // Friday
        ToDoItem task3 = new ToDoItem("Task 3", "Description 3", LocalDate.of(2024, 12, 6), 3); // Friday

        // Prepare input: tasks grouped by days
        List<List<ToDoItem>> tasksByDay = Arrays.asList(
                new ArrayList<>(), // Monday
                new ArrayList<>(), // Tuesday
                Arrays.asList(task1), // Wednesday
                new ArrayList<>(), // Thursday
                Arrays.asList(task2, task3), // Friday
                new ArrayList<>(), // Saturday
                new ArrayList<>()  // Sunday
        );

        // Expected output
        String expectedOutput = """
                Monday:
                  No tasks for this day.

                Tuesday:
                  No tasks for this day.

                Wednesday:
                  - Task 1 (Priority: 1)

                Thursday:
                  No tasks for this day.

                Friday:
                  - Task 2 (Priority: 2)
                  - Task 3 (Priority: 3)

                Saturday:
                  No tasks for this day.

                Sunday:
                  No tasks for this day.
                """.trim();

        // Act
        String result = presenter.formatTasksForDisplay(tasksByDay);

        // Assert
        assertEquals(expectedOutput, result);
    }
}
