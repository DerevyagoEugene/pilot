package api.jira;

public enum JiraStatus {

    BACKLOG("Backlog", "11", 2),
    IN_PROGRESS("In Progress", "31", 4),
    DONE("Done", "41", 3);

    private final String name;
    private final String id;
    private final int statusCategory;

    JiraStatus(String name, String id, int statusCategory) {
        this.name = name;
        this.id = id;
        this.statusCategory = statusCategory;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public int getStatusCategory() {
        return statusCategory;
    }
}
