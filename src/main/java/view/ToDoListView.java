package view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import javax.swing.*;

import data_access.InMemoryToDoDataAccessObject;
import interface_adapter.EditToDoItem.EditToDoItemController;
import interface_adapter.addItem.AddToDoItemController;
import interface_adapter.addItem.AddToDoItemViewModel;
import interface_adapter.DeleteItem.DeleteToDoItemController;

/**
 * The View for displaying and managing the to-do list.
 */
public class ToDoListView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "To-Do List view";
    private final AddToDoItemViewModel toDoViewModel;
    private final InMemoryToDoDataAccessObject toDoDataAccess;

    private final JTextField titleInputField = new JTextField(15);
    private final JTextField descriptionInputField = new JTextField(15);
    private final JTextField dueDateInputField = new JTextField(15); // Due date input field
    private final JComboBox<String> priorityInputField = new JComboBox<>(new String[]{"High", "Medium", "Low"});

    private final JLabel titleErrorField = new JLabel();
    private final JLabel descriptionErrorField = new JLabel();
    private final JLabel dueDateErrorField = new JLabel();
    private final JLabel priorityErrorField = new JLabel();

    private final JButton addButton;
    private final JButton deleteButton;
    private JList<String> toDoListDisplay = new JList<>(new DefaultListModel<>());
    private AddToDoItemController toDoController;
    private EditToDoItemController editToDoController;
    private DeleteToDoItemController deleteToDoController;


    public ToDoListView(AddToDoItemViewModel toDoViewModel, InMemoryToDoDataAccessObject toDoDataAccess) {
        this.toDoViewModel = toDoViewModel;
        this.toDoDataAccess = toDoDataAccess;
        this.toDoViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel("To-Do List");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final LabelTextPanel titleInfo = new LabelTextPanel(
                new JLabel("Title"), titleInputField);
        final LabelTextPanel descriptionInfo = new LabelTextPanel(
                new JLabel("Description"), descriptionInputField);
        final LabelTextPanel dueDateInfo = new LabelTextPanel(
                new JLabel("Due Date (YYYY-MM-DD)"), dueDateInputField); // Panel for due date
        final LabelComboPanel priorityInfo = new LabelComboPanel(
                new JLabel("Priority"), priorityInputField);

        final JPanel buttons = new JPanel();
        addButton = new JButton("Add Item");
        buttons.add(addButton);
        deleteButton = new JButton("Delete Item");
        buttons.add(deleteButton);

        addButton.addActionListener(evt -> {
            if (evt.getSource().equals(addButton)) {
                try {
                    LocalDate dueDate = LocalDate.parse(dueDateInputField.getText());
                    toDoController.addToDoItem(
                            titleInputField.getText(),
                            descriptionInputField.getText(),
                            dueDate, // Pass the parsed due date
                            priorityInputField.getSelectedIndex() + 1
                    );
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Invalid due date format. Please use YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        deleteButton.addActionListener(evt -> {
            if (evt.getSource().equals(deleteButton)) {
                String selectedItem = toDoListDisplay.getSelectedValue();
                if (selectedItem != null) {
                    int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this item?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        deleteToDoController.deleteToDoItem(selectedItem);
                        updateToDoListDisplay();  // Refresh the list after deletion
                    }
                }
            }
        });


        // Initialize JList and add double-click listener
        toDoListDisplay = new JList<>(new DefaultListModel<>());
        toDoListDisplay.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Double-click detected
                    String selectedItem = toDoListDisplay.getSelectedValue();
                    if (selectedItem != null) {
                        editToDoController.openEditView(selectedItem);
                    }
                }
            }
        });

        JScrollPane listScrollPane = new JScrollPane(toDoListDisplay);


        // Layout setup
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(titleInfo);
        this.add(titleErrorField);
        this.add(descriptionInfo);
        this.add(descriptionErrorField);
        this.add(dueDateInfo); // Add due date panel
        this.add(dueDateErrorField);
        this.add(priorityInfo);
        this.add(priorityErrorField);
        this.add(buttons);
        this.add(listScrollPane);

        // Initialize the display with current items
        updateToDoListDisplay();
    }

    /**
     * React to a button click that results in evt.
     * @param evt the ActionEvent to react to
     */
    @Override
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("toDoList".equals(evt.getPropertyName())) {
            // Update the list display when the to-do list changes
            updateToDoListDisplay();
        } else if ("errorMessage".equals(evt.getPropertyName())) {
            // Show error message if there's an issue
            String error = (String) evt.getNewValue();
            JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Updates the JList display with the current items in the to-do data access object.
     */
    public void updateToDoListDisplay() {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (String title : toDoDataAccess.getAllToDoItems().keySet()) {
            listModel.addElement(title);
        }
        toDoListDisplay.setModel(listModel);
    }

    public String getViewName() {
        return viewName;
    }

    public void setToDoController(AddToDoItemController toDoController) {
        this.toDoController = toDoController;
    }

    public void setEditToDoController(EditToDoItemController editToDoController) {
        this.editToDoController = editToDoController;
    }

    public void setDeleteToDoController(DeleteToDoItemController deleteToDoController) {
        this.deleteToDoController = deleteToDoController;
    }


}


