package use_case.HighestPriority;

import entity.ToDoItem;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class HighestPriorityInteractorTest {

    @Test
    public void testGetHighestPriorityTask_AllIncomplete() {
        // Arrange
        ToDoItem task1 = new ToDoItem("Task 1", "Description 1", LocalDate.of(2024, 12, 1), 1);
        ToDoItem task2 = new ToDoItem("Task 2", "Description 2", LocalDate.of(2024, 11, 30), 2);
        ToDoItem task3 = new ToDoItem("Task 3", "Description 3", LocalDate.of(2024, 11, 29), 3);
        HighestPriorityTaskRetriever retriever = new HighestPriorityTaskRetriever();

        // Act
        Optional<ToDoItem> result = retriever.getHighestPriorityTask(Arrays.asList(task1, task2, task3));

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Task 1", result.get().getTitle());
        assertEquals(1, result.get().getPriority());
    }

    @Test
    public void testGetHighestPriorityTask_SamePriorityDifferentDueDates() {
        // Arrange
        ToDoItem task1 = new ToDoItem("Task 1", "Description 1", LocalDate.of(2024, 12, 1), 1);
        ToDoItem task2 = new ToDoItem("Task 2", "Description 2", LocalDate.of(2024, 11, 30), 1); // Same priority, earlier due date
        ToDoItem task3 = new ToDoItem("Task 3", "Description 3", LocalDate.of(2024, 11, 29), 1); // Same priority, even earlier due date
        HighestPriorityTaskRetriever retriever = new HighestPriorityTaskRetriever();

        // Act
        Optional<ToDoItem> result = retriever.getHighestPriorityTask(Arrays.asList(task1, task2, task3));

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Task 3", result.get().getTitle());
        assertEquals(LocalDate.of(2024, 11, 29), result.get().getDueDate());
    }

    @Test
    public void testGetHighestPriorityTask_AllCompleted() {
        // Arrange
        ToDoItem task1 = new ToDoItem("Task 1", "Description 1", LocalDate.of(2024, 12, 1), 1);
        task1.setCompleted(true); // Mark as completed
        ToDoItem task2 = new ToDoItem("Task 2", "Description 2", LocalDate.of(2024, 11, 30), 2);
        task2.setCompleted(true); // Mark as completed
        ToDoItem task3 = new ToDoItem("Task 3", "Description 3", LocalDate.of(2024, 11, 29), 3);
        task3.setCompleted(true); // Mark as completed
        HighestPriorityTaskRetriever retriever = new HighestPriorityTaskRetriever();

        // Act
        Optional<ToDoItem> result = retriever.getHighestPriorityTask(Arrays.asList(task1, task2, task3));

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    public void testGetHighestPriorityTask_MixedCompletedAndIncomplete() {
        // Arrange
        ToDoItem task1 = new ToDoItem("Task 1", "Description 1", LocalDate.of(2024, 12, 1), 1);
        ToDoItem task2 = new ToDoItem("Task 2", "Description 2", LocalDate.of(2024, 11, 30), 1);
        task2.setCompleted(true); // Mark as completed
        ToDoItem task3 = new ToDoItem("Task 3", "Description 3", LocalDate.of(2024, 11, 29), 2);
        HighestPriorityTaskRetriever retriever = new HighestPriorityTaskRetriever();

        // Act
        Optional<ToDoItem> result = retriever.getHighestPriorityTask(Arrays.asList(task1, task2, task3));

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Task 1", result.get().getTitle());
    }

    @Test
    public void testGetHighestPriorityTask_EmptyList() {
        // Arrange
        HighestPriorityTaskRetriever retriever = new HighestPriorityTaskRetriever();

        // Act
        Optional<ToDoItem> result = retriever.getHighestPriorityTask(Arrays.asList());

        // Assert
        assertFalse(result.isPresent());
    }
}
