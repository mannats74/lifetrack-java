
import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;

public class App {

    private static Scanner scanner = new Scanner(System.in);
    private static ActivityManager manager = new ActivityManager();

    private static final String DATA_FILE = "data/activities.txt";

    public static void main(String[] args) {

        // Load previously saved activities
        try {
            manager.loadActivities(FileStorage.loadActivities(DATA_FILE));
            System.out.println("Saved activities loaded successfully.");
        } catch (IOException e) {
            System.out.println("Could not load saved activities: " + e.getMessage());
        }

        boolean running = true;

        System.out.println("\n================================");
        System.out.println("         LIFETRACKER");
        System.out.println("================================");

        while (running) {
            displayMenu();

            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    addActivity();
                    break;
                case 2:
                    viewActivities(manager.getActivities());
                    break;
                case 3:
                    completeActivity();
                    break;
                case 4:
                    deleteActivity();
                    break;
                case 5:
                    searchActivities();
                    break;
                case 6:
                    editActivity();
                    break;
                case 7:
                    filterActivities();
                    break;
                case 8:
                    saveData();
                    running = false;
                    System.out.println("Thank you for using LifeTracker!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }

        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n========== MAIN MENU ==========");
        System.out.println("1. Add activity");
        System.out.println("2. View all activities");
        System.out.println("3. Mark activity completed");
        System.out.println("4. Delete activity");
        System.out.println("5. Search activities");
        System.out.println("6. Edit activity");
        System.out.println("7. Filter activities");
        System.out.println("8. Exit");
        System.out.println("===============================");
    }

    private static void addActivity() {
        System.out.print("Enter activity title: ");
        String title = scanner.nextLine().trim();

        System.out.print("Enter category (Movie, TV Show, Book, Fitness, Other): ");
        String category = scanner.nextLine().trim();

        if (title.isEmpty() || category.isEmpty()) {
            System.out.println("Title and category cannot be empty.");
            return;
        }

        Activity activity = manager.addActivity(title, category);
        saveData();

        System.out.println("Activity added successfully!");
        System.out.println(activity);
    }

    private static void viewActivities(ArrayList<Activity> activities) {
        if (activities.isEmpty()) {
            System.out.println("No activities found.");
            return;
        }

        System.out.println("\n========== YOUR ACTIVITIES ==========");

        for (Activity activity : activities) {
            System.out.println(activity);
        }
    }

    private static void completeActivity() {
        int id = readInt("Enter the activity ID to complete: ");

        if (manager.completeActivity(id)) {
            saveData();
            System.out.println("Activity marked as completed!");
        } else {
            System.out.println("Activity not found.");
        }
    }

    private static void deleteActivity() {
        int id = readInt("Enter the activity ID to delete: ");

        if (manager.deleteActivity(id)) {
            saveData();
            System.out.println("Activity deleted successfully!");
        } else {
            System.out.println("Activity not found.");
        }
    }

    private static void searchActivities() {
        System.out.print("Enter title to search: ");
        String keyword = scanner.nextLine().trim();

        viewActivities(manager.searchActivities(keyword));
    }

    private static void editActivity() {
        int id = readInt("Enter the activity ID to edit: ");

        Activity activity = manager.findActivityById(id);

        if (activity == null) {
            System.out.println("Activity not found.");
            return;
        }

        System.out.println("Current activity: " + activity);

        System.out.print("Enter new title: ");
        String title = scanner.nextLine().trim();

        System.out.print("Enter new category: ");
        String category = scanner.nextLine().trim();

        if (title.isEmpty() || category.isEmpty()) {
            System.out.println("Title and category cannot be empty.");
            return;
        }

        manager.updateActivity(id, title, category);
        saveData();

        System.out.println("Activity updated successfully!");
    }

    private static void filterActivities() {
        System.out.println("1. Filter by category");
        System.out.println("2. Show completed activities");
        System.out.println("3. Show incomplete activities");

        int choice = readInt("Choose filter: ");

        if (choice == 1) {
            System.out.print("Enter category: ");
            String category = scanner.nextLine().trim();

            viewActivities(manager.filterByCategory(category));
        } else if (choice == 2) {
            viewActivities(manager.filterByCompletion(true));
        } else if (choice == 3) {
            viewActivities(manager.filterByCompletion(false));
        } else {
            System.out.println("Invalid filter choice.");
        }
    }

    private static void saveData() {
        try {
            FileStorage.saveActivities(manager.getActivities(), DATA_FILE);
        } catch (IOException e) {
            System.out.println("Warning: Could not save activities: " + e.getMessage());
        }
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();

            try {
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}
