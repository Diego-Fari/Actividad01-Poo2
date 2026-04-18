package views;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import controllers.HomeController;
import core.Model;
import core.View;
import models.Event;

@SuppressWarnings("serial")
public class GuestFormView extends JPanel implements View {
    private HomeController homeController;
    private JComboBox<Event> cmbEvents;
    private JTextField txtName;
    private JTextField txtEmail;
    private JTextField txtPhone;
    private DefaultComboBoxModel<Event> comboModel;

    public GuestFormView(HomeController homeController, JFrame mainFrame) {
        this.homeController = homeController;
        setupUI();
    }

    private void setupUI() {
        setLayout(new BorderLayout());
        JLabel title = new JLabel("Registrar Invitado", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        form.add(new JLabel("Evento:"), gbc);
        gbc.gridx = 1;
        comboModel = new DefaultComboBoxModel<>();
        cmbEvents = new JComboBox<>(comboModel);
        cmbEvents.setPreferredSize(new Dimension(250, 30));
        form.add(cmbEvents, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        form.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        txtName = new JTextField(20);
        form.add(txtName, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        form.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        txtEmail = new JTextField(20);
        form.add(txtEmail, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        form.add(new JLabel("Telefono:"), gbc);
        gbc.gridx = 1;
        txtPhone = new JTextField(20);
        form.add(txtPhone, gbc);

        add(form, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout());
        btnPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0));
        JButton btnSave = new JButton("Registrar Invitado");
        JButton btnCancel = new JButton("Cancelar");

        btnSave.addActionListener(e -> {
            Event selected = (Event) cmbEvents.getSelectedItem();
            if (selected == null) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un evento.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            homeController.registerGuest(selected.getId(), txtName.getText().trim(), txtEmail.getText().trim(), txtPhone.getText().trim());
            txtName.setText(""); txtEmail.setText(""); txtPhone.setText("");
        });
        btnCancel.addActionListener(e -> {
            txtName.setText(""); txtEmail.setText(""); txtPhone.setText("");
            homeController.showHome();
        });

        btnPanel.add(btnSave);
        btnPanel.add(btnCancel);
        add(btnPanel, BorderLayout.SOUTH);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void update(Model model, Object data) {
        comboModel.removeAllElements();
        List<Event> events = (List<Event>) data;
        for (Event event : events) comboModel.addElement(event);
    }
}