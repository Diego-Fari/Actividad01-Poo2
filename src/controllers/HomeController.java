package controllers;

import javax.swing.*;
import core.Controller;
import models.*;
import views.*;

public class HomeController extends Controller {
	private HomeView homeView;
	private EventFormView eventFormView;
	private EventListView eventListView;
	private GuestFormView guestFormView;
	private EventModel eventModel;

	@Override
	public void run() {
		eventModel = new EventModel();

		homeView = new HomeView(this, mainFrame);
		eventFormView = new EventFormView(this, mainFrame);
		eventListView = new EventListView(this, mainFrame);
		guestFormView = new GuestFormView(this, mainFrame);

		eventModel.attach(eventListView);
		eventModel.attach(guestFormView);

		addView("HomeView", homeView);
		addView("EventFormView", eventFormView);
		addView("EventListView", eventListView);
		addView("GuestFormView", guestFormView);

		loadView("HomeView");
		mainFrame.setVisible(true);
	}

	public void showHome() { loadView("HomeView"); }
	public void showEventForm() { loadView("EventFormView"); }

	public void showEventList() {
		eventModel.notifyViews();
		loadView("EventListView");
	}

	public void showGuestForm() {
		eventModel.notifyViews();
		loadView("GuestFormView");
	}

	public void registerEvent(String name, String date, String location) {
		if (name.isEmpty() || date.isEmpty() || location.isEmpty()) {
			JOptionPane.showMessageDialog(mainFrame, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		eventModel.addEvent(new Event(name, date, location));
		JOptionPane.showMessageDialog(mainFrame, "Evento registrado exitosamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
		showHome();
	}

	public void deleteEvent(int eventId) {
		int confirm = JOptionPane.showConfirmDialog(mainFrame, "Seguro de eliminar este evento?", "Confirmar", JOptionPane.YES_NO_OPTION);
		if (confirm == JOptionPane.YES_OPTION) {
			eventModel.removeEvent(eventId);
		}
	}

	public void registerGuest(int eventId, String name, String email, String phone) {
		if (name.isEmpty() || email.isEmpty()) {
			JOptionPane.showMessageDialog(mainFrame, "Nombre y email son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		eventModel.addGuestToEvent(eventId, new Guest(name, email, phone));
		JOptionPane.showMessageDialog(mainFrame, "Invitado registrado exitosamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
		showHome();
	}

	public EventModel getEventModel() { return eventModel; }
}