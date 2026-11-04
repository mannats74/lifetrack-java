public class Activity {

    private int id;
    private String title;
    private String category;
    private boolean completed;

    public Activity(int id, String title, String category) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.completed = false;
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

    @Override
    public String toString() {
        return "ID: " + id +
                " | Title: " + title +
                " | Category: " + category +
                " | Completed: " + completed;
    }

}
