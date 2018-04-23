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

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.ETRController;
import dao.model.Account;
import view.Labels;

public class ModifyAccount extends JPanel {
	private static final long serialVersionUID = 2027648580170751858L;

	public ModifyAccount(ETRController controller) {
		modify(controller);
	}
	
	private void modify(ETRController controller) {
		removeAll();
		setLayout(new GridLayout(2, 1));

		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(1, 2));
		JLabel ehaLabel = new JLabel(Labels.EHA);
		JTextField ehaTextField = new JTextField();
		inputPanel.add(ehaLabel);
		inputPanel.add(ehaTextField);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		JButton button = new JButton(Labels.SEARCH);
		buttonPanel.add(button);
		
		button.addActionListener(e -> {
			if(ehaTextField.getText().isEmpty()) {
				JOptionPane.showMessageDialog(
						  this,
						  Labels.EMPTY_EHA_TEXTFIELD,
						  Labels.ERROR,
						  JOptionPane.ERROR_MESSAGE);
			} else {
				try {
					Account account = controller.getAccountToModify(ehaTextField.getText());
					
					removeAll();
					
					JPanel inputPanel2 = new JPanel();
					inputPanel2.setLayout(new GridLayout(5, 2));

					JLabel nameLabel = new JLabel(Labels.NAME);
					JLabel ehaLabel2 = new JLabel(Labels.EHA);
					JLabel birthDateLabel = new JLabel(Labels.BIRTH_DATE);
					JLabel addressLabel = new JLabel(Labels.ADDRESS);
					JLabel departmentLabel = new JLabel(Labels.DEPARTMENT);

					JTextField nameTextField = new JTextField(account.getName());
					
					JTextField ehaTextField2 = new JTextField(account.getEha());
					ehaTextField2.setEditable(false);
					
					String[] correctedDate = account.getBirthDate().split(" ");
					JTextField birthDateTextField = new JTextField(correctedDate[0]);
					
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
					
					JTextField addressTextField = new JTextField(account.getAddress());
					
					JTextField departmentTextField;
					ArrayList<String> departmentList = account.getDepartment();
					
					String departmentToWrite = "";
					for(String d : departmentList) {
						departmentToWrite = departmentToWrite + d + ", ";
					}
					
					if(departmentToWrite.equals(null) || departmentToWrite.isEmpty()) {
						departmentTextField = new JTextField();
					} else {
						departmentToWrite = departmentToWrite.substring(0, departmentToWrite.length()-2);
						departmentTextField = new JTextField(departmentToWrite);
					}
					
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
					
					/*if(account instanceof Referent) {
						departmentTextField.setText("");
						departmentTextField.setEditable(false);
						departmentTextField.setFocusable(false);
					}*/

					inputPanel2.add(nameLabel);
					inputPanel2.add(nameTextField);
					inputPanel2.add(ehaLabel2);
					inputPanel2.add(ehaTextField2);
					inputPanel2.add(birthDateLabel);
					inputPanel2.add(birthDateTextField);
					inputPanel2.add(addressLabel);
					inputPanel2.add(addressTextField);
					inputPanel2.add(departmentLabel);
					inputPanel2.add(departmentTextField);
					
					JPanel buttonPanel2 = new JPanel();
					buttonPanel2.setLayout(new FlowLayout(FlowLayout.CENTER));
					JButton button2 = new JButton(Labels.MODIFY);
					buttonPanel2.add(button2);
					
					button2.addActionListener(e2 -> {
						if(nameTextField.getText().isEmpty()) {
							JOptionPane.showMessageDialog(
									  this,
									  Labels.EMPTY_NAME_TEXTFIELD,
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
								
								account.setName(nameTextField.getText());
								account.setBirthDate(birthDateTextField.getText());
								account.setAddress(addressTextField.getText());
								account.setDepartment(department);
								
								controller.modifyAccount(account);
								
								JOptionPane.showMessageDialog(
										  this,
										  Labels.USERS_DATA_SUCCESSFULLY_MODIFIED,
										  Labels.INFORMATION,
										  JOptionPane.INFORMATION_MESSAGE);
								modify(controller);
							} catch(SQLException exception) {
								JOptionPane.showMessageDialog(
										  this,
										  Labels.MODIFICATION_DID_NOT_SUCCEDED,
										  Labels.ERROR,
										  JOptionPane.ERROR_MESSAGE);
								exception.printStackTrace();
							} catch (DateTimeParseException exception) {
								JOptionPane.showMessageDialog(
										  inputPanel,
										  Labels.WRONG_DATE_FORMAT,
										  Labels.ERROR,
										  JOptionPane.ERROR_MESSAGE);
							}
						}
					});

					add(inputPanel2);
					add(buttonPanel2);
					revalidate();
					repaint();
				} catch (Exception exception) {
					JOptionPane.showMessageDialog(
							  this,
							  Labels.USER_DOES_NOT_EXIST,
							  Labels.ERROR,
							  JOptionPane.ERROR_MESSAGE);
					exception.printStackTrace();
					modify(controller);
				}
			}
		});

		add(inputPanel);
		add(buttonPanel);
		revalidate();
		repaint();
	}
	
}
