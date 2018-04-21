package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import controller.ETRController;
import dao.model.Account;
import dao.model.Professor;
import dao.model.Referent;
import dao.model.Student;
import view.referent.ReferentFrame;

public class ETRGUI extends JFrame {

	private static final long serialVersionUID = -6123017900301369195L;
	private ETRController controller;

	public ETRGUI(ETRController controller) {
		this.controller = controller;
	}

	public ETRController getController() {
		return controller;
	}

	public void startGUI() {
		SwingUtilities.invokeLater(() -> {
			createStartingPage();
		});
	}

	private void createStartingPage() {
		setTitle(Labels.STARTING_PAGE);
		// setExtendedState(JFrame.MAXIMIZED_BOTH);
		// setLocationRelativeTo(null);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(2 * dim.width / 5 - 2 * getSize().width / 5, dim.height / 3 - getSize().height / 3);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		panel.add(createSignInPanel());

		add(panel, BorderLayout.CENTER);

		pack();
		setVisible(true);
	}

	/*
	 * private JPanel createRegistrationPanel() { JPanel panel = new JPanel();
	 * panel.setLayout(new GridLayout(2, 1));
	 * 
	 * JPanel dataPanel = new JPanel(); dataPanel.setLayout(new GridLayout(3, 2));
	 * JLabel userNameLabel = new JLabel(Labels.USER_NAME); JTextField userName =
	 * new JTextField(20); JLabel passwordLabel = new JLabel(Labels.PASSWORD);
	 * JPasswordField password = new JPasswordField(); JLabel confirmPasswordLabel =
	 * new JLabel(Labels.CONFIRM_PASSWORD); JPasswordField confirmPassword = new
	 * JPasswordField(); dataPanel.add(userNameLabel); dataPanel.add(userName);
	 * dataPanel.add(passwordLabel); dataPanel.add(password);
	 * dataPanel.add(confirmPasswordLabel); dataPanel.add(confirmPassword);
	 * 
	 * JPanel buttonPanel = new JPanel(); buttonPanel.setLayout(new
	 * FlowLayout(FlowLayout.CENTER)); JButton registrationButton = new
	 * JButton(Labels.REGISTRATION); buttonPanel.add(registrationButton);
	 * 
	 * panel.add(dataPanel); panel.add(buttonPanel);
	 * 
	 * return panel; }
	 */

	@SuppressWarnings("deprecation")
	private JPanel createSignInPanel() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panel.setLayout(new GridLayout(2, 1));

		JPanel dataPanel = new JPanel();
		dataPanel.setLayout(new GridLayout(2, 2));
		JLabel userNameLabel = new JLabel(Labels.USER_NAME);
		JTextField userName = new JTextField(10);
		JLabel passwordLabel = new JLabel(Labels.PASSWORD);
		JPasswordField password = new JPasswordField();
		dataPanel.add(userNameLabel);
		dataPanel.add(userName);
		dataPanel.add(passwordLabel);
		dataPanel.add(password);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		JButton logInButton = new JButton(Labels.LOG_IN);
		buttonPanel.add(logInButton);

		logInButton.addActionListener(e -> {
			if (userName.getText().isEmpty()) {
				JOptionPane.showMessageDialog(
						  this,
						  Labels.MISSING_USERNAME,
						  Labels.ERROR,
						  JOptionPane.ERROR_MESSAGE);
			} else if (password.getText().isEmpty()) {
				JOptionPane.showMessageDialog(
						  this,
						  Labels.MISSING_PASSWORD,
						  Labels.ERROR,
						  JOptionPane.ERROR_MESSAGE);
			} else {
				dispose();
				new ReferentFrame(controller);
				try {
					Account acc = controller.getAccount(userName.getText(), password.getText());
					if(acc instanceof Referent) {
					}
					if(acc instanceof Professor) {
					}
					if(acc instanceof Student) {
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//System.out.println(userName.getText() + " " + password.getText());
			}

		});

		panel.add(dataPanel);
		panel.add(buttonPanel);

		return panel;
	}
}
