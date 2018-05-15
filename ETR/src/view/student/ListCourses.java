package view.student;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
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

public class ListCourses extends JPanel {
	private static final long serialVersionUID = 5440133217803163112L;
	private ETRController controller;
	private ETRGUI gui;
	private Account currentAccount;
	
	public ListCourses(ETRController controller, ETRGUI gui, Account currentAccount) {
		this.controller = controller;
		this.gui = gui;
		this.currentAccount = currentAccount;
		
		createCoursesList();
	}

	private void createCoursesList() {
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
				add(new JLabel(Labels.NO_COURSES_TO_SHOW), BorderLayout.CENTER);
				repaint();
				revalidate();
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
		            break;
		        }
		    }
		 
		    tableColumn.setPreferredWidth(defaultWidth);
		}
		
		table.setPreferredScrollableViewportSize(new Dimension(table.getPreferredSize().width, table.getPreferredSize().height));
		//JAVÍTANI, MERT EZ VÁLTOZÓ, ÉS ÁLLANDÓ KELL!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		table.setFillsViewportHeight(true);
		
		add(new JScrollPane(table), BorderLayout.CENTER);
		
		JButton registrate = new JButton(Labels.REGISTRATE);
		
		registrate.addActionListener(e -> {
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
					} catch (Exception exception) {
						ETRGUI.createMessage(gui, table.getValueAt(i, 1) + "(" + table.getValueAt(i, 0) + ")" + 
											 Labels.UNSUCCESSFULLY_REGISTRATED_COURSE, Labels.ERROR);
					}
				}
			}
			
			if(!successfullyRegistratedCourses.equals(Labels.SUCCESSFULLY_REGISTRADED_COURSES)) { 
				ETRGUI.createMessage(gui, successfullyRegistratedCourses, Labels.INFORMATION);
			}
			
			createCoursesList();
		});
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(registrate);
		
		add(buttonPanel, BorderLayout.SOUTH);
		repaint();
		revalidate();
	}

}
