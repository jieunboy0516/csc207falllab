package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import interface_adapter.EditToDoItem.EditToDoItemController;
import interface_adapter.EditToDoItem.EditToDoItemViewModel;

public class EditToDoItemView extends JPanel implements ActionListener {

    private final String viewName = "Edit Item view";
    private final EditToDoItemViewModel viewModel;
    private EditToDoItemController controller;

    private final JTextField titleField = new JTextField(15);
    private final JTextField descriptionField = new JTextField(15);
    private final JTextField dueDateField = new JTextField(10);
    private final JComboBox<String> priorityComboBox = new JComboBox<>(new String[]{"High", "Medium", "Low"});
    private final JCheckBox completedCheckBox = new JCheckBox("Completed");
    private final JButton saveButton = new JButton("Save");
    private final JButton cancelButton = new JButton("Cancel");

    public EditToDoItemView(EditToDoItemViewModel viewModel) {
        this.viewModel = viewModel;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new JLabel("Edit To-Do Item"));

        add(new JLabel("Title:"));
        add(titleField);

        add(new JLabel("Description:"));
        add(descriptionField);

        add(new JLabel("Due Date (YYYY-MM-DD):"));
        add(dueDateField);

        add(new JLabel("Priority:"));
        add(priorityComboBox);

        add(completedCheckBox);

        add(saveButton);
        add(cancelButton);

        saveButton.addActionListener(this);
        cancelButton.addActionListener(e -> setVisible(false));
    }

    public void setController(EditToDoItemController controller) {
        this.controller = controller;
    }

    public EditToDoItemController getController() {
        return controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveButton) {
            try {
                String newTitle = titleField.getText();
                String description = descriptionField.getText();
                LocalDate dueDate = LocalDate.parse(dueDateField.getText());
                int priority = priorityComboBox.getSelectedIndex() + 1;
                boolean isCompleted = completedCheckBox.isSelected();

                // Execute edit using controller
                controller.executeEdit(newTitle, description, dueDate, priority, isCompleted);
                JOptionPane.showMessageDialog(this, "To-Do item updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                SwingUtilities.getWindowAncestor(this).dispose(); // Close the window

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error updating item. Please check your inputs.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void populateFields(String originalTitle, String description, LocalDate dueDate, int priority, boolean isCompleted) {
        titleField.setText(originalTitle);
        descriptionField.setText(description);
        dueDateField.setText(dueDate.toString());
        priorityComboBox.setSelectedIndex(priority - 1);
        completedCheckBox.setSelected(isCompleted);
    }

    public String getViewName() {
        return viewName;
    }
}
