
public class Activity {

    private int id;
    private String title;
    private String category;
    private boolean completed;

    public Activity(int id, String title, String category) {
        this(id, title, category, false);
    }

    // Constructor used when loading saved activities
    public Activity(int id, String title, String category, boolean completed) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void markCompleted() {
        this.completed = true;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "ID: " + id +
                " | Title: " + title +
                " | Category: " + category +
                " | Completed: " + completed;
    }
}
