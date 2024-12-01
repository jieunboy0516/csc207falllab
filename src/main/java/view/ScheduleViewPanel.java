package view;

import entity.ToDoItem;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

/**
 * A panel to display the weekly schedule view with color-coded priorities.
 */
public class ScheduleViewPanel extends JPanel {
    public ScheduleViewPanel(LocalDate weekStart, List<List<ToDoItem>> tasksByDay) {
        this.setLayout(new BorderLayout());

        // Add a priority legend at the top
        JPanel legendPanel = new JPanel();
        legendPanel.setLayout(new BoxLayout(legendPanel, BoxLayout.X_AXIS));
        legendPanel.add(createLegendLabel("High Priority (Red)", new Color(255, 102, 102), Color.WHITE));
        legendPanel.add(Box.createHorizontalStrut(10)); // Spacing
        legendPanel.add(createLegendLabel("Medium Priority (Orange)", new Color(255, 178, 102), Color.BLACK));
        legendPanel.add(Box.createHorizontalStrut(10)); // Spacing
        legendPanel.add(createLegendLabel("Low Priority (Green)", new Color(153, 255, 153), Color.BLACK));
        this.add(legendPanel, BorderLayout.NORTH);

        // Create the grid for the schedule
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(2, 7)); // 2 rows: headers and tasks

        // Array of day names for the headers
        String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

        // Add headers with week dates
        LocalDate currentDate = weekStart;
        for (String day : days) {
            JLabel dayLabel = new JLabel(day + " (" + currentDate + ")", SwingConstants.CENTER);
            dayLabel.setFont(new Font("Arial", Font.BOLD, 14));
            gridPanel.add(dayLabel);
            currentDate = currentDate.plusDays(1);
        }

        // Add tasks for each day
        for (List<ToDoItem> tasks : tasksByDay) {
            JPanel taskPanel = new JPanel();
            taskPanel.setLayout(new BoxLayout(taskPanel, BoxLayout.Y_AXIS));

            // Sort tasks by priority
            tasks.sort(Comparator.comparingInt(ToDoItem::getPriority));

            for (ToDoItem task : tasks) {
                JLabel taskLabel = new JLabel(task.getTitle() + " (Due: " + task.getDueDate() + ")");
                taskLabel.setOpaque(true); // Allow background color

                // Set background color based on priority
                switch (task.getPriority()) {
                    case 1: taskLabel.setBackground(new Color(255, 102, 102)); taskLabel.setForeground(Color.WHITE); break;
                    case 2: taskLabel.setBackground(new Color(255, 178, 102)); taskLabel.setForeground(Color.BLACK); break;
                    case 3: taskLabel.setBackground(new Color(153, 255, 153)); taskLabel.setForeground(Color.BLACK); break;
                }

                taskLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Padding
                taskPanel.add(taskLabel);
            }

            JScrollPane scrollPane = new JScrollPane(taskPanel); // Add scrollable area for tasks
            gridPanel.add(scrollPane);
        }

        this.add(gridPanel, BorderLayout.CENTER);
    }

    /**
     * Creates a legend label with specific text, background color, and foreground color.
     *
     * @param text   The text for the legend.
     * @param bgColor The background color for the label.
     * @param fgColor The foreground (text) color for the label.
     * @return A JLabel configured for the legend.
     */
    private JLabel createLegendLabel(String text, Color bgColor, Color fgColor) {
        JLabel legendLabel = new JLabel(text);
        legendLabel.setOpaque(true);
        legendLabel.setBackground(bgColor);
        legendLabel.setForeground(fgColor);
        legendLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Padding
        return legendLabel;
    }
}
