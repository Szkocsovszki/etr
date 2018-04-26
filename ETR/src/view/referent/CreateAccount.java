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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

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
		
		JTextField nameTextField = new JTextField(Labels.DEFAULT_NAME);
		JTextField ehaTextField = new JTextField(Labels.DEFAULT_EHA);
		JTextField birthDateTextField = new JTextField(Labels.DEFAULT_BIRTH_DATE);
		JTextField addressTextField = new JTextField(Labels.DEFAULT_ADDRESS);
		JTextField departmentTextField = new JTextField(Labels.DEFAULT_DEPARTMENT);
		
		SwingUtilities.invokeLater(new Runnable() {
	        public void run() {
	            nameTextField.requestFocus();
	        }
        });
		nameTextField.setForeground(Color.GRAY);

		nameTextField.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				if(nameTextField.getText().equals(Labels.DEFAULT_NAME)) {
					nameTextField.setText("");
					nameTextField.setForeground(new Color(51, 51, 51));
				}
			}

			public void focusLost(FocusEvent e) {
				if(nameTextField.getText().isEmpty()) {
					nameTextField.setForeground(Color.GRAY);
					nameTextField.setText(Labels.DEFAULT_NAME);
				} /*else {
					String[] pieces = nameTextField.getText().split(" ");
					String name = "";
					for(int i=0; i<pieces.length; i++) {
						pieces[i] = Character.toUpperCase(pieces[i].charAt(0)) + pieces[i].substring(1).toLowerCase();
						name += pieces[i] + " ";
					}
					name = name.substring(0, name.length()-1);
					nameTextField.setText(name);
				}*/
			}
		});
		
		ehaTextField.setForeground(Color.GRAY);
		
		ehaTextField.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				ehaTextField.setForeground(new Color(51, 51, 51));
				if(ehaTextField.getText().equals(Labels.DEFAULT_EHA)) {
					ehaTextField.setText("");
				}
			}

			public void focusLost(FocusEvent e) {
				if(ehaTextField.getText().isEmpty() || ehaTextField.getText().equals(Labels.DEFAULT_EHA_ENDING)) {
					ehaTextField.setForeground(Color.GRAY);
					ehaTextField.setText(Labels.DEFAULT_EHA);
				} else {
					ehaTextField.setText(ehaTextField.getText().toUpperCase());
					if(!ehaTextField.getText().endsWith(Labels.DEFAULT_EHA_ENDING)) {
						ehaTextField.setText(ehaTextField.getText() + Labels.DEFAULT_EHA_ENDING);
					}
				}
			}
		});
		
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
		
		addressTextField.setForeground(Color.GRAY);
		
		addressTextField.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				if( addressTextField.getText().equals(Labels.DEFAULT_ADDRESS)) {
					addressTextField.setText("");
					addressTextField.setForeground(new Color(51, 51, 51));
				}
			}

			public void focusLost(FocusEvent e) {
				if( addressTextField.getText().isEmpty()) {
					addressTextField.setForeground(Color.GRAY);
					addressTextField.setText(Labels.DEFAULT_ADDRESS);
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
		accountPanel.setLayout(new FlowLayout());
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
		JButton createButton = new JButton(Labels.CREATE);
		buttonPanel.add(createButton);
		
		createButton.addActionListener(e -> {
			if(nameTextField.getText().equals(Labels.DEFAULT_NAME)) {
				JOptionPane.showMessageDialog(
						  this,
						  Labels.EMPTY_NAME_TEXTFIELD,
						  Labels.ERROR,
						  JOptionPane.ERROR_MESSAGE);
			} else if(ehaTextField.getText().equals(Labels.DEFAULT_EHA)) {
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
			} else if(addressTextField.getText().equals(Labels.DEFAULT_ADDRESS)) {
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
					Pattern pattern = Pattern.compile(Labels.CORRECT_EHA_FORM);
					Matcher matcher = pattern.matcher(ehaTextField.getText());
					if(!matcher.matches()) {
						throw new IllegalArgumentException();
					}
					
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
							new ArrayList<String>()
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
						throw new NullPointerException(Labels.NO_CHECKBOX_SELECTED);
					}
					JOptionPane.showMessageDialog(
							  this,
							  Labels.USER_SUCCESSFULLY_CREATED,
							  Labels.INFORMATION,
							  JOptionPane.INFORMATION_MESSAGE);
					nameTextField.setText(Labels.DEFAULT_NAME);
					nameTextField.setForeground(Color.GRAY);
					ehaTextField.setText(Labels.DEFAULT_EHA);
					ehaTextField.setForeground(Color.GRAY);
					birthDateTextField.setText(Labels.DEFAULT_BIRTH_DATE);
					birthDateTextField.setForeground(Color.GRAY);
					addressTextField.setText(Labels.DEFAULT_ADDRESS);
					addressTextField.setForeground(Color.GRAY);
					departmentTextField.setText(Labels.DEFAULT_DEPARTMENT);
					departmentTextField.setForeground(Color.GRAY);
					departmentTextField.setEditable(true);
					departmentTextField.setFocusable(true);
					buttonGroup.clearSelection();
					SwingUtilities.invokeLater(new Runnable() {
				        public void run() {
				            nameTextField.requestFocus();
				        }
			        });
				} catch(SQLException exception) {
					JOptionPane.showMessageDialog(
							  this,
							  Labels.USER_ALREADY_EXISTS,
							  Labels.ERROR,
							  JOptionPane.ERROR_MESSAGE);
					//exception.printStackTrace();
				} catch (NullPointerException exception) {
					JOptionPane.showMessageDialog(
							  this,
							  exception.getMessage(),
							  Labels.ERROR,
							  JOptionPane.ERROR_MESSAGE);
					exception.printStackTrace();
				} catch (DateTimeParseException exception) {
					JOptionPane.showMessageDialog(
							  this,
							  Labels.WRONG_DATE_FORMAT,
							  Labels.ERROR,
							  JOptionPane.ERROR_MESSAGE);
				} catch (IllegalArgumentException exception) {
					JOptionPane.showMessageDialog(
							  this,
							  Labels.WRONG_EHA_FORMAT,
							  Labels.ERROR,
							  JOptionPane.ERROR_MESSAGE);
					//exception.printStackTrace();
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
