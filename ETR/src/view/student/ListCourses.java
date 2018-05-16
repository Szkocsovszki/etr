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
	private int deletedRowCount; 
	
	public ListCourses(JPanel panel, ETRController controller, ETRGUI gui, Account currentAccount, JTable table, DefaultTableModel model) {
		deletedRowCount = 0;
		createRecords(panel, controller, gui, currentAccount, table, model);
	}
	
	private void createRecords(JPanel panel, ETRController controller, ETRGUI gui, Account currentAccount, JTable table,
			DefaultTableModel model) {
		panel.removeAll();
		table.setModel(model);
		
		try {
			ArrayList<Course> courses = controller.getCourses();
			ArrayList<Course> pickedUpCourses = controller.getCourses(currentAccount.getEha());
			ArrayList<Course> toDelete = new ArrayList<>();
			
			for(Course course : courses) {
				for(Course pickedUpCourse : pickedUpCourses) {
					if(pickedUpCourse.getCode() != null &&
					   course.getCode() != null &&
					   pickedUpCourse.getCode().toString().equals(course.getCode().toString())) {
						toDelete.add(course);
					}
					if(pickedUpCourse.getLecture() != null &&
					   course.getLecture() != null &&
					   pickedUpCourse.getLecture().toString().equals(course.getLecture().toString())) {
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
			
			int row = 0;
			for(Course course : courses) {
				model.addRow(new Object[0]);
				model.setValueAt(course.getCode(), row, 0);
				model.setValueAt(course.getName(), row, 1);
				model.setValueAt(course.getOnIt() + "/" + course.getCapacity(), row, 2);
				model.setValueAt(course.getCredit(), row, 3);
				model.setValueAt(course.getPlace(), row, 4);
				model.setValueAt(course.getWeekday() + ", " + course.getStart() + " - " + course.getEnd(), row, 5);
				model.setValueAt(course.getProfessor(), row, 6);
				model.setValueAt(false, row, 7);
				row++;
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
		/*int height = table.getPreferredSize().height < (Toolkit.getDefaultToolkit().getScreenSize().height - 200) ?
		(Toolkit.getDefaultToolkit().getScreenSize().height - 200) : table.getPreferredSize().height;*/
		
		int height = table.getPreferredSize().height - deletedRowCount * table.getRowHeight();
		deletedRowCount = 0;
		table.setPreferredSize(new Dimension(table.getPreferredSize().width, height));
				
		table.setPreferredScrollableViewportSize(new Dimension(width, table.getPreferredSize().height));
		//JAVÍTANI, MERT EZ VÁLTOZÓ, ÉS ÁLLANDÓ KELL!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		table.setFillsViewportHeight(true);
		
		panel.add(new JScrollPane(table), BorderLayout.CENTER);
		
		JButton button = new JButton(Labels.REGISTRATE);
		
		button.addActionListener(e -> {
			String successfullyRegistratedCourses = Labels.SUCCESSFULLY_REGISTRADED_COURSES;
			for(int i=0; i<table.getRowCount(); i++) {
				if(Boolean.valueOf(table.getValueAt(i, 7).toString())) {
					try {
						ArrayList<Course> courses = controller.getCourses();
						ArrayList<Course> pickedUpCourses = controller.getCourses(currentAccount.getEha());
						for(Course course : courses) {
							if(course.getCode().toString().equals(table.getValueAt(i, 0).toString())) {
								for(Course pickedUpCourse : pickedUpCourses) {
									if(pickedUpCourse.getLecture() != null &&
									   course.getLecture() != null &&
									   pickedUpCourse.getLecture().toString().equals(course.getLecture().toString())) {
										throw new Exception();
									}
								}
							}
						}
						controller.pickUpACourse(currentAccount.getEha(), table.getValueAt(i, 0).toString());
						successfullyRegistratedCourses += table.getValueAt(i, 1) + "\n";
						
						for(Course course : courses) {
							if(course.getCode().toString().equals(table.getValueAt(i, 0).toString())) {
								deletedRowCount++;
								for(Course course2 : courses) {
									if(!course.getCode().toString().equals(course2.getCode().toString())) {
										if(course.getLecture() != null && course2.getLecture() != null) {
											if(course.getLecture().toString().equals(course2.getLecture().toString())) {
												deletedRowCount++;
											}
										}
									}
								}
								break;
							}
						}
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
