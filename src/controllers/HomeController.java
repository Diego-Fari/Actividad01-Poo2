package controllers;

import javax.swing.*;
import java.util.List;
import core.Controller;
import models.*;
import views.*;

public class HomeController extends Controller {
	private HomeView homeView;
	private EventModel eventModel;

	@Override
	public void run() {
		eventModel = new EventModel();

		homeView = new HomeView(this, mainFrame);
		eventModel.attach(homeView);

		addView("HomeView", homeView);
		loadView("HomeView");
		mainFrame.setVisible(true);
	}

	public void registerEvent(String description, String email, String date, String frequency, boolean alarm) {
		if (description.isEmpty() || email.isEmpty() || date.isEmpty()) {
			JOptionPane.showMessageDialog(mainFrame, "Los campos descripcion, email y fecha son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		eventModel.addEvent(new Event(description, email, date, frequency, alarm));
		JOptionPane.showMessageDialog(mainFrame, "Evento registrado exitosamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
	}

	public void removeEvents(List<Integer> eventIds) {
		if (eventIds.isEmpty()) {
			JOptionPane.showMessageDialog(mainFrame, "Seleccione al menos un evento.", "Aviso", JOptionPane.WARNING_MESSAGE);
			return;
		}
		int confirm = JOptionPane.showConfirmDialog(mainFrame, "Seguro de eliminar los eventos seleccionados?", "Confirmar", JOptionPane.YES_NO_OPTION);
		if (confirm == JOptionPane.YES_OPTION) {
			eventModel.removeEvents(eventIds);
		}
	}

	public void registerGuest(String name, String phone, String gender, String birthDate, String address, boolean acceptedTerms) {
		if (name.isEmpty() || phone.isEmpty()) {
			JOptionPane.showMessageDialog(mainFrame, "Nombre y celular son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (!acceptedTerms) {
			JOptionPane.showMessageDialog(mainFrame, "Debe aceptar los terminos y condiciones.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		eventModel.addGuest(new Guest(name, phone, gender, birthDate, address, acceptedTerms));
		JOptionPane.showMessageDialog(mainFrame, "Invitado registrado exitosamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
	}

	public List<Event> searchEvents(String keyword) {
		return eventModel.searchEvents(keyword);
	}

	public EventModel getEventModel() { return eventModel; }
}