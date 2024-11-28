package interface_adapter.reminder;

import java.util.List;

/**
 * Method to check the status of the task.
 */
public class ReminderState {
    private List<String> reminders;

    public List<String> getReminders() {
        return reminders;
    }

    public void setReminders(List<String> reminders) {
        this.reminders = reminders;
    }
}
