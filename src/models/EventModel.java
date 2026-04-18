package models;

import java.util.ArrayList;
import java.util.List;
import core.Model;
import core.View;

public class EventModel implements Model {
    private List<Event> events;
    private List<View> views;

    public EventModel() {
        this.events = new ArrayList<>();
        this.views = new ArrayList<>();
    }

    public void addEvent(Event event) {
        events.add(event);
        notifyViews();
    }

    public void removeEvent(int eventId) {
        events.removeIf(e -> e.getId() == eventId);
        notifyViews();
    }

    public List<Event> getEvents() {
        return new ArrayList<>(events);
    }

    public Event getEventById(int id) {
        return events.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void addGuestToEvent(int eventId, Guest guest) {
        Event event = getEventById(eventId);
        if (event != null) {
            event.addGuest(guest);
            notifyViews();
        }
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