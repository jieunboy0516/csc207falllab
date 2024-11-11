package use_case.DeleteItem;

import data_access.InMemoryToDoDataAccessObject;
import entity.ToDoItemFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.AddItem.ToDoItemDataAccessInterface;
import entity.ToDoItem;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DeleteToDoItemInteractorTest {

    private ToDoItemDataAccessInterface toDoRepository;
    private ToDoItemFactory toDoItemFactory;

    @BeforeEach
    void setUp() {
        // Set up the in-memory repository and a factory for creating ToDo items
        toDoRepository = new InMemoryToDoDataAccessObject();
        toDoItemFactory = new ToDoItemFactory();
    }

    @Test
    void deleteExistingItemTest() {
        // Arrange: Create and save a to-do item in the repository
        ToDoItem item = toDoItemFactory.create("Grocery Shopping", "Buy groceries", LocalDate.now(), 2);
        toDoRepository.save(item);

        // Set up the interactor with only the repository (no output boundary)
        DeleteToDoItemInteractor interactor = new DeleteToDoItemInteractor(toDoRepository);

        // Act: Attempt to delete the item by title
        DeleteToDoItemInputData inputData = new DeleteToDoItemInputData("Grocery Shopping");

        // Assert: Verify that the deletion was successful and the item no longer exists in the repository
        assertTrue(outputData.isSuccess());
        assertEquals("Item deleted successfully.", outputData.getMessage());
        assertFalse(toDoRepository.existsByTitle("Grocery Shopping"));
    }

    @Test
    void deleteNonExistentItemTest() {
        // Set up the interactor with only the repository (no output boundary)
        DeleteToDoItemInteractor interactor = new DeleteToDoItemInteractor(toDoRepository);

        // Act: Attempt to delete a non-existent item by title
        DeleteToDoItemInputData inputData = new DeleteToDoItemInputData("Nonexistent Item");
        DeleteToDoItemOutputData outputData = interactor.execute(inputData);

        // Assert: Verify that the deletion was unsuccessful and returned the correct message
        assertFalse(outputData.isSuccess());
        assertEquals("Item not found.", outputData.getMessage());
    }
}
