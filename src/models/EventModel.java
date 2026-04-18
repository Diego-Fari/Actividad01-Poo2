package models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import core.Model;
import core.View;

public class EventModel implements Model {
    private List<Event> events;
    private List<Guest> guests;
    private List<View> views;

    public EventModel() {
        this.events = new ArrayList<>();
        this.guests = new ArrayList<>();
        this.views = new ArrayList<>();
    }

    public void addEvent(Event event) {
        events.add(event);
        notifyViews();
    }

    public void removeEvents(List<Integer> eventIds) {
        events.removeIf(e -> eventIds.contains(e.getId()));
        notifyViews();
    }

    public List<Event> getEvents() {
        return new ArrayList<>(events);
    }

    public List<Event> searchEvents(String keyword) {
        String lower = keyword.toLowerCase();
        return events.stream()
                .filter(e -> e.getDescription().toLowerCase().contains(lower)
                        || e.getDate().contains(lower)
                        || e.getEmail().toLowerCase().contains(lower))
                .collect(Collectors.toList());
    }

    public void addGuest(Guest guest) {
        guests.add(guest);
        notifyViews();
    }

    public List<Guest> getGuests() {
        return new ArrayList<>(guests);
    }

    @Override
    public void attach(View view) { views.add(view); }

    @Override
    public void detach(View view) { views.remove(view); }

    @Override
    public void notifyViews() {
        for (View view : views) {
            view.update(this, events);
        }
    }
}