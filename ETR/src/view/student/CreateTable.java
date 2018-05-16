package view.student;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTable;

import controller.ETRController;
import dao.model.Account;
import view.ETRGUI;

public class CreateTable extends JPanel {
	private static final long serialVersionUID = 5440133217803163112L;
	private ETRController controller;
	private ETRGUI gui;
	private Account currentAccount;
	private int tableToCreate;
	
	public CreateTable(ETRController controller, ETRGUI gui, Account currentAccount, int tableToCreate) {
		this.controller = controller;
		this.gui = gui;
		this.currentAccount = currentAccount;
		this.tableToCreate = tableToCreate;
		
		createTableModel();
	}

	public void createTableModel() {
		removeAll();
		setLayout(new BorderLayout());
		
		JTable table = new JTable();
		
		//table.setCellSelectionEnabled(true);
		table.getTableHeader().setReorderingAllowed(false);
		table.setDefaultEditor(Object.class, null);
		//table.getTableHeader().setResizingAllowed(false);
		table.setRowSelectionAllowed(false);
		table.setAutoCreateRowSorter(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		switch (tableToCreate) {
			case 0:
				new ListCourses(this, controller, gui, currentAccount, table);
				break;
			case 1:
				new ListOwnCourses(this, controller, gui, currentAccount, table);
				break;
			case 3:
				new ListExams(this, controller, gui, currentAccount, table);
				break;
		}
		
		repaint();
		revalidate();
	}
	
}
