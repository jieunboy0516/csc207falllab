package use_case.HighestPriority;

import entity.ToDoItem;

import java.util.Optional;

public class HighestPriorityInteractor implements HighestPriorityInputBoundary {

    private final HighestPriorityTaskRetriever retriever;
    private final HighestPriorityOutputBoundary outputBoundary;

    public HighestPriorityInteractor(HighestPriorityTaskRetriever retriever, HighestPriorityOutputBoundary outputBoundary) {
        this.retriever = retriever;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void retrieveHighestPriorityTask(HighestPriorityInputData inputData) {
        Optional<ToDoItem> task = retriever.getHighestPriorityTask(inputData.getTasks());

        if (task.isPresent()) {
            HighestPriorityOutputData outputData = new HighestPriorityOutputData(
                    task.get(),
                    "Highest priority task retrieved successfully."
            );
            outputBoundary.presentHighestPriorityTask(outputData);
        } else {
            HighestPriorityOutputData outputData = new HighestPriorityOutputData(
                    null,
                    "No highest priority task found."
            );
            outputBoundary.presentHighestPriorityTask(outputData);
        }
    }
}
