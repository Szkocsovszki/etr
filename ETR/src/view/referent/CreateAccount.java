package view.referent;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.ETRController;
import dao.model.Professor;
import dao.model.Referent;
import dao.model.Student;
import view.Labels;

public class CreateAccount extends JPanel {
	private static final long serialVersionUID = 1605525325559960395L;

	public CreateAccount(ETRController controller) {
		setLayout(new GridLayout(3, 1));
		
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(5, 2));

		JLabel nameLabel = new JLabel(Labels.NAME);
		JLabel ehaLabel = new JLabel(Labels.EHA);
		JLabel birthDateLabel = new JLabel(Labels.BIRTH_DATE);
		JLabel addressLabel = new JLabel(Labels.ADDRESS);
		JLabel departmentLabel = new JLabel(Labels.DEPARTMENT);
		
		JTextField nameTextField = new JTextField();
		JTextField ehaTextField = new JTextField();
		JTextField birthDateTextField = new JTextField(Labels.DEFAULT_BIRTH_DATE);
		JTextField addressTextField = new JTextField();
		JTextField departmentTextField = new JTextField(Labels.DEFAULT_DEPARTMENT);
		
		birthDateTextField.setForeground(Color.GRAY);

		birthDateTextField.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				if(birthDateTextField.getText().equals(Labels.DEFAULT_BIRTH_DATE)) {
					birthDateTextField.setText("");
					birthDateTextField.setForeground(new Color(51, 51, 51));
				}
			}

			public void focusLost(FocusEvent e) {
				if(birthDateTextField.getText().isEmpty()) {
					birthDateTextField.setForeground(Color.GRAY);
					birthDateTextField.setText(Labels.DEFAULT_BIRTH_DATE);
				}
			}
		});
		
		departmentTextField.setForeground(Color.GRAY);

		departmentTextField.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				if(departmentTextField.getText().equals(Labels.DEFAULT_DEPARTMENT)) {
					departmentTextField.setText("");
					departmentTextField.setForeground(new Color(51, 51, 51));
				}
			}
			public void focusLost(FocusEvent e) {
				if(departmentTextField.getText().isEmpty()) {
					departmentTextField.setForeground(Color.GRAY);
					departmentTextField.setText(Labels.DEFAULT_DEPARTMENT);
				}
			}
		});
		
		inputPanel.add(nameLabel);
		inputPanel.add(nameTextField);
		inputPanel.add(ehaLabel);
		inputPanel.add(ehaTextField);
		inputPanel.add(birthDateLabel);
		inputPanel.add(birthDateTextField);
		inputPanel.add(addressLabel);
		inputPanel.add(addressTextField);
		inputPanel.add(departmentLabel);
		inputPanel.add(departmentTextField);
		
		JPanel accountPanel = new JPanel();
		accountPanel.setLayout(new GridLayout(1, 3));
		JCheckBox referentCheckBox = new JCheckBox(Labels.REFERENT);
		JCheckBox professorCheckBox = new JCheckBox(Labels.PROFESSOR);
		JCheckBox studentCheckBox = new JCheckBox(Labels.STUDENT);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(referentCheckBox);
		buttonGroup.add(professorCheckBox);
		buttonGroup.add(studentCheckBox);
		
		accountPanel.add(referentCheckBox);
		accountPanel.add(professorCheckBox);
		accountPanel.add(studentCheckBox);
		
		referentCheckBox.addActionListener(e -> {
			departmentTextField.setText("");
			departmentTextField.setEditable(false);
			departmentTextField.setFocusable(false);
		});
		
		professorCheckBox.addActionListener(e -> {
			departmentTextField.setEditable(true);
			departmentTextField.setFocusable(true);
			if(departmentTextField.getText().isEmpty()) {
				departmentTextField.setForeground(Color.GRAY);
				departmentTextField.setText(Labels.DEFAULT_DEPARTMENT);
			}
		});
		
		studentCheckBox.addActionListener(e -> {
			departmentTextField.setEditable(true);
			departmentTextField.setFocusable(true);
			if(departmentTextField.getText().isEmpty()) {
				departmentTextField.setForeground(Color.GRAY);
				departmentTextField.setText(Labels.DEFAULT_DEPARTMENT);
			}
		});
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		JButton button = new JButton(Labels.CREATE);
		buttonPanel.add(button);
		
		button.addActionListener(e -> {
			if(nameTextField.getText().isEmpty()) {
				JOptionPane.showMessageDialog(
						  this,
						  Labels.EMPTY_NAME_TEXTFIELD,
						  Labels.ERROR,
						  JOptionPane.ERROR_MESSAGE);
			} else if(ehaTextField.getText().isEmpty()) {
				JOptionPane.showMessageDialog(
						  this,
						  Labels.EMPTY_EHA_TEXTFIELD,
						  Labels.ERROR,
						  JOptionPane.ERROR_MESSAGE);
			} else if(birthDateTextField.getText().equals(Labels.DEFAULT_BIRTH_DATE)) {
				JOptionPane.showMessageDialog(
						  this,
						  Labels.EMPTY_BIRTH_DATE_TEXTFIELD,
						  Labels.ERROR,
						  JOptionPane.ERROR_MESSAGE);
			} else if(addressTextField.getText().isEmpty()) {
				JOptionPane.showMessageDialog(
						  this,
						  Labels.EMPTY_ADDRESS_TEXTFIELD,
						  Labels.ERROR,
						  JOptionPane.ERROR_MESSAGE);
			} else if(departmentTextField.getText().equals(Labels.DEFAULT_DEPARTMENT)) {
				JOptionPane.showMessageDialog(
						  this,
						  Labels.EMPTY_DEPARTMENT_TEXTFIELD,
						  Labels.ERROR,
						  JOptionPane.ERROR_MESSAGE);
			} else {
				try {
					LocalDate.parse(birthDateTextField.getText(),
									DateTimeFormatter.ofPattern("uuuu-MM-dd")
			                 		.withResolverStyle(ResolverStyle.STRICT)
			        );
					
					String[] pieces = departmentTextField.getText().split(", ");
					ArrayList<String> department = new ArrayList<>();
					for(int i=0; i<pieces.length; i++) {
						department.add(pieces[i]);
					}
				
					if(referentCheckBox.isSelected()) {
						Referent referent = new Referent(
							nameTextField.getText(),
							ehaTextField.getText(),
							birthDateTextField.getText(),
							addressTextField.getText(),
							null
						);
						controller.createAccount(referent);
					} else if(professorCheckBox.isSelected()) {
						Professor professor = new Professor(
							nameTextField.getText(),
							ehaTextField.getText(),
							birthDateTextField.getText(),
							addressTextField.getText(),
							department
						);
						controller.createAccount(professor);
					} else if(studentCheckBox.isSelected()) {
						Student student = new Student(
							nameTextField.getText(),
							ehaTextField.getText(),
							birthDateTextField.getText(),
							addressTextField.getText(),
							department
						);
						controller.createAccount(student);
					} else {
						throw new NullPointerException();
					}
					JOptionPane.showMessageDialog(
							  this,
							  Labels.USER_SUCCESSFULLY_CREATED,
							  Labels.INFORMATION,
							  JOptionPane.INFORMATION_MESSAGE);
					nameTextField.setText("");
					ehaTextField.setText("");
					birthDateTextField.setText(Labels.DEFAULT_BIRTH_DATE);
					birthDateTextField.setForeground(Color.GRAY);
					addressTextField.setText("");
					departmentTextField.setText(Labels.DEFAULT_DEPARTMENT);
					departmentTextField.setForeground(Color.GRAY);
					departmentTextField.setEditable(true);
					departmentTextField.setFocusable(true);
					buttonGroup.clearSelection();
				} catch(SQLException exception) {
					JOptionPane.showMessageDialog(
							  this,
							  Labels.USER_ALREADY_EXISTS,
							  Labels.ERROR,
							  JOptionPane.ERROR_MESSAGE);
					exception.printStackTrace();
				} catch (NullPointerException exception) {
					JOptionPane.showMessageDialog(
							  this,
							  Labels.NO_CHECKBOX_SELECTED,
							  Labels.ERROR,
							  JOptionPane.ERROR_MESSAGE);
				} catch (DateTimeParseException exception) {
					JOptionPane.showMessageDialog(
							  this,
							  Labels.WRONG_DATE_FORMAT,
							  Labels.ERROR,
							  JOptionPane.ERROR_MESSAGE);
				} catch (Exception exception) {
					JOptionPane.showMessageDialog(
							  this,
							  exception.getMessage(),
							  Labels.ERROR,
							  JOptionPane.ERROR_MESSAGE);
					exception.printStackTrace();
				}
			}
		});

		add(inputPanel);
		add(accountPanel);
		add(buttonPanel);
	}
	
}
