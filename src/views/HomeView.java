package views;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import controllers.HomeController;
import core.Model;
import core.View;
import models.Event;

@SuppressWarnings("serial")
public class HomeView extends JPanel implements View {
	private HomeController homeController;
	private JFrame mainFrame;
	private JTabbedPane tabbedPane;

	// New Event tab
	private JTextField txtDescription;
	private JTextField txtEmail;
	private JTextField txtDate;
	private ButtonGroup freqGroup;
	private JRadioButton rbDaily;
	private JRadioButton rbWeekly;
	private JRadioButton rbMonthly;
	private JCheckBox chkAlarm;

	// Events tab
	private DefaultTableModel eventsTableModel;
	private JTable eventsTable;

	// Remove Event tab
	private DefaultTableModel removeTableModel;
	private JTable removeTable;

	// Guest tab
	private JTextField txtGuestName;
	private JTextField txtGuestPhone;
	private ButtonGroup genderGroup;
	private JRadioButton rbMale;
	private JRadioButton rbFemale;
	private JComboBox<Integer> cmbDay;
	private JComboBox<String> cmbMonth;
	private JComboBox<Integer> cmbYear;
	private JTextField txtAddress;
	private JCheckBox chkTerms;

	// Search tab
	private JTextField txtSearch;
	private DefaultTableModel searchTableModel;
	private JTable searchTable;

	public HomeView(HomeController homeController, JFrame mainFrame) {
		this.homeController = homeController;
		this.mainFrame = mainFrame;
		setupMainFrame();
		setupUI();
	}

	private void setupMainFrame() {
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setBounds(100, 100, 700, 450);
		mainFrame.setMinimumSize(new Dimension(700, 450));
		mainFrame.setTitle("Sistema de Gestion de Eventos");
	}

	private void setupUI() {
		setLayout(new BorderLayout());
		tabbedPane = new JTabbedPane();

		tabbedPane.addTab("New event", createNewEventPanel());
		tabbedPane.addTab("Events", createEventsPanel());
		tabbedPane.addTab("Remove Event", createRemovePanel());
		tabbedPane.addTab("Registrar invitado", createGuestPanel());
		tabbedPane.addTab("Buscar evento", createSearchPanel());

		add(tabbedPane, BorderLayout.CENTER);
	}

