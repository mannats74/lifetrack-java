
import java.util.ArrayList;

public class ActivityManager {

    private ArrayList<Activity> activities;
    private int nextId;

    public ActivityManager() {
        activities = new ArrayList<Activity>();
        nextId = 1;
    }

    public Activity addActivity(String title, String category) {
        Activity activity = new Activity(nextId, title, category);
        activities.add(activity);
        nextId++;

        return activity;
    }

    public ArrayList<Activity> getActivities() {
        return new ArrayList<Activity>(activities);
    }

    public Activity findActivityById(int id) {
        for (Activity activity : activities) {
            if (activity.getId() == id) {
                return activity;
            }
        }

        return null;
    }

    public boolean completeActivity(int id) {
        Activity activity = findActivityById(id);

        if (activity == null) {
            return false;
        }

        activity.markCompleted();
        return true;
    }

    public boolean deleteActivity(int id) {
        Activity activity = findActivityById(id);

        if (activity == null) {
            return false;
        }

        activities.remove(activity);
        return true;
    }

    public boolean updateActivity(int id, String title, String category) {
        Activity activity = findActivityById(id);

        if (activity == null) {
            return false;
        }

        activity.setTitle(title);
        activity.setCategory(category);
        return true;
    }

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

    public ArrayList<Activity> filterByCategory(String category) {
        ArrayList<Activity> results = new ArrayList<Activity>();

        for (Activity activity : activities) {
            if (activity.getCategory().equalsIgnoreCase(category)) {
                results.add(activity);
            }
        }

        return results;
    }

    public ArrayList<Activity> filterByCompletion(boolean completed) {
        ArrayList<Activity> results = new ArrayList<Activity>();

        for (Activity activity : activities) {
            if (activity.isCompleted() == completed) {
                results.add(activity);
            }
        }

        return results;
    }

    // Restore activities from saved data
    public void loadActivities(ArrayList<Activity> savedActivities) {
        activities.clear();
        activities.addAll(savedActivities);

        // Ensure new activities receive unique IDs
        nextId = 1;

        for (Activity activity : activities) {
            if (activity.getId() >= nextId) {
                nextId = activity.getId() + 1;
            }
        }
    }
}
