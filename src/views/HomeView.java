package views;

import java.awt.*;
import javax.swing.*;
import controllers.HomeController;
import core.Model;
import core.View;

@SuppressWarnings("serial")
public class HomeView extends JPanel implements View {
	private HomeController homeController;
	private JFrame mainFrame;

	public HomeView(HomeController homeController, JFrame mainFrame) {
		this.homeController = homeController;
		this.mainFrame = mainFrame;
		setupMainFrame();
		setupUI();
	}

	private void setupMainFrame() {
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setBounds(100, 100, 800, 500);
		mainFrame.setMinimumSize(new Dimension(800, 500));
		mainFrame.setTitle("Sistema de Gestion de Eventos");
	}

	private void setupUI() {
		setLayout(new BorderLayout());
		JLabel title = new JLabel("Sistema de Gestion de Eventos", SwingConstants.CENTER);
		title.setFont(new Font("Arial", Font.BOLD, 24));
		title.setBorder(BorderFactory.createEmptyBorder(40, 0, 20, 0));
		add(title, BorderLayout.NORTH);

		JPanel btnPanel = new JPanel(new GridLayout(4, 1, 10, 10));
		btnPanel.setBorder(BorderFactory.createEmptyBorder(20, 200, 80, 200));

		JButton btnNew = new JButton("Registrar Nuevo Evento");
		JButton btnList = new JButton("Listar Eventos");
		JButton btnDelete = new JButton("Eliminar Evento");
		JButton btnGuest = new JButton("Registrar Invitado");