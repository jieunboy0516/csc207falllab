package interface_adapter.reminder;

import use_case.reminder.CheckRemindersInputBoundary;

/**
 * Constructor for the controller.
 */
public class CheckRemindersController {
    private final CheckRemindersInputBoundary interactor;

    public CheckRemindersController(CheckRemindersInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * This get the reminders.
     */
    public void checkReminders() {
        interactor.checkReminders();
    }
}

