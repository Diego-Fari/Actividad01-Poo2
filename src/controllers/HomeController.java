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
		eventModel.not