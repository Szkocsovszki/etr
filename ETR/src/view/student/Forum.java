package view.student;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import controller.ETRController;
import dao.course.Course;
import dao.course.ForumMessage;
import dao.model.Account;
import view.ETRGUI;
import view.Labels;

public class Forum implements TableModelListener {
	private JPanel panel;
	private ETRController controller;
	private ETRGUI gui;
	private Account currentAccount;
	private JTable table;

	public Forum(JPanel panel, ETRController controller, ETRGUI gui, Account currentAccount, JTable table) {
		this.panel = panel;
		this.controller = controller;
		this.gui = gui;
		this.currentAccount = currentAccount;
		this.table = table;
		
		DefaultTableModel model = new DefaultTableModel() {
			private static final long serialVersionUID = 1402343006560426665L;

			public Class<?> getColumnClass(int column) {
				switch(column) {
				//Vizsgakód
				case 0:
					return String.class;
				//Lejelentkezés
				case 1:
					return Boolean.class;
				default:
					return String.class;
				}
			}
		
			@Override
			public boolean isCellEditable(int row, int column) {
	            return true;
	        }
		};
		
		model.addColumn(Labels.COURSE_NAME);
		model.addColumn(Labels.FORUM);
		
		createRecords(panel, controller, gui, currentAccount, table, model);
	}
	
	private void createRecords(JPanel panel, ETRController controller, ETRGUI gui, Account currentAccount, JTable table,
			DefaultTableModel model) {
		panel.removeAll();
		table.setModel(model);
		
		int rowCount = 0;
		
		try {
			ArrayList<Course> courses = controller.getCourses(currentAccount.getEha());
			
			if(courses.isEmpty()) {
				panel.add(new JLabel(Labels.NO_FORUM_TO_SHOW), BorderLayout.CENTER);
				panel.repaint();
				panel.revalidate();
				return;
			}
			
			for(Course course : courses) {
				model.addRow(new Object[0]);
				model.setValueAt(course.getName() + "(" + course.getCode() + ")", rowCount, 0);
				model.setValueAt(false, rowCount, 1);
				rowCount++;
			}
		} catch (Exception exception) {
			ETRGUI.createMessage(gui, exception.getMessage(), Labels.ERROR);
			exception.printStackTrace();
		}
		
		table.getModel().addTableModelListener(this);
		
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
		
		panel.repaint();
		panel.revalidate();
	}

	@Override
	public void tableChanged(TableModelEvent e) {
	        TableModel tableModel = (TableModel) e.getSource();
	        if(e.getColumn() == 1) {
	        	showCourseForum(tableModel.getValueAt(e.getFirstRow(), 0).toString());
	        }
	}

	private void showCourseForum(String courseName) {
		table.getModel().addTableModelListener(null);
		panel.removeAll();
		
		panel.setLayout(new GridLayout(3, 1));
		
		try {
			DefaultTableModel messageModel = new DefaultTableModel() {
				private static final long serialVersionUID = -3470390279755441442L;

				public Class<String> getColumnClass(int column) {
					return String.class;
				}
			
				@Override
				public boolean isCellEditable(int row, int column) {
		            return false;
		        }
			};
			
			messageModel.addColumn(courseName);
			
			ArrayList<ForumMessage> messages = controller.getMessages(courseName.split("\\(")[1].split("\\)")[0]);
			int rowCount = 0; 
			for(ForumMessage message : messages) {
				messageModel.addRow(new Object[0]);
				messageModel.setValueAt(message.getName() + ", " + message.getTime() +": " + message.getMessage(), rowCount, 0);
				rowCount++;
				
			}table.setPreferredSize(new Dimension(table.getPreferredSize().width, rowCount * table.getRowHeight()));
			table.setPreferredScrollableViewportSize(new Dimension(table.getPreferredSize().width, table.getPreferredSize().height));
			table.setFillsViewportHeight(true);
			table.setModel(messageModel);
			table.getModel().addTableModelListener(null);
			panel.add(new JScrollPane(table));
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		JTextField commentTextField = new JTextField();
		panel.add(commentTextField);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());
		JButton backButton = new JButton(Labels.BACK);
		JButton submitButton = new JButton(Labels.SUBMIT);
		
		backButton.addActionListener(e -> {
			panel.setLayout(new BorderLayout());
			new Forum(panel, controller, gui, currentAccount, table);
		});
		
		submitButton.addActionListener(e -> {
			try {
				controller.writeMessage(currentAccount.getEha(), courseName.split("\\(")[1].split("\\)")[0], commentTextField.getText());
				showCourseForum(courseName);
			} catch (SQLException e1) {
				ETRGUI.createMessage(gui, e1.getMessage(), Labels.ERROR);
			}
			commentTextField.setText("");
		});
		
		buttonPanel.add(submitButton, BorderLayout.WEST);
		buttonPanel.add(backButton, BorderLayout.EAST);
		JPanel panelForButtonPanel = new JPanel();
		panelForButtonPanel.setLayout(new FlowLayout());
		panelForButtonPanel.add(buttonPanel);
		panel.add(panelForButtonPanel);
		
		panel.repaint();
		panel.revalidate();
	}


}
