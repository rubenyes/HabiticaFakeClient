package presentation;

import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.border.EmptyBorder;

import java.awt.FlowLayout;
import java.awt.BorderLayout;

import logic.Task;

import javax.swing.border.LineBorder;

import java.awt.Color;
import java.util.Calendar;

import javax.swing.border.CompoundBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TaskPanel extends JPanel {
	
	private Task task;
	
	private JPanel panel;
	private JCheckBox chckbxCompleted;
	
	/**
	 * Create the panel.
	 */
	public TaskPanel(Task task) {
		this.task = task;
		
		setLayout(new GridLayout(0, 1, 0, 0));
		setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new EmptyBorder(5, 2, 5, 2)));
		setMaximumSize(new Dimension((int)Integer.MAX_VALUE, 130));
		
		JLabel lblTitle = new JLabel(task.getTitle());
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 14));
		add(lblTitle);
		
		JLabel lblDescription = new JLabel(task.getDescription());
		add(lblDescription);
		
		JLabel lblLblreminder = new JLabel("Reminder: "+task.getReminderString());
		add(lblLblreminder);
		
		JLabel lblDate = new JLabel("Date: "+task.getDateString());
		lblDate.setFont(new Font("Tahoma", Font.BOLD, 12));
		add(lblDate);
		
		panel = new JPanel();
		add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Controller.givemeController().deleteTask(task);
			}
		});
		panel.add(btnDelete, BorderLayout.EAST);
		
		chckbxCompleted = new JCheckBox("Completed");
		chckbxCompleted.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Controller.givemeController().completedTask(task, chckbxCompleted.isSelected());
				defineColor();
			}
		});
		panel.add(chckbxCompleted, BorderLayout.WEST);

		//Paint
		defineColor();
	}
	
	private void defineColor(){
		if(chckbxCompleted.isSelected()){
			setBackground(Color.GREEN);
			panel.setBackground(Color.GREEN);
			chckbxCompleted.setBackground(Color.GREEN);			
		}
		else{
			Calendar today = Calendar.getInstance();
			if(today.after(task.getDate())){
				setBackground(Color.RED);
				panel.setBackground(Color.RED);
				chckbxCompleted.setBackground(Color.RED);
			}
			else if(today.after(task.getReminder())){
				setBackground(Color.YELLOW);
				panel.setBackground(Color.YELLOW);
				chckbxCompleted.setBackground(Color.YELLOW);
			}
		}
	}

}
