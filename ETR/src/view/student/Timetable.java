package view.student;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

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

public class Timetable {
	public Timetable(JPanel panel, ETRController controller, ETRGUI gui, Account currentAccount, JTable table) {
		DefaultTableModel model = new DefaultTableModel() {
			private static final long serialVersionUID = 1402343006560426665L;

			public Class<String> getColumnClass(int columnIndex) {
	            return String.class;
	        }
		
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		model.addColumn(Labels.DAY_OF_THE_WEEK);
		for(int i=8; i<20; i++) {
			model.addColumn(i + ":00 - " + (i+1) + ":00");
		}
		
		createRecords(panel, controller, gui, currentAccount, table, model);
	}
	
	private void createRecords(JPanel panel, ETRController controller, ETRGUI gui, Account currentAccount, JTable table,
			DefaultTableModel model) {
		panel.removeAll();
		table.setModel(model);
		
		String[] weekDays = new String[] {Labels.MONDAY, Labels.TUESDAY, Labels.WEDNESDAY, Labels.THURSDAY, Labels.FRIDAY};
		
		try {
			ArrayList<Course> pickedUpCourses = controller.getCourses(currentAccount.getEha());
			
			if(pickedUpCourses.isEmpty()) {
				panel.add(new JLabel(Labels.NO_COURSES_TO_SHOW), BorderLayout.CENTER);
				panel.repaint();
				panel.revalidate();
				return;
			}
			
			int rowCount = 0;
			for(int i=0; i<5; i++) {
				model.addRow(new Object[0]);
				model.setValueAt(weekDays[rowCount], rowCount, 0);
				model.setValueAt(null, rowCount, 1);
				model.setValueAt(null, rowCount, 2);
				model.setValueAt(null, rowCount, 3);
				model.setValueAt(null, rowCount, 4);
				model.setValueAt(null, rowCount, 5);
				model.setValueAt(null, rowCount, 6);
				model.setValueAt(null, rowCount, 7);
				model.setValueAt(null, rowCount, 8);
				model.setValueAt(null, rowCount, 9);
				model.setValueAt(null, rowCount, 10);
				model.setValueAt(null, rowCount, 11);
				model.setValueAt(null, rowCount, 12);
				rowCount++;
			}
			
			for(Course course : pickedUpCourses) {
				int row = 0;
				int column = 0;
				for(int i=0; i<weekDays.length; i++) {
					if(weekDays[i].equals(course.getWeekday())) {
						row = i+1;
						break;
					}
				}
				for(int i=0; i<12; i++) {
					if(table.getColumnName(i).contains(course.getStart())) {
						column = i;
						break;
					}
				}

				model.setValueAt(course.getName() + " - " + course.getPlace(), row, column);
			}
			
		} catch (Exception exception) {
			ETRGUI.createMessage(gui, exception.getMessage(), Labels.ERROR);
			exception.printStackTrace();
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
		
		table.setPreferredSize(new Dimension(table.getPreferredSize().width, 5 * table.getRowHeight()));
				
		table.setPreferredScrollableViewportSize(new Dimension(width, table.getPreferredSize().height));
		table.setFillsViewportHeight(true);
		
		panel.add(new JScrollPane(table), BorderLayout.CENTER);
		
		panel.repaint();
		panel.revalidate();
	}

}
