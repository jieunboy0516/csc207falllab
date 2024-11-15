package use_case.AddItem;

import data_access.InMemoryToDoDataAccessObject;
import entity.ToDoItemFactory;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AddToDoItemInteractorTest {

    @Test
    void successTest() {
        AddToDoItemInputData inputData = new AddToDoItemInputData("Grocery Shopping", "Buy groceries for the week", LocalDate.now(), 2);
        ToDoItemDataAccessInterface toDoRepository = new InMemoryToDoDataAccessObject();

        // Create a successPresenter that verifies the output data and repository state
        AddToDoItemOutputBoundary successPresenter = new AddToDoItemOutputBoundary() {
            @Override
            public void presentAddToDoItem(AddToDoItemOutputData outputData) {
                // Assertions to confirm the interactor is working as expected
                assertNotNull(outputData);
                assertEquals("Grocery Shopping", outputData.getToDoItem().getTitle());
                assertTrue(toDoRepository.existsByTitle("Grocery Shopping"));
            }
        };

        // Instantiate the interactor and execute the add item use case
        AddToDoItemInputBoundary interactor = new AddToDoItemInteractor(successPresenter, toDoRepository,  new ToDoItemFactory());
        interactor.execute(inputData);

        // Final check to ensure the item was saved in the repository
        assertNotNull(toDoRepository.get("Grocery Shopping"));
        assertEquals("Grocery Shopping", toDoRepository.get("Grocery Shopping").getTitle());
    }

    @Test
    void failureDuplicateItemTest() {
        AddToDoItemInputData inputData = new AddToDoItemInputData("Grocery Shopping",
                "Buy groceries", LocalDate.now(), 2);
        ToDoItemDataAccessInterface toDoRepository = new InMemoryToDoDataAccessObject();
        toDoRepository.save(new ToDoItemFactory().create("Grocery Shopping",
                "Buy groceries", LocalDate.now(), 2));

        AddToDoItemOutputBoundary presenter = new AddToDoItemOutputBoundary() {
            @Override
            public void presentAddToDoItem(AddToDoItemOutputData outputData) {
                // Verify that the output matches the existing duplicate item
                assertNotNull(outputData);
                assertNotNull(outputData.getToDoItem());
                assertEquals("Grocery Shopping", outputData.getToDoItem().getTitle());
                assertEquals("Buy groceries", outputData.getToDoItem().getDescription());
            }
        };

        AddToDoItemInputBoundary interactor = new AddToDoItemInteractor(presenter, toDoRepository, new ToDoItemFactory());

        interactor.execute(inputData);

        assertEquals(1, toDoRepository.getAllToDoItems().size());
        assertEquals("Grocery Shopping", toDoRepository.get("Grocery Shopping").getTitle());
    }
}
