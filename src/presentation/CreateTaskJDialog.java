package presentation;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

import java.util.Date;
import java.util.Calendar;

import javax.swing.JTextField;

import exceptions.EmptyFieldException;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import logic.CompletedState;
import logic.Task;
import javax.swing.SpinnerNumberModel;

public class CreateTaskJDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldDescription;
	private JTextField textFieldTitle;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		try {
			CreateTaskJDialog dialog = new CreateTaskJDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * Create the dialog.
	 */
	public CreateTaskJDialog() {
		setBounds(100, 100, 314, 229);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblTitle = new JLabel("Title:*");
		lblTitle.setBounds(10, 11, 46, 14);
		contentPanel.add(lblTitle);
		
		JLabel lblDescription = new JLabel("Description:*");
		lblDescription.setBounds(10, 36, 88, 14);
		contentPanel.add(lblDescription);
		
		JLabel lblReminder = new JLabel("Reminder time:*");
		lblReminder.setBounds(10, 114, 88, 17);
		contentPanel.add(lblReminder);
		
		JLabel lblDate = new JLabel("Date:*");
		lblDate.setBounds(10, 64, 46, 14);
		contentPanel.add(lblDate);
		
		JSpinner spinnerReminderTime = new JSpinner();
		spinnerReminderTime.setModel(new SpinnerDateModel(new Date(1461621600000L), null, null, Calendar.MINUTE));
		spinnerReminderTime.setEditor(new JSpinner.DateEditor(spinnerReminderTime, "HH:mm"));
		spinnerReminderTime.setBounds(120, 112, 165, 20);
		contentPanel.add(spinnerReminderTime);
		
		JSpinner spinnerDate = new JSpinner();
		spinnerDate.setModel(new SpinnerDateModel(new Date(1461621600000L), null, null, Calendar.DAY_OF_YEAR));
		spinnerDate.setEditor(new JSpinner.DateEditor(spinnerDate, "dd/MM/yyyy"));
		spinnerDate.setBounds(120, 61, 165, 20);
		contentPanel.add(spinnerDate);
		
		textFieldDescription = new JTextField();
		textFieldDescription.setBounds(120, 33, 165, 20);
		contentPanel.add(textFieldDescription);
		textFieldDescription.setColumns(10);
		
		textFieldTitle = new JTextField();
		textFieldTitle.setBounds(120, 8, 165, 20);
		contentPanel.add(textFieldTitle);
		textFieldTitle.setColumns(10);
		
		JLabel lblReminderDaysAgo = new JLabel("Reminder days ago:*");
		lblReminderDaysAgo.setBounds(10, 89, 117, 14);
		contentPanel.add(lblReminderDaysAgo);
		
		JSpinner spinnerReminder = new JSpinner();
		spinnerReminder.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		spinnerReminder.setBounds(120, 86, 165, 20);
		contentPanel.add(spinnerReminder);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
			{
				JButton okButton = new JButton("Create task");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						try{
							String title = textFieldTitle.getText();
							String desc = textFieldDescription.getText();
							
							if(title.equals("") || desc.equals(""))
								throw new EmptyFieldException();
							
							Date date = (Date)spinnerDate.getValue();
							Date reminder = (Date)spinnerReminderTime.getValue();
							
							Calendar dateCal = Calendar.getInstance();
							dateCal.setTime(date);
							int reminderDaysAgo = (int)spinnerReminder.getValue();
							Calendar reminderCal = Calendar.getInstance();
							reminderCal.setTime(reminder);
							String idUser = Controller.givemeController().getIdUser();
							
							Task task = new Task(idUser, title, desc, dateCal, reminderDaysAgo, reminderCal, CompletedState.NOTYET);
							
							boolean res = Controller.givemeController().createTask(task);
							
							if(res){
								JOptionPane.showMessageDialog(contentPanel, "New task created successfully!");
								dispose();
							}
							else{
								JOptionPane.showMessageDialog(contentPanel,
									    "The new task could not be created.",
									    "Error",
									    JOptionPane.ERROR_MESSAGE);
							}
						}catch(EmptyFieldException e){
							JOptionPane.showMessageDialog(contentPanel, "Name and description are mandatory fields.");
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
