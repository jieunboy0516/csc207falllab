package use_case.AddItem;

import data_access.InMemoryToDoDataAccessObject;
import entity.ToDoItemFactory;
import entity.ToDoItem;
import org.junit.Test;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AddToDoItemInteractorTest {

    @Test
    void successTest() {
        AddToDoItemInputData inputData = new AddToDoItemInputData("Grocery Shopping", "Buy groceries for the week", LocalDate.now(), 2);
        ToDoItemDataAccessInterface toDoRepository = new InMemoryToDoDataAccessObject();

        // This creates a successPresenter that tests whether the test case is as we expect.
        AddToDoItemOutputBoundary successPresenter = new AddToDoItemOutputBoundary() {
            @Override
            public void prepareSuccessView(AddToDoItemOutputData toDoItemData) {
                // Check that the output data is correct and that the item has been created in the DAO.
                assertEquals("Grocery Shopping", toDoItemData.getTitle());
                assertTrue(toDoRepository.existsByTitle("Grocery Shopping"));
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }
        };

        AddToDoItemInputBoundary interactor = new AddToDoItemInteractor(toDoRepository, successPresenter, new ToDoItemFactory());
        interactor.execute(inputData);
    }

    @Test
    void failureTitleExistsTest() {
        AddToDoItemInputData inputData = new AddToDoItemInputData("Grocery Shopping", "Buy groceries for the week", LocalDate.now(), 2);
        ToDoItemDataAccessInterface toDoRepository = new InMemoryToDoDataAccessObject();

        // Add an item with the same title to the repository
        ToDoItemFactory factory = new ToDoItemFactory();
        ToDoItem existingItem = factory.create("Grocery Shopping", "Buy groceries for the week", LocalDate.now(), 2);
        toDoRepository.save(existingItem);

        // This creates a presenter that tests whether the test case is as we expect.
        AddToDoItemOutputBoundary failurePresenter = new AddToDoItemOutputBoundary() {
            @Override
            public void prepareSuccessView(AddToDoItemOutputData toDoItemData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("ToDo item with this title already exists.", error);
            }
        };

        AddToDoItemInputBoundary interactor = new AddToDoItemInteractor(toDoRepository, failurePresenter, new ToDoItemFactory());
        interactor.execute(inputData);
    }
}
