package view.professor;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.GridBagLayout;
import java.awt.Toolkit;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import controller.ETRController;
import dao.model.Account;
import view.ChangePassword;
import view.ETRGUI;
import view.Labels;

public class ProfessorFrame extends JFrame {
	private static final long serialVersionUID = -5076258023858992696L;
	private ETRController controller;
	private Account currentAccount;
	private JPanel workingPanel;
	private ETRGUI gui;

	public ProfessorFrame(ETRController controller, Account account) {
		this.controller = controller;
		this.currentAccount = account;
		setTitle(Labels.ETR);
		setSize(
				(int)Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 752,
				(int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 400
		);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setJMenuBar(createMenuBar());

		workingPanel = new JPanel();
		workingPanel.setLayout(new GridBagLayout());
		add(workingPanel, BorderLayout.CENTER);

		setVisible(true);
	}

	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JButton changePassword = new JButton(Labels.CHANGE_PASSWORD);
		JButton signOut = new JButton(Labels.SIGN_OUT);
		
		changePassword.addActionListener(e -> {
			workingPanel.removeAll();
			workingPanel.add(new ChangePassword(controller, currentAccount));
			revalidate();
			repaint();
		});

		signOut.addActionListener(e -> {
			dispose();
			gui = new ETRGUI(controller);
			gui.startGUI();
		});

		signOut.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		menuBar.add(Box.createHorizontalGlue());
		menuBar.add(changePassword);
		menuBar.add(signOut);
		
		return menuBar;
	}

}
