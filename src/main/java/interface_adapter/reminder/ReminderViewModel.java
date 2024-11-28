package interface_adapter.reminder;

import java.util.List;

import interface_adapter.ViewModel;

/**
 * Constructor for the View Model.
 */
public class ReminderViewModel extends ViewModel<ReminderState> {

    public ReminderViewModel(String viewName) {
        super(viewName);
    }

    /**
     * Method to open up the reminder smaller window.
     *  @param reminders this is the string of the titles of the upcoming tasks.
     */
    public void updateReminders(List<String> reminders) {
        final ReminderState state = new ReminderState();
        state.setReminders(reminders);
        setState(state);
        firePropertyChanged("reminders");
    }
}
