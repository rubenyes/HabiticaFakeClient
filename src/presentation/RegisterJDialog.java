package presentation;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SpinnerDateModel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Label;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

import exceptions.EmptyFieldException;
import exceptions.PasswordsDoNotMatchException;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import logic.Account;

import javax.swing.JSpinner;

public class RegisterJDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JPasswordField passwordField1;
	private JPasswordField passwordField2;
	private JTextField textFieldEmail;
	private JTextField textFieldName;
	private JSpinner spinner;
	private SpinnerDateModel spinnerModel;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		try {
			RegisterJDialog dialog = new RegisterJDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * Create the dialog.
	 */
	public RegisterJDialog() {
		setBounds(100, 100, 242, 207);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblName = new JLabel("Name:*");
			lblName.setBounds(10, 11, 46, 14);
			contentPanel.add(lblName);
		}
		{
			JLabel lblEmail = new JLabel("Email:*");
			lblEmail.setBounds(10, 36, 46, 14);
			contentPanel.add(lblEmail);
		}
		
		JLabel lblBirthday = new JLabel("Birthday:");
		lblBirthday.setBounds(10, 61, 46, 14);
		contentPanel.add(lblBirthday);
		
		JLabel lblPassword = new JLabel("Password:*");
		lblPassword.setBounds(10, 86, 70, 14);
		contentPanel.add(lblPassword);
		
		JLabel lblPassword_1 = new JLabel("Password:*");
		lblPassword_1.setBounds(10, 111, 70, 20);
		contentPanel.add(lblPassword_1);
		
		passwordField1 = new JPasswordField();
		passwordField1.setBounds(90, 86, 126, 20);
		contentPanel.add(passwordField1);
		
		passwordField2 = new JPasswordField();
		passwordField2.setBounds(90, 111, 126, 20);
		contentPanel.add(passwordField2);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setBounds(90, 36, 126, 20);
		contentPanel.add(textFieldEmail);
		textFieldEmail.setColumns(10);
		
		textFieldName = new JTextField();
		textFieldName.setBounds(90, 11, 126, 20);
		contentPanel.add(textFieldName);
		textFieldName.setColumns(10);
		
		spinner = new JSpinner();
		spinnerModel = new SpinnerDateModel(new Date(1461621600000L), null, null, Calendar.DAY_OF_YEAR);
		spinner.setModel(spinnerModel);
		spinner.setEditor(new JSpinner.DateEditor(spinner, "dd/MM/yyyy"));
		spinner.setBounds(90, 61, 126, 20);
		contentPanel.add(spinner);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Create account");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						try {
							String name = textFieldName.getText();
							String email = textFieldEmail.getText();
							String pass1 = passwordField1.getText();
							String pass2 = passwordField2.getText();							

							if(name.equals("") || email.equals("") || pass1.equals("") || pass2.equals(""))
								throw new EmptyFieldException();
								
							if(!pass1.equals(pass2))
								throw new PasswordsDoNotMatchException();
							
							Account account = null;
							
							Date birth = (Date) spinner.getValue();
							if(birth.compareTo(new Date(1461621600000L)) != 0){
								SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");						
								Calendar birthday = Calendar.getInstance();
								birthday.setTime(birth);
								account = new Account(name, email, pass1, birthday);
							}
							else{
								account = new Account(name, email, pass1);
							}
							
							boolean res = Controller.givemeController().registerUser(account);
							if(res){
								JOptionPane.showMessageDialog(contentPanel, "New account registered successfully!");
								dispose();
							}
							else{
								JOptionPane.showMessageDialog(contentPanel,
									    "The new account could not be registered.",
									    "Error",
									    JOptionPane.ERROR_MESSAGE);
							}
						} catch (EmptyFieldException e) {
							JOptionPane.showMessageDialog(contentPanel, "Name, email and password are mandatory fields.");
						} catch (PasswordsDoNotMatchException e) {
							JOptionPane.showMessageDialog(contentPanel, "Passwords do not match.");
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
}
