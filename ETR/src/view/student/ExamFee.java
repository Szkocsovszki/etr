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

public class ExamFee {
	
	public ExamFee(JPanel panel, ETRController controller, ETRGUI gui, Account currentAccount, JTable table) {
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
					//Ár
					case 2:
						return Integer.class;
					//Befizetés
					case 3:
						return Boolean.class;
					default:
						return String.class;
				}
			}
		
			@Override
			public boolean isCellEditable(int row, int column) {
	            if(column == 3) {
	                return true;
	            } else {
	            	return false;
	            }
	        }
		};
		
		model.addColumn(Labels.EXAM_CODE);
		model.addColumn(Labels.EXAM_NAME);
		model.addColumn(Labels.PRICE);
		model.addColumn(Labels.PAY);
		
		createRecords(panel, controller, gui, currentAccount, table, model);
	}
	
	private void createRecords(JPanel panel, ETRController controller, ETRGUI gui, Account currentAccount, JTable table,
			DefaultTableModel model) {
		panel.removeAll();
		table.setModel(model);
		
		int rowCount = 0;
		
		try {
			ArrayList<Exam> pickedUpExams = controller.getExams(currentAccount.getEha());
			
			boolean hasItemsToPay = false;
			for(Exam exam : pickedUpExams) {
				if(exam.getPrice() > 0) {
					hasItemsToPay = true;
					break;
				}
			}
			
			if(pickedUpExams.isEmpty() || !hasItemsToPay) {
				panel.add(new JLabel(Labels.NO_ITEMS_TO_SHOW), BorderLayout.CENTER);
				panel.repaint();
				panel.revalidate();
				return;
			}
			
			for(Exam exam : pickedUpExams) {
				if(exam.getPrice() > 0) {
					model.addRow(new Object[0]);
					model.setValueAt(exam.getCode(), rowCount, 0);
					model.setValueAt(exam.getName(), rowCount, 1);
					model.setValueAt(exam.getPrice(), rowCount, 2);
					model.setValueAt(false, rowCount, 3);
					rowCount++;
				}
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
		
		JButton button = new JButton(Labels.PAY);
		
		button.addActionListener(e -> {
			String successfullyPaidExams = Labels.SUCCESSFULLY_PAID_EXAMS;
			for(int i=0; i<table.getRowCount(); i++) {
				if(Boolean.valueOf(table.getValueAt(i, 3).toString())) {
					try {
						controller.payment(currentAccount.getEha(), table.getValueAt(i, 0).toString());
						successfullyPaidExams += table.getValueAt(i, 1) + "(" + table.getValueAt(i, 0) + ")\n";
					} catch (Exception exception) {
						ETRGUI.createMessage(gui, table.getValueAt(i, 1) + "(" + table.getValueAt(i, 0) + ")" + 
											 Labels.UNSUCCESSFULLY_PAID_EXAM, Labels.ERROR);
					}
				}
			}
			
			if(!successfullyPaidExams.equals(Labels.SUCCESSFULLY_PAID_EXAMS)) { 
				ETRGUI.createMessage(gui, successfullyPaidExams, Labels.INFORMATION);
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
