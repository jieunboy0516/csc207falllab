package use_case.schedule;

import entity.ToDoItem;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GetTasksForWeekInteractorTest {

    @Test
    void testGetTasksForCurrentWeek_withValidTasks() {
        // Arrange
        List<ToDoItem> allTasks = new ArrayList<>();
        LocalDate startOfWeek = LocalDate.of(2024, 12, 1);

        // Add tasks with varying due dates
        allTasks.add(new ToDoItem("Task 1", "Description", startOfWeek, 1)); // Monday
        allTasks.add(new ToDoItem("Task 2", "Description", startOfWeek.plusDays(3), 2)); // Thursday
        allTasks.add(new ToDoItem("Task 3", "Description", startOfWeek.plusDays(6), 3)); // Sunday

        GetTasksForWeekInteractor interactor = new GetTasksForWeekInteractor();

        // Act
        List<List<ToDoItem>> tasksByDay = interactor.getTasksForCurrentWeek(allTasks, startOfWeek);

        // Assert
        assertEquals(7, tasksByDay.size()); // One list for each day
        assertEquals(1, tasksByDay.get(0).size()); // Monday
        assertEquals(1, tasksByDay.get(3).size()); // Thursday
        assertEquals(1, tasksByDay.get(6).size()); // Sunday
        assertEquals(0, tasksByDay.get(1).size()); // Tuesday
    }

    @Test
    void testGetTasksForCurrentWeek_withNoTasks() {
        // Arrange
        List<ToDoItem> allTasks = new ArrayList<>();
        LocalDate startOfWeek = LocalDate.of(2024, 12, 1);

        GetTasksForWeekInteractor interactor = new GetTasksForWeekInteractor();

        // Act
        List<List<ToDoItem>> tasksByDay = interactor.getTasksForCurrentWeek(allTasks, startOfWeek);

        // Assert
        assertEquals(7, tasksByDay.size());
        for (List<ToDoItem> dayTasks : tasksByDay) {
            assertTrue(dayTasks.isEmpty());
        }
    }

    @Test
    void testGetTasksForCurrentWeek_withTasksOutsideWeek() {
        // Arrange
        List<ToDoItem> allTasks = new ArrayList<>();
        LocalDate startOfWeek = LocalDate.of(2024, 12, 1);

        // Tasks outside the week
        allTasks.add(new ToDoItem("Task 1", "Description", startOfWeek.minusDays(1), 1)); // Before week
        allTasks.add(new ToDoItem("Task 2", "Description", startOfWeek.plusDays(7), 2)); // After week

        GetTasksForWeekInteractor interactor = new GetTasksForWeekInteractor();

        // Act
        List<List<ToDoItem>> tasksByDay = interactor.getTasksForCurrentWeek(allTasks, startOfWeek);

        // Assert
        assertEquals(7, tasksByDay.size());
        for (List<ToDoItem> dayTasks : tasksByDay) {
            assertTrue(dayTasks.isEmpty());
        }
    }
}