package view;

import java.awt.ComponentOrientation;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import controller.ETRController;

public class ReferentFrame extends JFrame {
	private static final long serialVersionUID = 1873297524242533738L;
	private ETRController controller;
	private ETRGUI gui;
	private JPanel panel;

	public ReferentFrame(ETRController controller) {
		this.controller = controller;
		// setTitle();
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setJMenuBar(createMenuBar());

		setVisible(true);
	}

	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu(Labels.ACCOUNTS);
		JButton signOut = new JButton(Labels.SIGN_OUT);

		JMenuItem createAccount = new JMenuItem(Labels.CREATE_ACCOUNT);
		JMenuItem modifyAccount = new JMenuItem(Labels.MODIFY_ACCOUNT);
		JMenuItem deleteAccount = new JMenuItem(Labels.DELETE_ACCOUNT);

		createAccount.addActionListener(e -> {
			panel = new JPanel();
			add(panel);
		});

		modifyAccount.addActionListener(e -> {
			panel = new JPanel();
			add(panel);
		});

		deleteAccount.addActionListener(e -> {
			panel = new JPanel();
			add(panel);
		});
		
		signOut.addActionListener( e -> {
			dispose();
			gui = new ETRGUI(controller);
			gui.startGUI();
		});

		menu.add(createAccount);
		menu.add(modifyAccount);
		menu.add(deleteAccount);

		menuBar.add(menu);
		signOut.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		menuBar.add(Box.createHorizontalGlue());
		menuBar.add(signOut);

		return menuBar;
	}
}
