package view.student;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import controller.ETRController;
import dao.course.Course;
import dao.model.Account;
import view.ETRGUI;
import view.Labels;

public class ListOwnCourses {
	
	public ListOwnCourses(JPanel panel, ETRController controller, ETRGUI gui, Account currentAccount, JTable table) {
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
				//Előadó
				case 2:
					return String.class;
				//Kredit
				case 3:
					return Integer.class;
				//Osztályzat
				case 4:
					return String.class;
				//Lejelentkezés
				case 5:
					return Boolean.class;
				default:
					return String.class;
			}
		}
		
		@Override
		public boolean isCellEditable(int row, int column) {
			if(column == 5) {
                if(!getValueAt(row, 4).equals(Labels.NOT_AVAILABLE)) {
                	return false;
                }
                return true;
            } else {
            	return false;
            }
        }
		};
		
		model.addColumn(Labels.COURSE_CODE);
		model.addColumn(Labels.COURSE_NAME);
		model.addColumn(Labels.PROFESSOR);
		model.addColumn(Labels.CREDIT);
		model.addColumn(Labels.MARK);
		model.addColumn(Labels.UNREGISTRATION);
		
		createRecords(panel, controller, gui, currentAccount, table, model);
	}
	
	private void createRecords(JPanel panel, ETRController controller, ETRGUI gui, Account currentAccount, JTable table,
			DefaultTableModel model) {
		panel.removeAll();
		table.setModel(model);
		
		int rowCount = 0;
		
		try {
			ArrayList<Course> pickedUpCourses = controller.getCourses(currentAccount.getEha());
			
			if(pickedUpCourses.isEmpty()) {
				panel.add(new JLabel(Labels.NO_COURSES_TO_SHOW), BorderLayout.CENTER);
				panel.repaint();
				panel.revalidate();
				return;
			}
			
			for(Course course : pickedUpCourses) {
				model.addRow(new Object[0]);
				model.setValueAt(course.getCode(), rowCount, 0);
				model.setValueAt(course.getName(), rowCount, 1);
				model.setValueAt(course.getProfessor(), rowCount, 2);
				model.setValueAt(course.getCredit(), rowCount, 3);
				if(course.getMark() == 0) {
					model.setValueAt(Labels.NOT_AVAILABLE, rowCount, 4);
				} else {
					model.setValueAt(course.getMark(), rowCount, 4);
				}
				model.setValueAt(false, rowCount, 5);
				rowCount++;
			}
		} catch (Exception exception) {
			ETRGUI.createMessage(gui, exception.getMessage(), Labels.ERROR);
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
		        }
		    }
		 
		    tableColumn.setPreferredWidth(defaultWidth);
		}
		
		int width = table.getPreferredSize().width < (Toolkit.getDefaultToolkit().getScreenSize().width - 50) ?
				(Toolkit.getDefaultToolkit().getScreenSize().width - 50) : table.getPreferredSize().width;
				
		if(table.getWidth() < width) {
			table.setPreferredSize(new Dimension(width, table.getPreferredSize().height));
		}
		
		table.setPreferredSize(new Dimension(table.getPreferredSize().width, rowCount * table.getRowHeight()));
				
		table.setPreferredScrollableViewportSize(new Dimension(width, table.getPreferredSize().height));
		table.setFillsViewportHeight(true);
		
		panel.add(new JScrollPane(table), BorderLayout.CENTER);
		
		JButton button = new JButton(Labels.UNREGISTRATION);
		
		button.addActionListener(e -> {
			String successfullyTakenOffCourses = Labels.SUCCESSFULLY_TAKEN_OFF_COURSES;
			for(int i=0; i<table.getRowCount(); i++) {
				if(Boolean.valueOf(table.getValueAt(i, 5).toString())) {
					try {
						controller.takeOffACourse(currentAccount.getEha(), table.getValueAt(i, 0).toString());
						successfullyTakenOffCourses += table.getValueAt(i, 1) + "(" + table.getValueAt(i, 0) + ")\n";
					} catch (Exception exception) {
						ETRGUI.createMessage(gui, table.getValueAt(i, 1) + "(" + table.getValueAt(i, 0) + ")" + 
											 Labels.UNSUCCESSFULLY_TAKEN_OFF_COURSE, Labels.ERROR);
					}
				}
			}
			
			if(!successfullyTakenOffCourses.equals(Labels.SUCCESSFULLY_TAKEN_OFF_COURSES)) { 
				ETRGUI.createMessage(gui, successfullyTakenOffCourses, Labels.INFORMATION);
			}
			
			DefaultTableModel tableModel = (DefaultTableModel)table.getModel();
			tableModel.getDataVector().removeAllElements();
			createRecords(panel, controller, gui, currentAccount, table, model);
		});
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(button);
		
		panel.add(buttonPanel, BorderLayout.SOUTH);
		
		panel.repaint();
		panel.revalidate();
	}

}
