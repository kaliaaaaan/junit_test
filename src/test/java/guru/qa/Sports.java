package guru.qa;

public enum Sports {
    FOOTBALL ("football"),
    HOCKEY ("hockey");

    private final String sport;
    Sports(String value) {
        this.sport = value;
    }
    public String getSport() {
        return sport;
    }
}