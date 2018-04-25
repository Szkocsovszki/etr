package view;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import controller.ETRController;
import dao.model.Account;

public class ChangePassword extends JPanel {
	private static final long serialVersionUID = 3169541212541291110L;

	public ChangePassword(ETRController controller, Account current) {
		setLayout(new GridLayout(2, 1));

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
		JButton modifyButton = new JButton(Labels.MODIFY);
		buttonPanel.add(modifyButton);
		
		modifyButton.addActionListener(e -> {
			Account acc = null;
			try {
				acc = controller.logIn(current.getEha(), String.valueOf(oldPasswordField.getPassword()));
			}catch(Exception ex) {}
			
			if(acc != null) {
				
				if(!(String.valueOf(newPasswordField.getPassword())).equals(String.valueOf(confirmPasswordField.getPassword())) ) {
					JOptionPane.showMessageDialog(
							  this,
							  Labels.NEW_PASSWORD_DOES_NOT_MATCH_CONFIRMED_PASSWORD,
							  Labels.ERROR,
							  JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						controller.changePassword(current.getEha(), String.valueOf(newPasswordField.getPassword()));
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
			}else {
				JOptionPane.showMessageDialog(
						  this,
						  Labels.OLD_PASSWORD_DOES_NOT_MATCH,
						  Labels.ERROR,
						  JOptionPane.ERROR_MESSAGE);
			}
		});

		add(inputPanel);
		add(buttonPanel);
	}

}
