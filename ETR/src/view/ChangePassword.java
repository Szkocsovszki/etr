package view;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import controller.ETRController;

public class ChangePassword extends JPanel {
	private static final long serialVersionUID = 3169541212541291110L;

	@SuppressWarnings("deprecation")
	public ChangePassword(ETRController controller) {
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
		JButton button = new JButton(Labels.MODIFY);
		buttonPanel.add(button);
		
		button.addActionListener(e2 -> {
			//r�gi jelsz� lek�r�se - MEGVAL�S�TANI!!!
			
			/*else*/ if(!newPasswordField.getText().equals(confirmPasswordField.getText())) {
				JOptionPane.showMessageDialog(
						  this,
						  Labels.NEW_PASSWORD_DOES_NOT_MATCH_CONFIRMED_PASSWORD,
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

		add(inputPanel);
		add(buttonPanel);
	}

}