	// ==================== NEW EVENT TAB ====================
	private JPanel createNewEventPanel() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 15, 10, 15);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.WEST;

		Font labelFont = new Font("Arial", Font.BOLD, 12);

		// Event description
		gbc.gridx = 0; gbc.gridy = 0;
		JLabel lblDesc = new JLabel("Event description");
		lblDesc.setFont(labelFont);
		panel.add(lblDesc, gbc);
		gbc.gridx = 1;
		txtDescription = new JTextField(25);
		panel.add(txtDescription, gbc);

		// Forward e-mail
		gbc.gridx = 0; gbc.gridy = 1;
		JLabel lblEmail = new JLabel("Forward e-mail");
		lblEmail.setFont(labelFont);
		panel.add(lblEmail, gbc);
		gbc.gridx = 1;
		txtEmail = new JTextField(25);
		panel.add(txtEmail, gbc);

		// Date
		gbc.gridx = 0; gbc.gridy = 2;
		JLabel lblDate = new JLabel("Date");
		lblDate.setFont(labelFont);
		panel.add(lblDate, gbc);
		gbc.gridx = 1;
		txtDate = new JTextField(10);
		panel.add(txtDate, gbc);

		// Frequency
		gbc.gridx = 0; gbc.gridy = 3;
		JLabel lblFreq = new JLabel("Frequency");
		lblFreq.setFont(labelFont);
		panel.add(lblFreq, gbc);
		gbc.gridx = 1;
		JPanel freqPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		rbDaily = new JRadioButton("Daily");
		rbWeekly = new JRadioButton("Weekly");
		rbMonthly = new JRadioButton("Monthly");
		rbMonthly.setSelected(true);
		freqGroup = new ButtonGroup();
		freqGroup.add(rbDaily);
		freqGroup.add(rbWeekly);
		freqGroup.add(rbMonthly);
		freqPanel.add(rbDaily);
		freqPanel.add(rbWeekly);
		freqPanel.add(rbMonthly);
		panel.add(freqPanel, gbc);

		// Alarm + Save + Clean
		gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
		JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		chkAlarm = new JCheckBox("Alarm");
		JButton btnSave = new JButton("Save");
		JButton btnClean = new JButton("Clean");

		btnSave.addActionListener(e -> {
			String freq = rbDaily.isSelected() ? "DAILY" : rbWeekly.isSelected() ? "WEEKLY" : "MONTHLY";
			homeController.registerEvent(
					txtDescription.getText().trim(),
					txtEmail.getText().trim(),
					txtDate.getText().trim(),
					freq,
					chkAlarm.isSelected()
			);
			clearNewEventFields();
		});

		btnClean.addActionListener(e -> clearNewEventFields());

		bottomPanel.add(chkAlarm);
		bottomPanel.add(btnSave);
		bottomPanel.add(btnClean);
		panel.add(bottomPanel, gbc);

		return panel;
	}

	private void clearNewEventFields() {
		txtDescription.setText("");
		txtEmail.setText("");
		txtDate.setText("");
		rbMonthly.setSelected(true);
		chkAlarm.setSelected(false);
	}

	// ==================== EVENTS TAB ====================
	private JPanel createEventsPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		String[] columns = {"Date", "Description", "Frequency", "E-mail", "Alarm"};
		eventsTableModel = new DefaultTableModel(columns, 0) {
			@Override
			public boolean isCellEditable(int row, int column) { return false; }
		};
		eventsTable = new JTable(eventsTableModel);
		eventsTable.setRowHeight(22);
		panel.add(new JScrollPane(eventsTable), BorderLayout.CENTER);
		return panel;
	}

	// ==================== REMOVE EVENT TAB ====================
	private JPanel createRemovePanel() {
		JPanel panel = new JPanel(new BorderLayout());
		String[] columns = {"Date", "Description", "Frequency", "E-mail", "Alarm", "Boolean"};
		removeTableModel = new DefaultTableModel(columns, 0) {
			@Override
			public Class<?> getColumnClass(int column) {
				if (column == 5) return Boolean.class;
				return String.class;
			}
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 5;
			}
		};
		removeTable = new JTable(removeTableModel);
		removeTable.setRowHeight(22);
		panel.add(new JScrollPane(removeTable), BorderLayout.CENTER);

		JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
		JButton btnSelectAll = new JButton("Seleccionar Todos");
		JButton btnCancel = new JButton("Cancel");
		JButton btnRemove = new JButton("Remover");

		btnSelectAll.addActionListener(e -> {
			for (int i = 0; i < removeTableModel.getRowCount(); i++) {
				removeTableModel.setValueAt(true, i, 5);
			}
		});

		btnCancel.addActionListener(e -> {
			for (int i = 0; i < removeTableModel.getRowCount(); i++) {
				removeTableModel.setValueAt(false, i, 5);
			}
		});

		btnRemove.addActionListener(e -> {
			List<Integer> idsToRemove = new ArrayList<>();
			List<Event> events = homeController.getEventModel().getEvents();
			for (int i = 0; i < removeTableModel.getRowCount(); i++) {
				Boolean selected = (Boolean) removeTableModel.getValueAt(i, 5);
				if (selected != null && selected) {
					idsToRemove.add(events.get(i).getId());
				}
			}
			homeController.removeEvents(idsToRemove);
		});

		bottomPanel.add(btnSelectAll);
		bottomPanel.add(btnCancel);
		bottomPanel.add(btnRemove);
		panel.add(bottomPanel, BorderLayout.SOUTH);
		return panel;
	}

	// ==================== GUEST TAB ====================
	private JPanel createGuestPanel() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 15, 10, 15);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.WEST;

		Font labelFont = new Font("Arial", Font.BOLD, 12);

		// Nombre
		gbc.gridx = 0; gbc.gridy = 0;
		JLabel lblName = new JLabel("Ingrese Nombre:");
		lblName.setFont(labelFont);
		panel.add(lblName, gbc);
		gbc.gridx = 1;
		txtGuestName = new JTextField(20);
		panel.add(txtGuestName, gbc);

		// Celular
		gbc.gridx = 0; gbc.gridy = 1;
		JLabel lblPhone = new JLabel("Ingrese numero celular:");
		lblPhone.setFont(labelFont);
		panel.add(lblPhone, gbc);
		gbc.gridx = 1;
		txtGuestPhone = new JTextField(20);
		panel.add(txtGuestPhone, gbc);

		// Genero
		gbc.gridx = 0; gbc.gridy = 2;
		JLabel lblGender = new JLabel("Genero:");
		lblGender.setFont(labelFont);
		panel.add(lblGender, gbc);
		gbc.gridx = 1;
		JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		rbMale = new JRadioButton("Masculino");
		rbFemale = new JRadioButton("Femenino");
		rbMale.setSelected(true);
		genderGroup = new ButtonGroup();
		genderGroup.add(rbMale);
		genderGroup.add(rbFemale);
		genderPanel.add(rbMale);
		genderPanel.add(rbFemale);
		panel.add(genderPanel, gbc);

		// Fecha de nacimiento
		gbc.gridx = 0; gbc.gridy = 3;
		JLabel lblBirth = new JLabel("Fecha de Nacimiento:");
		lblBirth.setFont(labelFont);
		panel.add(lblBirth, gbc);
		gbc.gridx = 1;
		JPanel birthPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		Integer[] days = new Integer[31];
		for (int i = 0; i < 31; i++) days[i] = i + 1;
		cmbDay = new JComboBox<>(days);
		String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
		cmbMonth = new JComboBox<>(months);
		Integer[] years = new Integer[50];
		for (int i = 0; i < 50; i++) years[i] = 1980 + i;
		cmbYear = new JComboBox<>(years);
		cmbYear.setSelectedItem(1995);
		birthPanel.add(cmbDay);
		birthPanel.add(cmbMonth);
		birthPanel.add(cmbYear);
		panel.add(birthPanel, gbc);

		// Direccion
		gbc.gridx = 0; gbc.gridy = 4;
		JLabel lblAddr = new JLabel("Direccion:");
		lblAddr.setFont(labelFont);
		panel.add(lblAddr, gbc);
		gbc.gridx = 1;
		txtAddress = new JTextField(20);
		panel.add(txtAddress, gbc);

		// Terminos
		gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
		chkTerms = new JCheckBox("Acepta Terminos y Condiciones");
		panel.add(chkTerms, gbc);

		// Boton registrar
		gbc.gridy = 6;
		JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JButton btnRegister = new JButton("Registrar");
		JButton btnClean = new JButton("Limpiar");

		btnRegister.addActionListener(e -> {
			String gender = rbMale.isSelected() ? "Masculino" : "Femenino";
			String birth = cmbDay.getSelectedItem() + "/" + cmbMonth.getSelectedItem() + "/" + cmbYear.getSelectedItem();
			homeController.registerGuest(
					txtGuestName.getText().trim(),
					txtGuestPhone.getText().trim(),
					gender,
					birth,
					txtAddress.getText().trim(),
					chkTerms.isSelected()
			);
			clearGuestFields();
		});

		btnClean.addActionListener(e -> clearGuestFields());

		btnPanel.add(btnRegister);
		btnPanel.add(btnClean);
		panel.add(btnPanel, gbc);

		return panel;
	}

	private void clearGuestFields() {
		txtGuestName.setText("");
		txtGuestPhone.setText("");
		rbMale.setSelected(true);
		cmbDay.setSelectedIndex(0);
		cmbMonth.setSelectedIndex(0);
		cmbYear.setSelectedItem(1995);
		txtAddress.setText("");
		chkTerms.setSelected(false);
	}

	// ==================== SEARCH TAB ====================
	private JPanel createSearchPanel() {
		JPanel panel = new JPanel(new BorderLayout());

		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
		topPanel.add(new JLabel("Buscar:"));
		txtSearch = new JTextField(20);
		JButton btnSearch = new JButton("Buscar");
		topPanel.add(txtSearch);
		topPanel.add(btnSearch);
		panel.add(topPanel, BorderLayout.NORTH);

		String[] columns = {"Date", "Description", "Frequency", "E-mail", "Alarm"};
		searchTableModel = new DefaultTableModel(columns, 0) {
			@Override
			public boolean isCellEditable(int row, int column) { return false; }
		};
		searchTable = new JTable(searchTableModel);
		searchTable.setRowHeight(22);
		panel.add(new JScrollPane(searchTable), BorderLayout.CENTER);

		btnSearch.addActionListener(e -> {
			searchTableModel.setRowCount(0);
			List<Event> results = homeController.searchEvents(txtSearch.getText().trim());
			for (Event ev : results) {
				searchTableModel.addRow(new Object[]{ev.getDate(), ev.getDescription(), ev.getFrequency(), ev.getEmail(), ev.getAlarmText()});
			}
		});

		return panel;
	}

	// ==================== UPDATE (OBSERVER) ====================
	@Override
	@SuppressWarnings("unchecked")
	public void update(Model model, Object data) {
		List<Event> events = (List<Event>) data;

		// Update Events tab
		eventsTableModel.setRowCount(0);
		for (Event ev : events) {
			eventsTableModel.addRow(new Object[]{ev.getDate(), ev.getDescription(), ev.getFrequency(), ev.getEmail(), ev.getAlarmText()});
		}

		// Update Remove tab
		removeTableModel.setRowCount(0);
		for (Event ev : events) {
			removeTableModel.addRow(new Object[]{ev.getDate(), ev.getDescription(), ev.getFrequency(), ev.getEmail(), ev.getAlarmText(), false});
		}
	}
}