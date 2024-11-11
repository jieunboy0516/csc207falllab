package app;


import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import data_access.InMemoryToDoDataAccessObject;
import entity.ToDoItemFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.addItem.AddToDoItemController;
import interface_adapter.addItem.AddToDoItemPresenter;
import interface_adapter.addItem.AddToDoItemViewModel;
import interface_adapter.DeleteItem.DeleteToDoItemController;
import interface_adapter.EditToDoItem.EditToDoItemController;
import interface_adapter.EditToDoItem.EditToDoItemPresenter;
import interface_adapter.EditToDoItem.EditToDoItemViewModel;
import use_case.AddItem.AddToDoItemInputBoundary;
import use_case.AddItem.AddToDoItemInteractor;
import use_case.AddItem.AddToDoItemOutputBoundary;
import use_case.DeleteItem.DeleteToDoItemInputBoundary;
import use_case.DeleteItem.DeleteToDoItemInteractor;
import use_case.EditToDoItem.EditToDoItemInputBoundary;
import use_case.EditToDoItem.EditToDoItemInteractor;
import use_case.EditToDoItem.EditToDoItemOutputBoundary;
import view.ToDoListView;
import view.EditToDoItemView;
import view.ViewManager;

/**
 * The AppBuilder class for the To-Do List application.
 * It constructs and wires the components following the CA architecture.
 */
public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();

    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    private ToDoListView toDoListView;
    private AddToDoItemViewModel addToDoItemViewModel;
    private EditToDoItemView editToDoItemView;
    private EditToDoItemViewModel editToDoItemViewModel;


    private InMemoryToDoDataAccessObject toDoDataAccess = new InMemoryToDoDataAccessObject();

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    /**
     * Adds the To-Do List View to the application.
     * @return this builder
     */
    public AppBuilder addToDoListView() {
        addToDoItemViewModel = new AddToDoItemViewModel("To-Do List View Model");
        toDoListView = new ToDoListView(addToDoItemViewModel, toDoDataAccess);
        cardPanel.add(toDoListView, toDoListView.getViewName());
        return this;
    }

    /**
     * Adds the Add To-Do Item Use Case to the application.
     * @return this builder
     */
    public AppBuilder addAddToDoItemUseCase() {

        ToDoItemFactory toDoItemFactory = new ToDoItemFactory();

        AddToDoItemOutputBoundary addToDoItemOutputBoundary = new AddToDoItemPresenter(viewManagerModel, addToDoItemViewModel, toDoListView);
        AddToDoItemInputBoundary addToDoItemInteractor = new AddToDoItemInteractor(addToDoItemOutputBoundary,toDoDataAccess, toDoItemFactory);

        AddToDoItemController toDoController = new AddToDoItemController(addToDoItemInteractor);
        toDoListView.setToDoController(toDoController);
        return this;
    }



    /**
     * Adds the Edit To-Do Item Use Case to the application.
     * @return this builder
     */
    public AppBuilder addEditToDoItemUseCase() {

        editToDoItemViewModel = new EditToDoItemViewModel("Edit To-Do Item");

        EditToDoItemOutputBoundary editToDoItemOutputBoundary = new EditToDoItemPresenter(editToDoItemViewModel);
        EditToDoItemInputBoundary editToDoItemInteractor = new EditToDoItemInteractor(toDoDataAccess, editToDoItemOutputBoundary);

        EditToDoItemController editToDoController = new EditToDoItemController(editToDoItemInteractor, toDoDataAccess, toDoListView);
        editToDoItemView = new EditToDoItemView(editToDoItemViewModel);

        toDoListView.setEditToDoController(editToDoController);
        editToDoItemViewModel.setEditToDoItemController(editToDoController);

        cardPanel.add(editToDoItemView, editToDoItemView.getViewName());
        return this;
    }


    /**
     * Adds the Delete To-Do Item Use Case to the application.
     * @return this builder
     */
    public AppBuilder addDeleteToDoItemUseCase() {
        DeleteToDoItemInputBoundary deleteToDoItemInteractor = new DeleteToDoItemInteractor(toDoDataAccess);
        DeleteToDoItemController deleteToDoController = new DeleteToDoItemController(deleteToDoItemInteractor);

        toDoListView.setDeleteToDoController(deleteToDoController);
        return this;
    }

    /**
     * Creates the JFrame for the application and sets the ToDoListView to be displayed initially.
     * @return the application
     */
    public JFrame build() {
        final JFrame application = new JFrame("To-Do List Application");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        application.add(cardPanel);

        viewManagerModel.setState(toDoListView.getViewName());
        viewManagerModel.firePropertyChanged();

        return application;
    }
}
