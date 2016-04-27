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

import logic.Habit;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import logic.Difficulty;

public class CreateHabitJDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldDescription;
	private JComboBox comboBoxType;
	private JComboBox comboBoxDifficulty;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		try {
			CreateHabitJDialog dialog = new CreateHabitJDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * Create the dialog.
	 */
	public CreateHabitJDialog() {
		setBounds(100, 100, 279, 165);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblDescription = new JLabel("Description:*");
		lblDescription.setBounds(10, 11, 88, 14);
		contentPanel.add(lblDescription);
		
				
		textFieldDescription = new JTextField();
		textFieldDescription.setBounds(96, 11, 157, 20);
		contentPanel.add(textFieldDescription);
		textFieldDescription.setColumns(10);
		
		JLabel lblType = new JLabel("Type:*");
		lblType.setBounds(10, 36, 88, 14);
		contentPanel.add(lblType);
		
		JLabel lblDifficulty = new JLabel("Difficulty:*");
		lblDifficulty.setBounds(10, 61, 88, 14);
		contentPanel.add(lblDifficulty);
		
		comboBoxType = new JComboBox();
		comboBoxType.setModel(new DefaultComboBoxModel(new String[] {"Good", "Bad", "Both"}));
		comboBoxType.setBounds(96, 36, 157, 20);
		contentPanel.add(comboBoxType);
		
		comboBoxDifficulty = new JComboBox();
		comboBoxDifficulty.setModel(new DefaultComboBoxModel(Difficulty.values()));
		comboBoxDifficulty.setBounds(96, 61, 157, 20);
		contentPanel.add(comboBoxDifficulty);
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
				JButton okButton = new JButton("Create habit");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						try{
							String desc = textFieldDescription.getText();
							
							if(desc.equals(""))
								throw new EmptyFieldException();
							
							String idUser = Controller.givemeController().getIdUser();
							Difficulty diff = (Difficulty)comboBoxDifficulty.getSelectedItem();
							int type = Habit.getTypeFromString((String)comboBoxType.getSelectedItem());
							Habit habit = new Habit(idUser, desc, diff, type);
							
							boolean res = Controller.givemeController().createHabit(habit);
							
							if(res){
								JOptionPane.showMessageDialog(contentPanel, "New habit created successfully!");
								dispose();
							}
							else{
								JOptionPane.showMessageDialog(contentPanel,
									    "The new habit could not be created.",
									    "Error",
									    JOptionPane.ERROR_MESSAGE);
							}
						}catch(EmptyFieldException e){
							JOptionPane.showMessageDialog(contentPanel, "Description is a mandatory field.");
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
