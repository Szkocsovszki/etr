package view.referent;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.ETRController;
import dao.model.Referent;
import view.Labels;

public class CreateAccount extends JPanel {
	private static final long serialVersionUID = 1605525325559960395L;

	public CreateAccount(ETRController controller) {
		setLayout(new GridLayout(2, 1));
		
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
				//errorIsVisible = wrongDateFormat ? true : false;
				
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
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		JButton button = new JButton(Labels.CREATE);
		buttonPanel.add(button);
		
		button.addActionListener(e2 -> {
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
					int counter = 0;
					
					for(int i=0; i<birthDateTextField.getText().length(); i++) {
						if(birthDateTextField.getText().charAt(i) == '-') {
							counter++;
							if(counter > 2) {
								break;
							}
						}
					}
					
					if(counter == 2) {
						String[] pieces = birthDateTextField.getText().split("-");
						new Date(Integer.parseInt(pieces[0]), Integer.parseInt(pieces[1]), Integer.parseInt(pieces[2]));
					} else {
						throw new Exception();
					}
					
					String[] pieces = departmentTextField.getText().split(", ");
					ArrayList<String> department = new ArrayList<>();
					for(int i=0; i<pieces.length; i++) {
						department.add(pieces[i]);
					}
				
					if(true) {
						Referent referent = new Referent(
															nameTextField.getText(),
															ehaTextField.getText(),
															null,
															addressTextField.getText(),
															null
														);
						controller.createAccount(referent);
					} /*else if(true) {
						Professor professor = new Professor(
								nameTextField.getText(),
								ehaTextField.getText(),
								new Date(birthDateTextField.getText()),
								addressTextField.getText(),
								department
							);
						controller.createAccount(professor);
					} else {
						Student student = new Student(
								nameTextField.getText(),
								ehaTextField.getText(),
								new Date(birthDateTextField.getText()),
								addressTextField.getText(),
								department
							);
						controller.createAccount(student);
					}*/
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
				} catch(SQLException exception) {
					JOptionPane.showMessageDialog(
							  this,
							  Labels.USER_ALREADY_EXISTS,
							  Labels.ERROR,
							  JOptionPane.ERROR_MESSAGE);
					exception.printStackTrace();
				} catch (Exception exception) {
					JOptionPane.showMessageDialog(
							  inputPanel,
							  Labels.WRONG_DATE_FORMAT,
							  Labels.ERROR,
							  JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		add(inputPanel);
		add(buttonPanel);
	}
	
}
