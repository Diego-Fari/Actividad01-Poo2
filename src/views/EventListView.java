package views;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import controllers.HomeController;
import core.Model;
import core.View;
import models.Event;
import models.Guest;

@SuppressWarnings("serial")
public class EventListView extends JPanel implements View {
    private HomeController homeController;
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextArea txtGuests;

    public EventListView(HomeController homeController, JFrame mainFrame) {
        this.homeController = homeController;
        setupUI();
    }

    private void setupUI() {
        setLayout(new BorderLayout());
        JLabel title = new JLabel("Lista de Eventos", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        String[] columns = {"ID", "Nombre", "Fecha", "Ubicacion", "# Invitados"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        table.setRowHeight(25);

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) showGuestsForSelected();
        });

        JScrollPane scrollPane = new JScrollPane(table);

        txtGuests = new JTextArea(5, 30);
        txtGuests.setEditable(false);
        JScrollPane guestScroll = new JScrollPane(txtGuests);
        guestScroll.setBorder(BorderFactory.createTitledBorder("Invitados del Evento"));

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollPane, guestScroll);
        split.setResizeWeight(0.6);
        add(split, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout());
        btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        JButton btnDelete = new JButton("Eliminar Evento Seleccionado");
        JButton btnBack = new JButton("Volver al Inicio");

        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int eventId = (int) tableModel.getValueAt(row, 0);
                homeController.deleteEvent(eventId);
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un evento.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });
        btnBack.addActionListener(e -> homeController.showHome());

        btnPanel.add(btnDelete);
        btnPanel.add(btnBack);
        add(btnPanel, BorderLayout.SOUTH);
    }

    private void showGuestsForSelected() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            int eventId = (int) tableModel.getValueAt(row, 0);
            Event event = homeController.getEventModel().getEventById(eventId);
            if (event != null) {
                StringBuilder sb = new StringBuilder();
                List<Guest> guests = event.getGuests();
                if (guests.isEmpty()) sb.append("No hay invitados registrados.");
                else for (Guest g : guests)
                    sb.append("- ").append(g.getName()).append(" | ").append(g.getEmail()).append(" | ").append(g.getPhone()).append("\n");
                txtGuests.setText(sb.toString());
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void update(Model model, Object data) {
        tableModel.setRowCount(0);
        List<Event> events = (List<Event>) data;
        for (Event event : events) {
            tableModel.addRow(new Object[]{event.getId(), event.getName(), event.getDate(), event.getLocation(), event.getGuests().size()});
        }
        txtGuests.setText("");
    }
}