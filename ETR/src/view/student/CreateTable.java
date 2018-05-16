package view.student;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.ETRController;
import dao.model.Account;
import view.ETRGUI;
import view.Labels;

public class CreateTable extends JPanel {
	private static final long serialVersionUID = 5440133217803163112L;
	private ETRController controller;
	private ETRGUI gui;
	private Account currentAccount;
	private boolean ownCourses;
	
	//private JButton button;
	/*private JTable table;*/
	
	public CreateTable(ETRController controller, ETRGUI gui, Account currentAccount, boolean ownCourses) {
		this.controller = controller;
		this.gui = gui;
		this.currentAccount = currentAccount;
		this.ownCourses = ownCourses;
		
		createTableModel();
	}

	public void createTableModel() {
		removeAll();
		setLayout(new BorderLayout());
		
		JTable table = new JTable();
		
		DefaultTableModel model = new DefaultTableModel() {
			private static final long serialVersionUID = 1402343006560426665L;

			public Class<?> getColumnClass(int column) {
				switch(column) {
					//Kurzuskód
					case 0:
						return String.class;
					//Kurzusnév
					case 1:
						return String.class;
					//Kapacitás
					case 2:
						return String.class;
					//Kredit
					case 3:
						return Integer.class;
					//Hely
					case 4:
						return Integer.class;
					//Időpont
					case 5:
						return String.class;
					//Előadó
					case 6:
						return String.class;
					//Jelentkezés
					case 7:
						return Boolean.class;
					default:
						return String.class;
				}
			}
			
			@Override
			public boolean isCellEditable(int row, int column) {
	            if(column == 7) {
	                return true;
	            } else {
	            	return false;
	            }
	        }
		};
		
		//table.setCellSelectionEnabled(true);
		table.getTableHeader().setReorderingAllowed(false);
		table.setDefaultEditor(Object.class, null);
		//table.getTableHeader().setResizingAllowed(false);
		table.setRowSelectionAllowed(false);
		table.setAutoCreateRowSorter(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);		
		table.setModel(model);
		
		model.addColumn(Labels.COURSE_CODE);
		model.addColumn(Labels.COURSE_NAME);
		model.addColumn(Labels.CAPACITY);
		model.addColumn(Labels.CREDIT);
		model.addColumn(Labels.PLACE);
		model.addColumn(Labels.TIME);
		model.addColumn(Labels.PROFESSOR);
		model.addColumn(Labels.REGISTRATE);
		
		if(ownCourses) {
			
		} else {
			new ListCourses(this, controller, gui, currentAccount, table, model);
		}
		
		repaint();
		revalidate();
	}
	
}
