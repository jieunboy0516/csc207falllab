package use_case.reminder;

import java.time.LocalDate;
import java.util.List;

import data_access.InMemoryToDoDataAccessObject;

/**
 * Constructor for the interactor.
 */
public class CheckRemindersInteractor implements CheckRemindersInputBoundary {
    private final InMemoryToDoDataAccessObject dataAccess;
    private final CheckRemindersOutputBoundary outputBoundary;

    public CheckRemindersInteractor(InMemoryToDoDataAccessObject dataAccess,
                                    CheckRemindersOutputBoundary outputBoundary) {
        this.dataAccess = dataAccess;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void checkReminders() {
        final LocalDate today = LocalDate.now();
        final List<String> reminders = dataAccess.getUpcomingReminders(today);
        outputBoundary.presentReminders(reminders);
    }
}
