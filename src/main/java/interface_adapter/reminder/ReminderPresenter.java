package interface_adapter.reminder;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import use_case.reminder.CheckRemindersOutputBoundary;

/**
 * Constructor for the presenter.
 */
public class ReminderPresenter implements CheckRemindersOutputBoundary {
    private final ReminderViewModel viewModel;

    public ReminderPresenter(ReminderViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentReminders(List<String> reminders) {
        viewModel.updateReminders(reminders);

        SwingUtilities.invokeLater(() -> {
            final String message = String.join("\n", reminders);
            JOptionPane.showMessageDialog(null, message, "Reminders", JOptionPane.INFORMATION_MESSAGE);
        });
    }
}
