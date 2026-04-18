package models;

public class Event {
    private static int counter = 0;
    private int id;
    private String description;
    private String email;
    private String date;
    private String frequency;
    private boolean alarm;

    public Event(String description, String email, String date, String frequency, boolean alarm) {
        this.id = ++counter;
        this.description = description;
        this.email = email;
        this.date = date;
        this.frequency = frequency;
        this.alarm = alarm;
    }

    public int getId() { return id; }
    public String getDescription() { return description; }
    public String getEmail() { return email; }
    public String getDate() { return date; }
    public String getFrequency() { return frequency; }
    public boolean isAlarm() { return alarm; }
    public String getAlarmText() { return alarm ? "ON" : "OFF"; }

    @Override
    public String toString() {
        return id + " - " + description + " (" + date + ")";
    }
}