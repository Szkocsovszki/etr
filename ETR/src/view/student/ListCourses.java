package view.student;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.ETRController;
import dao.model.Account;
import view.Labels;

public class ListCourses extends JPanel {
	private static final long serialVersionUID = 5440133217803163112L;
	//private ETRController controller;
	//private Account account;
	
	public ListCourses(ETRController controller, Account currentAccount) {
		/*this.controller = controller;
		this.account = currentAccount;*/
		
		createCoursesList();
	}

	private void createCoursesList() {
		setLayout(new BorderLayout());
		
		JTable table = new JTable();
		//table.setCellSelectionEnabled(true);
		table.getTableHeader().setReorderingAllowed(false);
		table.setDefaultEditor(Object.class, null);
		table.getTableHeader().setResizingAllowed(false);
		table.setRowSelectionAllowed(false);
		table.setAutoCreateRowSorter(true);
		
		DefaultTableModel model = new DefaultTableModel() {
			private static final long serialVersionUID = 1402343006560426665L;

			public Class<?> getColumnClass(int column) {
				switch(column) {
					case 0:
						return String.class;
					case 1:
						return String.class;
					case 2:
						return String.class;
					case 3:
						return Boolean.class;
					default:
						return String.class;
				}
			}
		};
		
		table.setModel(model);
		
		model.addColumn(Labels.COURSE_CODE);
		model.addColumn(Labels.COURSE_NAME);
		model.addColumn(Labels.CAPACITY);
		model.addColumn(Labels.REGISTRATE);
		
		for(int i=0; i<100; i++) {
			model.addRow(new Object[0]);
			model.setValueAt("k"+(i+1), i, 0);
			model.setValueAt("Kurzus "+(i+1), i, 1);
			model.setValueAt((200-i)+"/200", i, 2);
			model.setValueAt(false, i, 3);
		}
		
		add(new JScrollPane(table), BorderLayout.CENTER);
		
		JButton registrate = new JButton(Labels.REGISTRATE);
		
		registrate.addActionListener(e -> {
			for(int i=0; i<table.getRowCount(); i++) {
				if(Boolean.valueOf(table.getValueAt(i, 3).toString())) {
					JOptionPane.showMessageDialog(null, table.getValueAt(i, 1).toString());
				}
			}
		});
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(registrate);
		
		add(buttonPanel, BorderLayout.SOUTH);
		
		/*try {
			ArrayList<String> courseList = controller.getCourses();
			//setLayout(new GridLayout(courseList.size(), 1));
			for(String course : courseList) {
				JLabel label = new JLabel(course);
				table.add(label);
			}
		} catch (SQLException exception) {
		}*/
	}

}
