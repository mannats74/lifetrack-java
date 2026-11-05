
import java.util.ArrayList;

public class ActivityManager {

    private ArrayList<Activity> activities;
    private int nextId;

    public ActivityManager() {
        activities = new ArrayList<Activity>();
        nextId = 1;
    }

    // Add a new activity
    public Activity addActivity(String title, String category) {
        Activity activity = new Activity(nextId, title, category);
        activities.add(activity);
        nextId++;

        return activity;
    }

    // Retrieve all activities
    public ArrayList<Activity> getActivities() {
        return new ArrayList<Activity>(activities);
    }

    // Find an activity using its ID
    public Activity findActivityById(int id) {
        for (Activity activity : activities) {
            if (activity.getId() == id) {
                return activity;
            }
        }

        return null;
    }

    // Mark an activity as completed
    public boolean completeActivity(int id) {
        Activity activity = findActivityById(id);

        if (activity == null) {
            return false;
        }

        activity.markCompleted();
        return true;
    }

    // Delete an activity
    public boolean deleteActivity(int id) {
        Activity activity = findActivityById(id);

        if (activity == null) {
            return false;
        }

        activities.remove(activity);
        return true;
    }

    // Update an activity
    public boolean updateActivity(int id, String title, String category) {
        Activity activity = findActivityById(id);

        if (activity == null) {
            return false;
        }

        activity.setTitle(title);
        activity.setCategory(category);
        return true;
    }

    // Search activities by title
    public ArrayList<Activity> searchActivities(String keyword) {
        ArrayList<Activity> results = new ArrayList<Activity>();

        for (Activity activity : activities) {
            if (activity.getTitle().toLowerCase()
                    .contains(keyword.toLowerCase())) {
                results.add(activity);
            }
        }

        return results;
    }

    // Filter activities by category
    public ArrayList<Activity> filterByCategory(String category) {
        ArrayList<Activity> results = new ArrayList<Activity>();

        for (Activity activity : activities) {
            if (activity.getCategory().equalsIgnoreCase(category)) {
                results.add(activity);
            }
        }

        return results;
    }

    // Filter by completion status
    public ArrayList<Activity> filterByCompletion(boolean completed) {
        ArrayList<Activity> results = new ArrayList<Activity>();

        for (Activity activity : activities) {
            if (activity.isCompleted() == completed) {
                results.add(activity);
            }
        }

        return results;
    }

}
