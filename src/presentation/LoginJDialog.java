package presentation;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

import persistance.DAL;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import logic.Account;

public class LoginJDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			LoginJDialog dialog = new LoginJDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public LoginJDialog() {
		setBounds(100, 100, 298, 120);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblEmail = new JLabel("Email:");
			lblEmail.setBounds(10, 11, 65, 14);
			contentPanel.add(lblEmail);
		}
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(10, 32, 65, 14);
		contentPanel.add(lblPassword);
		
		textField = new JTextField();
		textField.setBounds(85, 8, 187, 20);
		contentPanel.add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(85, 29, 187, 20);
		contentPanel.add(passwordField);
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new GridLayout(0, 2, 0, 0));
			{
				JButton registerButton = new JButton("Register");
				registerButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						RegisterJDialog dialog = new RegisterJDialog();
						dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
						dialog.setVisible(true);
					}
				});
				registerButton.setActionCommand("Cancel");
				buttonPane.add(registerButton);
			}
			{
				JButton loginButton = new JButton("Login");
				loginButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						String email = textField.getText();
						String password = passwordField.getText();
						boolean res = Controller.givemeController().isCorrectLogin(email, password);
						if(res){
							dispose();
							AppJFrame frame = new AppJFrame();
							frame.setVisible(true);
						}
						else{
							JOptionPane.showMessageDialog(contentPanel,
								    "The email or password are incorrect.",
								    "Login error",
								    JOptionPane.ERROR_MESSAGE);
						}
					}
				});
				loginButton.setActionCommand("OK");
				buttonPane.add(loginButton);
				getRootPane().setDefaultButton(loginButton);
			}
		}
	}
}
