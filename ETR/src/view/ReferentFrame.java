package view;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.ETRController;
import dao.model.Referent;

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
		JButton signOut = new JButton(Labels.SIGN_OUT);

		JButton createAccount = new JButton(Labels.CREATE_ACCOUNT);
		JButton modifyAccount = new JButton(Labels.MODIFY_ACCOUNT);
		JButton deleteAccount = new JButton(Labels.DELETE_ACCOUNT);

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
			JTextField birthDateTextField = new JTextField();
			JTextField addressTextField = new JTextField();
			JTextField departmentTextField = new JTextField();

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
																ehaTextField.getText(),
																new Date(birthDateTextField.getText()),
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
						birthDateTextField.setText("");
						addressTextField.setText("");
						departmentTextField.setText("");
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
					controller.getAccountToModify(ehaTextField.getText());
					
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
					
					JTextField nameTextField = new JTextField("asd");
					JTextField ehaTextField2 = new JTextField(ehaTextField.getText());
					JTextField birthDateTextField = new JTextField("asd");
					JTextField addressTextField = new JTextField("asd");
					JTextField departmentTextField = new JTextField("asd");
					
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
																		new Date(birthDateTextField.getText()),
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
