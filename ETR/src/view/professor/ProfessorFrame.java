package view.professor;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
		JButton writeMark = new JButton(Labels.WRITE_MARK);
		JButton changePassword = new JButton(Labels.CHANGE_PASSWORD);
		JButton signOut = new JButton(Labels.SIGN_OUT);
		
		writeMark.addActionListener(e -> {
			workingPanel.removeAll();
			
			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(4, 2));
			
			JLabel ehaLabel = new JLabel("Hallgató EHA: ");
			JLabel courseLabel = new JLabel("Kurzuskód: ");
			JLabel markLabel = new JLabel("Osztályzat: ");
			JTextField ehaTexBox = new JTextField();
			JTextField courseTexBox = new JTextField();
			JTextField markTexBox = new JTextField();
			JButton button = new JButton("Felvitel");
			
			panel.add(ehaLabel);
			panel.add(ehaTexBox);
			panel.add(courseLabel);
			panel.add(courseTexBox);
			panel.add(markLabel);
			panel.add(markTexBox);
			panel.add(button);
			
			/*button.addActionListener(e -> {
				
			});*/
			
			workingPanel.add(panel);
			
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
		
		menuBar.add(writeMark);
		signOut.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		menuBar.add(Box.createHorizontalGlue());
		menuBar.add(changePassword);
		menuBar.add(signOut);
		
		return menuBar;
	}

}
