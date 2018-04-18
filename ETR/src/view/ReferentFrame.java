package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.sql.Date;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.ETRController;
import dao.model.Account;
import dao.model.Referent;
import dao.model.Student;

public class ReferentFrame extends JFrame {
	private static final long serialVersionUID = 1873297524242533738L;
	private ETRController controller;
	private ETRGUI gui;
	private JPanel workingPanel;

	public ReferentFrame(ETRController controller) {
		this.controller = controller;
		// setTitle();
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setJMenuBar(createMenuBar());

		workingPanel = new JPanel();
		workingPanel.setLayout(new GridBagLayout());
		add(workingPanel, BorderLayout.CENTER);

		setVisible(true);
	}

	@SuppressWarnings("deprecation")
	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();

		JButton createAccount = new JButton(Labels.CREATE_ACCOUNT);
		JButton modifyAccount = new JButton(Labels.MODIFY_ACCOUNT);
		JButton deleteAccount = new JButton(Labels.DELETE_ACCOUNT);
		JButton changePassword = new JButton(Labels.CHANGE_PASSWORD);
		JButton signOut = new JButton(Labels.SIGN_OUT);

		createAccount.addActionListener(e -> {
			workingPanel.removeAll();

			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(2, 1));
			
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
					} else {
						int counter = 0;
						
						for(int i=0; i<birthDateTextField.getText().length(); i++) {
							if(birthDateTextField.getText().charAt(i) == '/') {
								counter++;
								if(counter > 2) {
									break;
								}
							}
						}
						
						if(counter != 2) {
							JOptionPane.showMessageDialog(
									  inputPanel,
									  Labels.WRONG_DATE_FORMAT,
									  Labels.ERROR,
									  JOptionPane.ERROR_MESSAGE);
						}
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
					String[] pieces = departmentTextField.getText().split(", ");
					ArrayList<String> department = new ArrayList<>();
					for(int i=0; i<pieces.length; i++) {
						department.add(pieces[i]);
					}
					try {
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
					} catch(Exception exception) {
						JOptionPane.showMessageDialog(
								  this,
								  Labels.USER_ALREADY_EXISTS,
								  Labels.ERROR,
								  JOptionPane.ERROR_MESSAGE);
						exception.printStackTrace();
					}
				}
			});

			panel.add(inputPanel);
			panel.add(buttonPanel);

			workingPanel.add(panel);

			revalidate();
			repaint();
		});

		modifyAccount.addActionListener(e -> {
			modify();
		});

		deleteAccount.addActionListener(e -> {
			workingPanel.removeAll();

			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(2, 1));

			JPanel inputPanel = new JPanel();
			inputPanel.setLayout(new GridLayout(1, 2));
			JLabel deleteAccountLabel = new JLabel(Labels.EHA);
			JTextField deleteAccountTextField = new JTextField();
			inputPanel.add(deleteAccountLabel);
			inputPanel.add(deleteAccountTextField);
			
			JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
			JButton button = new JButton(Labels.DELETE);
			buttonPanel.add(button);
			
			button.addActionListener(e2 -> {
				if(deleteAccountTextField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(
							  this,
							  Labels.EMPTY_EHA_TEXTFIELD,
							  Labels.ERROR,
							  JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						controller.deleteAccount(deleteAccountTextField.getText());
						JOptionPane.showMessageDialog(
								  this,
								  Labels.USER_SUCCESSFULLY_DELETED,
								  Labels.INFORMATION,
								  JOptionPane.INFORMATION_MESSAGE);
						deleteAccountTextField.setText("");
					} catch(Exception exception) {
						JOptionPane.showMessageDialog(
								  this,
								  Labels.USER_DOES_NOT_EXIST,
								  Labels.ERROR,
								  JOptionPane.ERROR_MESSAGE);
						exception.printStackTrace();
					}
				}
			});

			panel.add(inputPanel);
			panel.add(buttonPanel);

			workingPanel.add(panel);

			revalidate();
			repaint();
		});
		
		changePassword.addActionListener(e -> {
			workingPanel.removeAll();

			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(2, 1));

			JPanel inputPanel = new JPanel();
			inputPanel.setLayout(new GridLayout(3, 2));
			JLabel oldPasswordLabel = new JLabel(Labels.OLD_PASSWORD);
			JLabel newPasswordLabel = new JLabel(Labels.NEW_PASSWORD);
			JLabel confirmPasswordLabel = new JLabel(Labels.CONFIRM_PASSWORD);
			JPasswordField oldPasswordField = new JPasswordField();
			JPasswordField newPasswordField = new JPasswordField();
			JPasswordField confirmPasswordField = new JPasswordField();
			inputPanel.add(oldPasswordLabel);
			inputPanel.add(oldPasswordField);
			inputPanel.add(newPasswordLabel);
			inputPanel.add(newPasswordField);
			inputPanel.add(confirmPasswordLabel);
			inputPanel.add(confirmPasswordField);
			
			JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
			JButton button = new JButton(Labels.MODIFY);
			buttonPanel.add(button);
			
			button.addActionListener(e2 -> {
				//r�gi jelsz� lek�r�se - MEGVAL�S�TANI!!!
				
				/*else*/ if(!newPasswordField.getText().equals(confirmPasswordField.getText())) {
					JOptionPane.showMessageDialog(
							  this,
							  Labels.NEW_PASSWORD_MUST_MATCH_CONFIRMED_PASSWORD,
							  Labels.ERROR,
							  JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						controller.changePassword(/*jelsz� tov�bb�t�s*/);
						JOptionPane.showMessageDialog(
								  this,
								  Labels.PASSWORD_SUCCESSFULLY_CHANGED,
								  Labels.INFORMATION,
								  JOptionPane.INFORMATION_MESSAGE);
						oldPasswordField.setText("");
						newPasswordField.setText("");
						confirmPasswordField.setText("");
					} catch (Exception exception) {
						JOptionPane.showMessageDialog(
								  this,
								  Labels.UNSUCCESSFULLY_PASSWORD_CHANGE,
								  Labels.ERROR,
								  JOptionPane.ERROR_MESSAGE);
					}
				}
			});

			panel.add(inputPanel);
			panel.add(buttonPanel);

			workingPanel.add(panel);

			revalidate();
			repaint();
		});

		signOut.addActionListener(e -> {
			dispose();
			gui = new ETRGUI(controller);
			gui.startGUI();
		});

		menuBar.add(createAccount);
		menuBar.add(modifyAccount);
		menuBar.add(deleteAccount);

		signOut.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		menuBar.add(Box.createHorizontalGlue());
		menuBar.add(changePassword);
		menuBar.add(signOut);

		return menuBar;
	}

	@SuppressWarnings("deprecation")
	private void modify() {
		workingPanel.removeAll();

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 1));

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
		
		button.addActionListener(e2 -> {
			if(ehaTextField.getText().isEmpty()) {
				JOptionPane.showMessageDialog(
						  this,
						  Labels.EMPTY_EHA_TEXTFIELD,
						  Labels.ERROR,
						  JOptionPane.ERROR_MESSAGE);
			} else {
				try {
					Account account = controller.getAccountToModify(ehaTextField.getText());
					
					String asd = "asd";
					ArrayList<String> asd2 = new ArrayList<>();
					asd2.add(asd);
					//asd2.add(asd);
					//asd2.add(asd);
					/*SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					Date date = sdf.parse("20/02/2002");*/
					Date date = new Date(2002, 02, 20);
					account = new Student(
											"asd", 
											ehaTextField.getText(),
											date,
											"asd",
											asd2
										 );
					
					workingPanel.removeAll();

					JPanel panel2 = new JPanel();
					panel2.setLayout(new GridLayout(2, 1));
					
					JPanel inputPanel2 = new JPanel();
					inputPanel2.setLayout(new GridLayout(5, 2));

					JLabel nameLabel = new JLabel(Labels.NAME);
					JLabel ehaLabel2 = new JLabel(Labels.EHA);
					JLabel birthDateLabel = new JLabel(Labels.BIRTH_DATE);
					JLabel addressLabel = new JLabel(Labels.ADDRESS);
					JLabel departmentLabel = new JLabel(Labels.DEPARTMENT);
					/*JTextField nameTextField = new JTextField(acc.getName());
					JTextField ehaTextField2 = new JTextField(ehaTextField.getText());
					JTextField birthDateTextField = new JTextField(acc.getBirthDate().toString());
					JTextField addressTextField = new JTextField(acc.getAddress());
					JTextField departmentTextField = new JTextField(acc.getDepartment().toString());*/
					JTextField nameTextField = new JTextField(account.getName());
					JTextField ehaTextField2 = new JTextField(account.getEha());
					
					Date date2 = account.getBirthDate();
					String dateToWrite = "";
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
					dateToWrite = dateToWrite + simpleDateFormat.format(date2) + "/";
					simpleDateFormat = new SimpleDateFormat("MM");
					dateToWrite = dateToWrite + simpleDateFormat.format(date2) + "/";
					simpleDateFormat = new SimpleDateFormat("dd");
					dateToWrite = dateToWrite + simpleDateFormat.format(date2);
					
					JTextField birthDateTextField = new JTextField(dateToWrite);
					JTextField addressTextField = new JTextField(account.getAddress());
					
					ArrayList<String> departmentList = account.getDepartment();
					String departmentToWrite = "";
					
					for(String d : departmentList) {
						departmentToWrite = departmentToWrite + d + ", ";
					}
					departmentToWrite = departmentToWrite.substring(0, departmentToWrite.length()-2);
					
					JTextField departmentTextField = new JTextField(departmentToWrite);
					
					ehaTextField2.setEditable(false);

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
					
					button2.addActionListener(e3 -> {
						if(nameTextField.getText().isEmpty()) {
							JOptionPane.showMessageDialog(
									  this,
									  Labels.EMPTY_NAME_TEXTFIELD,
									  Labels.ERROR,
									  JOptionPane.ERROR_MESSAGE);
						} else if(birthDateTextField.getText().isEmpty()) {
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
						} else if(departmentTextField.getText().isEmpty()) {
							JOptionPane.showMessageDialog(
									  this,
									  Labels.EMPTY_DEPARTMENT_TEXTFIELD,
									  Labels.ERROR,
									  JOptionPane.ERROR_MESSAGE);
						} else {
							ArrayList<String> department = new ArrayList<>();
							department.add(departmentTextField.getText());
							try {
								if(true) {
									Referent referent = new Referent(
																		nameTextField.getText(),
																		ehaTextField2.getText(),
																		null,
																		addressTextField.getText(),
																		null
																	);
									controller.modifyAccount(referent);
								} /*else if(true) {
									Professor professor = new Professor(
											nameTextField.getText(),
											ehaTextField2.getText(),
											new Date(birthDateTextField.getText()),
											addressTextField.getText(),
											department
										);
									controller.modifyAccount(professor);
								} else {
									Student student = new Student(
											nameTextField.getText(),
											ehaTextField2.getText(),
											new Date(birthDateTextField.getText()),
											addressTextField.getText(),
											department
										);
									controller.modifyAccount(student);
								}*/
								JOptionPane.showMessageDialog(
										  this,
										  Labels.USERS_DATA_SUCCESSFULLY_MODIFIED,
										  Labels.INFORMATION,
										  JOptionPane.INFORMATION_MESSAGE);
								modify();
							} catch(Exception exception) {
								JOptionPane.showMessageDialog(
										  this,
										  Labels.MODIFICATION_DID_NOT_SUCCEDED,
										  Labels.ERROR,
										  JOptionPane.ERROR_MESSAGE);
								exception.printStackTrace();
							}
						}
					});

					panel2.add(inputPanel2);
					panel2.add(buttonPanel2);

					workingPanel.add(panel2);

					revalidate();
					repaint();
				} catch (Exception exception) {
					JOptionPane.showMessageDialog(
							  this,
							  Labels.USER_DOES_NOT_EXIST,
							  Labels.ERROR,
							  JOptionPane.ERROR_MESSAGE);
					exception.printStackTrace();
					modify();
				}
			}
		});

		panel.add(inputPanel);
		panel.add(buttonPanel);

		workingPanel.add(panel);

		revalidate();
		repaint();
		
	}
}
