package view.professor;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import controller.ETRController;
import dao.model.Account;

public class ProfessorFrame extends JFrame {
	private static final long serialVersionUID = -5076258023858992696L;
	private ETRController controller;
	private Account currentAccount;
	private JPanel workingPanel;

	public ProfessorFrame(ETRController controller, Account account) {
		this.controller = controller;
		this.currentAccount = account;
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
		return null;
	}

}
