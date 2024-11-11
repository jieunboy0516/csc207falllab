package use_case.EditToDoItem;

import data_access.InMemoryToDoDataAccessObject;
import entity.ToDoItemFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.AddItem.ToDoItemDataAccessInterface;
import entity.ToDoItem;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EditToDoItemInteractorTest {

    private ToDoItemDataAccessInterface toDoRepository;
    private ToDoItemFactory toDoItemFactory;

    @BeforeEach
    void setUp() {
        toDoRepository = new InMemoryToDoDataAccessObject();
        toDoItemFactory = new ToDoItemFactory();
    }

    @Test
    void editExistingItemTest() {
        // Arrange: Save an initial to-do item in the repository
        toDoRepository.save(toDoItemFactory.create("Grocery Shopping", "Buy groceries", LocalDate.now(), 2));

        // Set up a success presenter to verify the output
        EditToDoItemOutputBoundary successPresenter = new EditToDoItemOutputBoundary() {
            @Override
            public void presentEditToDoItem(EditToDoItemOutputData outputData) {
                assertTrue(outputData.isSuccess());
                assertEquals("Item updated successfully.", outputData.getMessage());
                assertEquals("Weekly Grocery Shopping", outputData.getTitle());
            }

            @Override
            public void presentUpdatedItem(ToDoItem item) {
                // Additional assertions if needed for the updated item
                assertEquals("Weekly Grocery Shopping", item.getTitle());
                assertEquals("Buy groceries for the week", item.getDescription());
            }
        };

        // Act: Edit the item with a new title and updated details
        EditToDoItemInputData inputData = new EditToDoItemInputData(
                "Grocery Shopping", "Weekly Grocery Shopping", "Buy groceries for the week", LocalDate.now().plusDays(2), 1, false);
        EditToDoItemInputBoundary interactor = new EditToDoItemInteractor(toDoRepository, successPresenter);
        interactor.execute(inputData);

        // Assert: Verify that the item was updated in the repository
        assertTrue(toDoRepository.existsByTitle("Weekly Grocery Shopping"));
        assertFalse(toDoRepository.existsByTitle("Grocery Shopping"));
        assertEquals("Weekly Grocery Shopping", toDoRepository.get("Weekly Grocery Shopping").getTitle());
    }

    @Test
    void editNonExistentItemTest() {
        // Set up a failure presenter to verify the failure message
        EditToDoItemOutputBoundary failurePresenter = new EditToDoItemOutputBoundary() {
            @Override
            public void presentEditToDoItem(EditToDoItemOutputData outputData) {
                assertFalse(outputData.isSuccess());
                assertEquals("Item not found.", outputData.getMessage());
                assertEquals("Nonexistent Item", outputData.getTitle());
            }

            @Override
            public void presentUpdatedItem(ToDoItem item) {
                fail("Expected failure due to non-existent item, but got an update instead.");
            }
        };

        // Attempt to edit an item that does not exist
        EditToDoItemInputData inputData = new EditToDoItemInputData(
                "Nonexistent Item", "Updated Title", "Updated Description", LocalDate.now().plusDays(3), 1, false);
        EditToDoItemInputBoundary interactor = new EditToDoItemInteractor(toDoRepository, failurePresenter);
        interactor.execute(inputData);
    }

    @Test
    void openEditViewForExistingItemTest() {
        // Arrange: Save an item in the repository
        toDoRepository.save(toDoItemFactory.create("Grocery Shopping", "Buy groceries", LocalDate.now(), 2));

        // Set up a presenter to verify the success of opening the edit view
        EditToDoItemOutputBoundary presenter = new EditToDoItemOutputBoundary() {
            @Override
            public void presentEditToDoItem(EditToDoItemOutputData outputData) {
                assertTrue(outputData.isSuccess());
                assertEquals("Edit view opened.", outputData.getMessage());
                assertEquals("Grocery Shopping", outputData.getTitle());
            }

            @Override
            public void presentUpdatedItem(ToDoItem item) {
                // Not needed for this test, but must be implemented
            }
        };

        // Act: Attempt to open the edit view for the existing item
        EditToDoItemInteractor interactor = new EditToDoItemInteractor(toDoRepository, presenter);
        interactor.openEditView("Grocery Shopping");
    }

    @Test
    void openEditViewForNonExistentItemTest() {
        // Set up a presenter to verify the failure message when the item is not found
        EditToDoItemOutputBoundary presenter = new EditToDoItemOutputBoundary() {
            @Override
            public void presentEditToDoItem(EditToDoItemOutputData outputData) {
                assertFalse(outputData.isSuccess());
                assertEquals("Item not found.", outputData.getMessage());
                assertEquals("Nonexistent Item", outputData.getTitle());
            }

            @Override
            public void presentUpdatedItem(ToDoItem item) {
                fail("Expected failure due to non-existent item, but got an update instead.");
            }
        };

        // Act: Attempt to open the edit view for a non-existent item
        EditToDoItemInteractor interactor = new EditToDoItemInteractor(toDoRepository, presenter);
        interactor.openEditView("Nonexistent Item");
    }
}
