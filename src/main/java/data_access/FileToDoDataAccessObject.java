package data_access;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import entity.ToDoItem;

/**
 * DAO for to-do item data implemented using a File to persist the data.
 */
public class FileToDoDataAccessObject {

    private static final String HEADER = "title,description,dueDate,priority,isCompleted";

    private final File csvFile;
    private final Map<String, Integer> headers = new LinkedHashMap<>();
    private final Map<String, ToDoItem> toDoItems = new HashMap<>();

    public FileToDoDataAccessObject(String csvPath) throws IOException {

        csvFile = new File(csvPath);
        headers.put("title", 0);
        headers.put("description", 1);
        headers.put("dueDate", 2);
        headers.put("priority", 3);
        headers.put("isCompleted", 4);

        if (csvFile.length() == 0) {
            save();
        } else {
            try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
                final String header = reader.readLine();

                if (!header.equals(HEADER)) {
                    throw new RuntimeException(String.format("Header should be%n: %s%nbut was:%n%s", HEADER, header));
                }

                String row;
                while ((row = reader.readLine()) != null) {
                    final String[] col = row.split(",");
                    final String title = col[headers.get("title")];
                    final String description = col[headers.get("description")];
                    final LocalDate dueDate = LocalDate.parse(col[headers.get("dueDate")]);
                    final int priority = Integer.parseInt(col[headers.get("priority")]);
                    final boolean isCompleted = Boolean.parseBoolean(col[headers.get("isCompleted")]);
                    final ToDoItem toDoItem = new ToDoItem(title, description, dueDate, priority);
                    toDoItem.setCompleted(isCompleted);
                    toDoItems.put(title, toDoItem);
                }
            }
        }
    }

    private void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
            writer.write(String.join(",", headers.keySet()));
            writer.newLine();

            for (ToDoItem item : toDoItems.values()) {
                final String line = String.format("%s,%s,%s,%d,%b",
                        item.getTitle(), item.getDescription(), item.getDueDate(),
                        item.getPriority(), item.isCompleted());
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void save(ToDoItem item) {
        toDoItems.put(item.getTitle(), item);
        this.save();
    }

    public ToDoItem get(String title) {
        return toDoItems.get(title);
    }

    public boolean existsByTitle(String title) {
        return toDoItems.containsKey(title);
    }

    public void updateToDoItem(ToDoItem item) {
        // Replace the ToDoItem object in the map
        toDoItems.put(item.getTitle(), item);
        save();
    }

    public void delete(String title) {
        toDoItems.remove(title);
    }

}
