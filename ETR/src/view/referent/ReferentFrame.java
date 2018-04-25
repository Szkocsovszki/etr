package view.referent;

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

public class ReferentFrame extends JFrame {
	private static final long serialVersionUID = 1873297524242533738L;
	private ETRController controller;
	private ETRGUI gui;
	private JPanel workingPanel;
	private Account currentAccount;

	public ReferentFrame(ETRController controller, Account account) {
		this.controller = controller;
		this.currentAccount = account;
		// setTitle();
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

		JButton createAccount = new JButton(Labels.CREATE_ACCOUNT);
		JButton modifyAccount = new JButton(Labels.MODIFY_ACCOUNT);
		JButton deleteAccount = new JButton(Labels.DELETE_ACCOUNT);
		JButton changePassword = new JButton(Labels.CHANGE_PASSWORD);
		JButton signOut = new JButton(Labels.SIGN_OUT);

		createAccount.addActionListener(e -> {
			workingPanel.removeAll();
			workingPanel.add(new CreateAccount(controller));
			revalidate();
			repaint();
		});

		modifyAccount.addActionListener(e -> {
			workingPanel.removeAll();
			workingPanel.add(new ModifyAccount(controller));
			revalidate();
			repaint();
		});

		deleteAccount.addActionListener(e -> {
			workingPanel.removeAll();
			workingPanel.add(new DeleteAccount(controller));
			revalidate();
			repaint();
		});
		
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

		menuBar.add(createAccount);
		menuBar.add(modifyAccount);
		menuBar.add(deleteAccount);

		signOut.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		menuBar.add(Box.createHorizontalGlue());
		menuBar.add(changePassword);
		menuBar.add(signOut);

		return menuBar;
	}
}
