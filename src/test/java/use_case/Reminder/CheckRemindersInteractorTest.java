package use_case.Reminder;

import data_access.InMemoryToDoDataAccessObject;
import entity.ToDoItem;
import entity.ToDoItemFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.reminder.CheckRemindersInteractor;
import use_case.reminder.CheckRemindersOutputBoundary;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CheckRemindersInteractorTest {
    private InMemoryToDoDataAccessObject dataAccess;
    private ToDoItemFactory toDoItemFactory;
    private CheckRemindersOutputBoundary outputBoundary;

    @BeforeEach
    void setUp() {
        dataAccess = new InMemoryToDoDataAccessObject();
        toDoItemFactory = new ToDoItemFactory();

        // Mock the output boundary to validate reminders
        outputBoundary = reminders -> {
            assertNotNull(reminders); // Ensure reminders are not null
        };
    }

    @Test
    void upcomingTasksWithDaysLeftAreSorted() {
        // Arrange: Add tasks with different due dates
        ToDoItem item1 = toDoItemFactory.create("Task A", "Description A", LocalDate.now().plusDays(3), 1);
        ToDoItem item2 = toDoItemFactory.create("Task B", "Description B", LocalDate.now().plusDays(1), 2);
        ToDoItem item3 = toDoItemFactory.create("Task C", "Description C", LocalDate.now().plusDays(5), 3);
        ToDoItem item4 = toDoItemFactory.create("Completed Task", "Description D", LocalDate.now().plusDays(2), 4);
        item4.setCompleted(true); // Mark as completed

        dataAccess.save(item1);
        dataAccess.save(item2);
        dataAccess.save(item3);
        dataAccess.save(item4);

        // Act: Call the interactor
        CheckRemindersInteractor interactor = new CheckRemindersInteractor(dataAccess, outputBoundary);
        interactor.checkReminders();

        // Assert: Verify reminders are sorted and include days left
        List<String> reminders = dataAccess.getUpcomingReminders(LocalDate.now());
        assertEquals(3, reminders.size()); // Only 3 tasks since 1 is completed
        assertTrue(reminders.get(0).contains("Task B")); // Closest task
        assertTrue(reminders.get(1).contains("Task A")); // Next closest
        assertTrue(reminders.get(2).contains("Task C")); // Furthest task
    }

    @Test
    void noRemindersIfAllTasksCompleted() {
        // Arrange: Add only completed tasks
        ToDoItem completedItem = toDoItemFactory.create("Completed Task", "Description", LocalDate.now().plusDays(1), 1);
        completedItem.setCompleted(true);
        dataAccess.save(completedItem);

        // Act: Call the interactor
        CheckRemindersInteractor interactor = new CheckRemindersInteractor(dataAccess, outputBoundary);
        interactor.checkReminders();

        // Assert: Verify no reminders
        List<String> reminders = dataAccess.getUpcomingReminders(LocalDate.now());
        assertTrue(reminders.isEmpty(), "Expected no reminders, but some were returned.");
    }

    @Test
    void upcomingTasksShowPriorityAndDaysLeft() {
        // Arrange: Add tasks with different due dates and priorities
        ToDoItem item1 = toDoItemFactory.create("Task A", "Description A", LocalDate.now().plusDays(3), 1);
        ToDoItem item2 = toDoItemFactory.create("Task B", "Description B", LocalDate.now().plusDays(1), 2);
        ToDoItem item3 = toDoItemFactory.create("Task C", "Description C", LocalDate.now().plusDays(5), 3);

        dataAccess.save(item1);
        dataAccess.save(item2);
        dataAccess.save(item3);

        // Act: Call the interactor
        CheckRemindersInteractor interactor = new CheckRemindersInteractor(dataAccess, outputBoundary);
        interactor.checkReminders();

        // Assert: Verify reminders contain priority and days left
        List<String> reminders = dataAccess.getUpcomingReminders(LocalDate.now());
        assertEquals(3, reminders.size()); // Verify 3 tasks

        // Print out the reminders for debugging
        reminders.forEach(System.out::println);

        assertTrue(reminders.get(0).contains("Priority: Medium")); // Task B should have medium priority
        assertTrue(reminders.get(1).contains("Priority: High")); // Task A should have high priority
        assertTrue(reminders.get(2).contains("Priority: Low")); // Task C should have low priority
    }
}
