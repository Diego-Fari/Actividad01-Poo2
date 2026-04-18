package models;

import java.util.ArrayList;
import java.util.List;

public class Event {
    private static int counter = 0;
    private int id;
    private String name;
    private String date;
    private String location;
    private List<Guest> guests;

    public Event(String name, String date, String location) {
        this.id = ++counter;
        this.name = name;
        this.date = date;
        this.location = location;
        this.guests = new ArrayList<>();
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDate() { return date; }
    public String getLocation() { return location; }
    public List<Guest> getGuests() { return guests; }
    public void addGuest(Guest guest) { guests.add(guest); }

    @Override
    public String toString() {
        return id + " - " + name + " (" + date + ")";
    }
}