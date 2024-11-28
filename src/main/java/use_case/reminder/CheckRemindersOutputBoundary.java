package use_case.reminder;

import java.util.List;

/**
 * Constructor for the output boundary.
 */
public interface CheckRemindersOutputBoundary {
    /**
     * Blah.
     *  @param reminders this is the string of the titles of the upcoming tasks.
     */
    void presentReminders(List<String> reminders);
}
