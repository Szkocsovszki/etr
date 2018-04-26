package view.student;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.GridBagLayout;
import java.awt.Toolkit;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import controller.ETRController;
import dao.model.Account;
import view.ChangePassword;
import view.ETRGUI;
import view.Labels;

public class StudentFrame extends JFrame {
	private static final long serialVersionUID = -5076258023858992696L;
	private ETRController controller;
	private Account currentAccount;
	private JPanel workingPanel;
	private ETRGUI gui;

	public StudentFrame(ETRController controller, Account account) {
		this.controller = controller;
		this.currentAccount = account;
		setTitle(Labels.ETR + " - " + currentAccount.getEha());
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
		JMenu courseMenu = new JMenu(Labels.COURSES);
		JMenu examMenu = new JMenu(Labels.EXAMS);
		JButton changePassword = new JButton(Labels.CHANGE_PASSWORD);
		JButton signOut = new JButton(Labels.SIGN_OUT);
		
		JMenuItem listCourses = new JMenuItem(Labels.LIST_COURSES);
		JMenuItem listRegistratedCourses = new JMenuItem(Labels.LIST_REGISTRATED_COURSES);
		JMenuItem timetable = new JMenuItem(Labels.TIMETABLE);
		
		JMenuItem listExams = new JMenuItem(Labels.LIST_EXAMS);
		JMenuItem listRegistratedExams = new JMenuItem(Labels.LIST_REGISTRATED_EXAMS);
		JMenuItem examFee = new JMenuItem(Labels.EXAM_FEE);
		
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
		
		courseMenu.add(listCourses);
		courseMenu.add(listRegistratedCourses);
		courseMenu.add(timetable);
		
		examMenu.add(listExams);
		examMenu.add(listRegistratedExams);
		examMenu.add(examFee);
		
		menuBar.add(courseMenu);
		menuBar.add(examMenu);
		signOut.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		menuBar.add(Box.createHorizontalGlue());
		menuBar.add(changePassword);
		menuBar.add(signOut);
		return menuBar;
	}

}
