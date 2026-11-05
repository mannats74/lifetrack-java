
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class FileStorage {

    // Save all activities to a file
    public static void saveActivities(List<Activity> activities, String filename)
            throws IOException {

        File file = new File(filename);

        // Create the data directory if it doesn't exist
        File parent = file.getParentFile();

        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {

            for (Activity activity : activities) {

                String title = Base64.getEncoder().encodeToString(
                        activity.getTitle().getBytes(StandardCharsets.UTF_8));

                String category = Base64.getEncoder().encodeToString(
                        activity.getCategory().getBytes(StandardCharsets.UTF_8));

                String line = activity.getId() + "|" +
                        activity.isCompleted() + "|" +
                        title + "|" +
                        category;

                writer.write(line);
                writer.newLine();
            }
        }
    }

    // Load activities from a file
    public static ArrayList<Activity> loadActivities(String filename)
            throws IOException {

        ArrayList<Activity> activities = new ArrayList<Activity>();

        File file = new File(filename);

        // No saved file yet: start with an empty list
        if (!file.exists()) {
            return activities;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                try {
                    String[] parts = line.split("\\|", -1);

                    if (parts.length != 4) {
                        continue;
                    }

                    int id = Integer.parseInt(parts[0]);

                    if (id <= 0) {
                        continue;
                    }

                    if (!parts[1].equals("true") && !parts[1].equals("false")) {
                        continue;
                    }

                    boolean completed = Boolean.parseBoolean(parts[1]);

                    String title = new String(
                            Base64.getDecoder().decode(parts[2]),
                            StandardCharsets.UTF_8);

                    String category = new String(
                            Base64.getDecoder().decode(parts[3]),
                            StandardCharsets.UTF_8);

                    activities.add(
                            new Activity(id, title, category, completed));

                } catch (IllegalArgumentException e) {
                    // Skip malformed records instead of crashing
                }
            }
        }

        return activities;
    }
}
