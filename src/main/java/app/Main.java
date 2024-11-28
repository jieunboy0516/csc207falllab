package app;

import javax.swing.JFrame;

/**
 * The Main class of the To-Do List application.
 */
public class Main {
    /**
     * Builds and runs the CA architecture of the application.
     * @param args unused arguments
     */
    public static void main(String[] args) {
        final AppBuilder appBuilder = new AppBuilder();

        final JFrame application = appBuilder
                .addToDoListView()
                .addAddToDoItemUseCase()
                .addEditToDoItemUseCase()
                .addDeleteToDoItemUseCase()
                .addCheckRemindersUseCase()
                .build();

        application.pack();
        application.setVisible(true);
    }
}
