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

public class ListCourses {
	
	public ListCourses(JPanel panel, ETRController controller, ETRGUI gui, Account currentAccount, JTable table) {
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
	            	if(getValueAt(row, 2).toString().split("/")[0].equals(getValueAt(row, 2).toString().split("/")[1])) {
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
		model.addColumn(Labels.CAPACITY);
		model.addColumn(Labels.CREDIT);
		model.addColumn(Labels.PLACE);
		model.addColumn(Labels.TIME);
		model.addColumn(Labels.PROFESSOR);
		model.addColumn(Labels.REGISTRATE);
		
		createRecords(panel, controller, gui, currentAccount, table, model);
	}
	
	private void createRecords(JPanel panel, ETRController controller, ETRGUI gui, Account currentAccount, JTable table,
			DefaultTableModel model) {
		panel.removeAll();
		table.setModel(model);
		
		int rowCount= 0;
		
		try {
			ArrayList<Course> courses = controller.getCourses();
			ArrayList<Course> pickedUpCourses = controller.getCourses(currentAccount.getEha());
			ArrayList<Course> toDelete = new ArrayList<>();
			
			for(Course course : courses) {
				for(Course pickedUpCourse : pickedUpCourses) {
					//Előadások
					if(pickedUpCourse.getCode().equals(course.getCode())) {
						toDelete.add(course);
					}
					//Gyakorlatok
					if(pickedUpCourse.getLecture() != null &&
					   course.getLecture() != null &&
					   pickedUpCourse.getLecture().equals(course.getLecture())) {
						toDelete.add(course);
					}
				}
			}
			
			courses.removeAll(toDelete);
			
			if(courses.isEmpty()) {
				panel.add(new JLabel(Labels.NO_COURSES_TO_SHOW), BorderLayout.CENTER);
				panel.repaint();
				panel.revalidate();
				return;
			}
			
			for(Course course : courses) {
				model.addRow(new Object[0]);
				model.setValueAt(course.getCode(), rowCount, 0);
				model.setValueAt(course.getName(), rowCount, 1);
				model.setValueAt(course.getOnIt() + "/" + course.getCapacity(), rowCount, 2);
				model.setValueAt(course.getCredit(), rowCount, 3);
				model.setValueAt(course.getPlace(), rowCount, 4);
				model.setValueAt(course.getWeekday() + ", " + course.getStart() + " - " + course.getEnd(), rowCount, 5);
				model.setValueAt(course.getProfessor(), rowCount, 6);
				model.setValueAt(false, rowCount, 7);
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
		
		JButton button = new JButton(Labels.REGISTRATE);
		
		button.addActionListener(e -> {
			String successfullyRegistratedCourses = Labels.SUCCESSFULLY_REGISTRADED_COURSES;
			for(int i=0; i<table.getRowCount(); i++) {
				if(Boolean.valueOf(table.getValueAt(i, 7).toString())) {
					try {
						for(Course course : controller.getCourses()) {
							if(course.getCode().equals(table.getValueAt(i, 0).toString())) {
								for(Course pickedUpCourse : controller.getCourses(currentAccount.getEha())) {
									if(pickedUpCourse.getLecture() != null &&
									   course.getLecture() != null &&
									   pickedUpCourse.getLecture().equals(course.getLecture())) {
										throw new Exception();
									}
								}
							}
						}
						controller.pickUpACourse(currentAccount.getEha(), table.getValueAt(i, 0).toString());
						successfullyRegistratedCourses += table.getValueAt(i, 1) + "(" + table.getValueAt(i, 0) + ")\n";
					} catch (Exception exception) {
						ETRGUI.createMessage(gui, table.getValueAt(i, 1) + "(" + table.getValueAt(i, 0) + ")" + 
											 Labels.UNSUCCESSFULLY_REGISTRATED_COURSE, Labels.ERROR);
					}
				}
			}
			
			if(!successfullyRegistratedCourses.equals(Labels.SUCCESSFULLY_REGISTRADED_COURSES)) { 
				ETRGUI.createMessage(gui, successfullyRegistratedCourses, Labels.INFORMATION);
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
