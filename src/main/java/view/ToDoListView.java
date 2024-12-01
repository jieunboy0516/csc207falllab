package view;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.Scanner;
import javax.swing.*;

import data_access.InMemoryToDoDataAccessObject;
import entity.ToDoItem;
import interface_adapter.EditToDoItem.EditToDoItemController;
import interface_adapter.addItem.AddToDoItemController;
import interface_adapter.addItem.AddToDoItemViewModel;
import interface_adapter.DeleteItem.DeleteToDoItemController;
import interface_adapter.reminder.CheckRemindersController;
import use_case.schedule.GetTasksForWeekInteractor;
import interface_adapter.schedule.ScheduleViewPresenter;
import java.util.ArrayList;
import java.awt.BorderLayout;


/**
 * The View for displaying and managing the to-do list.
 */
public class ToDoListView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "To-Do List view";
    private final AddToDoItemViewModel toDoViewModel;
    private final InMemoryToDoDataAccessObject toDoDataAccess;


    private final JTextField usernameInputField = new JTextField(15); // New username input field
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
    private final JButton checkRemindersButton = new JButton("Check Reminders");
    private final JButton scheduleViewButton = new JButton("Schedule View");
    private final JCheckBox filterIncompleteCheckbox = new JCheckBox("Show only incomplete items");
    private final JButton readTitlesButton = new JButton("Read Titles"); // New button for reading titles
    private JList<String> toDoListDisplay = new JList<>(new DefaultListModel<>());
    private AddToDoItemController toDoController;
    private EditToDoItemController editToDoController;
    private DeleteToDoItemController deleteToDoController;
    private CheckRemindersController checkRemindersController;



    public ToDoListView(AddToDoItemViewModel toDoViewModel, InMemoryToDoDataAccessObject toDoDataAccess) {
        this.toDoViewModel = toDoViewModel;
        this.toDoDataAccess = toDoDataAccess;
        this.toDoViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel("To-Do List");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);


        final LabelTextPanel usernameInfo = new LabelTextPanel(
                new JLabel("Username"), usernameInputField); // New username panel
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
                if (!validateUsername(usernameInputField.getText())) {
                    JOptionPane.showMessageDialog(this, "Invalid username. Please check your input.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

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

        // Add ActionListener to the "Check Reminders" button
        checkRemindersButton.addActionListener(evt -> {
            if (checkRemindersController != null) {
                checkRemindersController.checkReminders();
            }
        });

        scheduleViewButton.addActionListener(evt -> showScheduleView());

        filterIncompleteCheckbox.addActionListener(evt -> updateToDoListDisplay());

        
        JScrollPane listScrollPane = new JScrollPane(toDoListDisplay);


        // Add ActionListener for Read Titles Button
        readTitlesButton.addActionListener(evt -> readTitlesAloud());


        // Layout setup
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(usernameInfo);
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
        this.add(checkRemindersButton);
        this.add(scheduleViewButton);


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

    private void showScheduleView() {
        JFrame scheduleFrame = new JFrame("Weekly Schedule");
        scheduleFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        scheduleFrame.setSize(800, 400); // Set appropriate size

        // Initialize variables for week navigation
        final LocalDate[] currentWeekStart = {LocalDate.now().with(java.time.DayOfWeek.SUNDAY)};

        // Create a panel to hold the schedule view and navigation buttons
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Schedule panel placeholder
        final JPanel[] schedulePanel = {new JPanel()};

        // Method to update the schedule panel
        Runnable updateSchedule = () -> {
            // Remove the old schedule panel
            mainPanel.remove(schedulePanel[0]);

            // Fetch tasks for the current week
            GetTasksForWeekInteractor interactor = new GetTasksForWeekInteractor();
            ScheduleViewPresenter presenter = new ScheduleViewPresenter();
            var allTasks = toDoDataAccess.getAllToDoItems().values();
            var tasksByWeek = interactor.getTasksForCurrentWeek(new ArrayList<>(allTasks), currentWeekStart[0]);

            // Create a new schedule panel
            schedulePanel[0] = new ScheduleViewPanel(currentWeekStart[0], tasksByWeek);

            // Add the new schedule panel to the main panel
            mainPanel.add(schedulePanel[0], BorderLayout.CENTER);

            // Refresh the frame
            mainPanel.revalidate();
            mainPanel.repaint();
        };

        // Navigation buttons
        JButton prevWeekButton = new JButton("Previous Week");
        JButton nextWeekButton = new JButton("Next Week");
        JButton currentWeekButton = new JButton("Current Week");

        prevWeekButton.addActionListener(e -> {
            currentWeekStart[0] = currentWeekStart[0].minusWeeks(1);
            updateSchedule.run();
        });

        nextWeekButton.addActionListener(e -> {
            currentWeekStart[0] = currentWeekStart[0].plusWeeks(1);
            updateSchedule.run();
        });

        currentWeekButton.addActionListener(e -> {
            currentWeekStart[0] = LocalDate.now().with(java.time.DayOfWeek.SUNDAY);
            updateSchedule.run();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(prevWeekButton);
        buttonPanel.add(currentWeekButton); // Add the "Current Week" button here
        buttonPanel.add(nextWeekButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Initial schedule load
        updateSchedule.run();

        scheduleFrame.add(mainPanel);
        scheduleFrame.setVisible(true);
    }



    /**
     * Validates the username by sending a GET request to the API.
     * @param username the username to validate
     * @return true if the username exists, false otherwise
     */
    private boolean validateUsername(String username) {
        try {
            URL url = new URL("https://jsonplaceholder.typicode.com/users");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            if (connection.getResponseCode() == 200) { // Success
                StringBuilder inline = new StringBuilder();
                Scanner scanner = new Scanner(connection.getInputStream());

                while (scanner.hasNext()) {
                    inline.append(scanner.nextLine());
                }
                scanner.close();

                // Use JsonParser to parse the JSON response
                JsonArray users = com.google.gson.JsonParser.parseString(inline.toString()).getAsJsonArray();
                for (int i = 0; i < users.size(); i++) {
                    JsonObject user = users.get(i).getAsJsonObject();
                    if (user.get("username").getAsString().equals(username)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error validating username: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
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

    public void setCheckRemindersController(CheckRemindersController checkRemindersController) {
        this.checkRemindersController = checkRemindersController;
    }

}


