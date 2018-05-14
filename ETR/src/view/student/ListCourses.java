package view.student;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import controller.ETRController;
import dao.course.Course;
import dao.model.Account;
import view.Labels;

public class ListCourses extends JPanel {
	private static final long serialVersionUID = 5440133217803163112L;
	private ETRController controller;
	//private Account account;
	
	public ListCourses(ETRController controller, Account currentAccount) {
		this.controller = controller;/*
		this.account = currentAccount;*/
		
		createCoursesList();
	}

	private void createCoursesList() {
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
		
		/*for(int i=0; i<100; i++) {
			model.addRow(new Object[0]);
			model.setValueAt("k"+(i+1), i, 0);
			model.setValueAt("Kurzus "+(i+1), i, 1);
			model.setValueAt((200-i)+"/200", i, 2);
			model.setValueAt(false, i, 3);
		}*/
		
		try {
			ArrayList<Course> courses = controller.getCourses();
			int row = 0;
			for(Course course : courses) {
				model.addRow(new Object[0]);
				model.setValueAt(course.getCode(), row, 0);
				model.setValueAt(course.getName(), row, 1);
				model.setValueAt("0/200", row, 2);
				model.setValueAt(course.getCredit().toString(), row, 3);
				model.setValueAt(course.getPlace(), row, 4);
				model.setValueAt(course.getWeekday() + ", " + course.getStart() + " - " + course.getEnd(), row, 5);
				model.setValueAt("Előadó", row, 6);
				model.setValueAt(false, row, 7);
				row++;
			}
		} catch (Exception exception) {
			JOptionPane.showMessageDialog(
					  this,
					  exception.getMessage(),
					  Labels.ERROR,
					  JOptionPane.ERROR_MESSAGE);
		}
		
		for(int column = 0; column < table.getColumnCount(); column++) {
		    TableColumn tableColumn = table.getColumnModel().getColumn(column);
		    int defaultWidth = tableColumn.getWidth();
		 
		    for(int row = 0; row < table.getRowCount(); row++) {
		        TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
		        Component c = table.prepareRenderer(cellRenderer, row, column);
		        int width = c.getPreferredSize().width + table.getIntercellSpacing().width;
		        
		        if(width >= defaultWidth) {
		            defaultWidth = width;
		            break;
		        }
		    }
		 
		    tableColumn.setPreferredWidth(defaultWidth);
		}
		
		/*table.setPreferredScrollableViewportSize(new Dimension(1000, 500));
		table.setFillsViewportHeight(true);*/

		add(new JScrollPane(table), BorderLayout.CENTER);
		
		JButton registrate = new JButton(Labels.REGISTRATE);
		
		registrate.addActionListener(e -> {
			for(int i=0; i<table.getRowCount(); i++) {
				if(Boolean.valueOf(table.getValueAt(i, 7).toString())) {
					JOptionPane.showMessageDialog(null, table.getValueAt(i, 1).toString());
				}
			}
		});
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(registrate);
		
		add(buttonPanel, BorderLayout.SOUTH);
	}

}
