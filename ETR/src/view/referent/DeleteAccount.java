package view.referent;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.ETRController;
import view.Labels;

public class DeleteAccount extends JPanel {
	private static final long serialVersionUID = 6298961839489643025L;

	public DeleteAccount(ETRController controller) {
		setLayout(new GridLayout(2, 1));

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
		
		button.addActionListener(e -> {
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

		add(inputPanel);
		add(buttonPanel);
	}

}
