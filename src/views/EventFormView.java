package views;

import java.awt.*;
import javax.swing.*;
import controllers.HomeController;
import core.Model;
import core.View;

@SuppressWarnings("serial")
public class EventFormView extends JPanel implements View {
    private HomeController homeController;
    private JTextField txtName;
    private JTextField txtDate;
    private JTextField txtLocation;

    public EventFormView(HomeController homeController, JFrame mainFrame) {
        this.homeController = homeController;
        setupUI();
    }

    private void setupUI() {
        setLayout(new BorderLayout());
        JLabel title = new JLabel("Registrar Nuevo Evento", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        form.add(new JLabel("Nombre del Evento:"), gbc);
        gbc.gridx = 1;
        txtName = new JTextField(20);
        form.add(txtName, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        form.add(new JLabel("Fecha (DD/MM/AAAA):"), gbc);
        gbc.gridx = 1;
        txtDate = new JTextField(20);
        form.add(txtDate, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        form.add(new JLabel("Ubicacion:"), gbc);
        gbc.gridx = 1;
        txtLocation = new JTextField(20);
        form.add(txtLocation, gbc);

        add(form, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout());
        btnPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0));
        JButton btnSave = new JButton("Guardar Evento");
        JButton btnCancel = new JButton("Cancelar");

        btnSave.addActionListener(e -> {
            homeController.registerEvent(txtName.getText().trim(), txtDate.getText().trim(), txtLocation.getText().trim());
            txtName.setText(""); txtDate.setText(""); txtLocation.setText("");
        });
        btnCancel.addActionListener(e -> {
            txtName.setText(""); txtDate.setText(""); txtLocation.setText("");
            homeController.showHome();
        });

        btnPanel.add(btnSave);
        btnPanel.add(btnCancel);
        add(btnPanel, BorderLayout.SOUTH);
    }

    @Override
    public void update(Model model, Object data) {}
}