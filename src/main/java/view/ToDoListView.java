package view;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

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
import entity.ToDoItem;
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
    private final JCheckBox filterIncompleteCheckbox = new JCheckBox("Show only incomplete items");
    private final JButton readTitlesButton = new JButton("Read Titles"); // New button for reading titles
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

        filterIncompleteCheckbox.addActionListener(evt -> updateToDoListDisplay());

        
        JScrollPane listScrollPane = new JScrollPane(toDoListDisplay);


        // Add ActionListener for Read Titles Button
        readTitlesButton.addActionListener(evt -> readTitlesAloud());


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


        this.add(filterIncompleteCheckbox);
        this.add(readTitlesButton); // Add the "Read Titles" button


        // Initialize the display with current items
        updateToDoListDisplay();
    }


    VoiceManager freeVM;
    Voice voice;

    public void TextToSpeech(String words) {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        voice = VoiceManager.getInstance().getVoice("kevin16");
        if (voice != null) {
            voice.allocate();// Allocating Voice
            try {
                voice.setRate(190);// Setting the rate of the voice
                voice.setPitch(150);// Setting the Pitch of the voice
                voice.setVolume(3);// Setting the volume of the voice
                voice.speak(words);

            } catch (Exception e1) {
                e1.printStackTrace();
            }

        } else {
            throw new IllegalStateException("Cannot find voice: kevin16");
        }
    }

    private void readTitlesAloud() {
        try {
            for (int i = 0; i < toDoListDisplay.getModel().getSize(); i++) {
                String title = toDoListDisplay.getModel().getElementAt(i);
                ToDoItem item = toDoDataAccess.get(title);
                String status = item.isCompleted() ? "Completed" : "Not Completed";
                TextToSpeech(title + " : " + status);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error with text-to-speech: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
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
        boolean filterIncomplete = filterIncompleteCheckbox.isSelected();

        for (ToDoItem item : toDoDataAccess.getAllToDoItems().values()) {
            if (!filterIncomplete || !item.isCompleted()) {
                listModel.addElement(item.getTitle());
            }
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


