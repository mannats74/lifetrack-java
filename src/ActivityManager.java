
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

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

    public void loadActivities(ArrayList<Activity> savedActivities) {
        activities.clear();
        activities.addAll(savedActivities);

        nextId = 1;

        for (Activity activity : activities) {
            if (activity.getId() >= nextId) {
                nextId = activity.getId() + 1;
            }
        }
    }

    // Statistics methods

    public int getTotalActivities() {
        return activities.size();
    }

    public int getCompletedCount() {
        return filterByCompletion(true).size();
    }

    public int getIncompleteCount() {
        return filterByCompletion(false).size();
    }

    public double getCompletionRate() {
        if (activities.isEmpty()) {
            return 0.0;
        }

        return (getCompletedCount() * 100.0) / getTotalActivities();
    }

    public Map<String, Integer> getCategoryCounts() {
        Map<String, Integer> counts = new LinkedHashMap<String, Integer>();

        for (Activity activity : activities) {
            String category = activity.getCategory();

            if (counts.containsKey(category)) {
                counts.put(category, counts.get(category) + 1);
            } else {
                counts.put(category, 1);
            }
        }

        return counts;
    }

    public String getMostActiveCategory() {
        Map<String, Integer> counts = getCategoryCounts();

        String mostActive = "None";
        int highestCount = 0;

        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            if (entry.getValue() > highestCount) {
                highestCount = entry.getValue();
                mostActive = entry.getKey();
            }
        }

        return mostActive;
    }
}
