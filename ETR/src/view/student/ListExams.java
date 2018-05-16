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
import dao.course.Exam;
import dao.model.Account;
import view.ETRGUI;
import view.Labels;

public class ListExams {
	
	public ListExams(JPanel panel, ETRController controller, ETRGUI gui, Account currentAccount, JTable table) {
		DefaultTableModel model = new DefaultTableModel() {
			private static final long serialVersionUID = 1402343006560426665L;

			public Class<?> getColumnClass(int column) {
				switch(column) {
					//Vizsgakód
					case 0:
						return String.class;
					//Vizsganév
					case 1:
						return String.class;
					//Kapacitás
					case 2:
						return String.class;
					//Hely
					case 3:
						return String.class;
					//Időpont
					case 4:
						return String.class;
					//Ár
					case 5:
						return Integer.class;
					//Jelentkezés
					case 6:
						return Boolean.class;
					default:
						return String.class;
				}
			}
			
			@Override
			public boolean isCellEditable(int row, int column) {
	            if(column == 6) {
	            	if(getValueAt(row, 2).toString().split("/")[0].equals(getValueAt(row, 2).toString().split("/")[1])) {
	            		return false;
	            	}
	            	return true;
	            } else {
	            	return false;
	            }
	        }
		};
		
		model.addColumn(Labels.EXAM_CODE);
		model.addColumn(Labels.EXAM_NAME);
		model.addColumn(Labels.CAPACITY);
		model.addColumn(Labels.PLACE);
		model.addColumn(Labels.TIME);
		model.addColumn(Labels.PRICE);
		model.addColumn(Labels.REGISTRATE);
		
		createRecords(panel, controller, gui, currentAccount, table, model);
	}
	
	private void createRecords(JPanel panel, ETRController controller, ETRGUI gui, Account currentAccount, JTable table,
			DefaultTableModel model) {
		panel.removeAll();
		table.setModel(model);
		
		int rowCount = 0;
		
		try {
			ArrayList<Exam> exams = controller.getExams();
			ArrayList<Exam> toDelete = new ArrayList<>();
			
			for(Exam exam : exams) {
				for(Exam pickedUpExam : controller.getExams(currentAccount.getEha())) {
					if(pickedUpExam.getCode().equals(exam.getCode())) {
						toDelete.add(exam);
					}
				}
			}
			
			exams.removeAll(toDelete);
			
			if(exams.isEmpty()) {
				panel.add(new JLabel(Labels.NO_EXAMS_TO_SHOW), BorderLayout.CENTER);
				panel.repaint();
				panel.revalidate();
				return;
			}
			
			for(Exam exam : exams) {
				model.addRow(new Object[0]);
				model.setValueAt(exam.getCode(), rowCount, 0);
				model.setValueAt(exam.getName(), rowCount, 1);
				model.setValueAt(exam.getOnIt() + "/" + exam.getCapacity(), rowCount, 2);
				model.setValueAt(exam.getPlace(), rowCount, 3);
				model.setValueAt(exam.getTime(), rowCount, 4);
				model.setValueAt(exam.getPrice(), rowCount, 5);
				model.setValueAt(false, rowCount, 6);
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
			String successfullyRegistratedExams = Labels.SUCCESSFULLY_REGISTRADED_EXAMS;
			for(int i=0; i<table.getRowCount(); i++) {
				if(Boolean.valueOf(table.getValueAt(i, 6).toString())) {
					try {
						for(Exam exam : controller.getExams()) {
							if(exam.getCode().equals(table.getValueAt(i, 0).toString())) {
								for(Exam pickedUpExam : controller.getExams(currentAccount.getEha())) {
									if(pickedUpExam.getName() != null &&
									   exam.getName() != null &&
									   pickedUpExam.getName().equals(exam.getName()) &&
									   pickedUpExam.getMark() == 0) {
										throw new Exception();
									}
								}
							}
						}
						
						controller.pickUpAnExam(currentAccount.getEha(), table.getValueAt(i, 0).toString());
						successfullyRegistratedExams += table.getValueAt(i, 1) + "(" + table.getValueAt(i, 0) + ")" + "\n";
					} catch (Exception exception) {
						ETRGUI.createMessage(gui, table.getValueAt(i, 1) + "(" + table.getValueAt(i, 0) + ")" + 
											 Labels.UNSUCCESSFULLY_REGISTRATED_EXAM, Labels.ERROR);
					}
				}
			}
			
			if(!successfullyRegistratedExams.equals(Labels.SUCCESSFULLY_REGISTRADED_EXAMS)) {
				ETRGUI.createMessage(gui, successfullyRegistratedExams, Labels.INFORMATION);
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
