package presentation;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.Font;

import logic.Difficulty;
import logic.Habit;

import javax.swing.SwingConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class HabitPanel extends JPanel {

	private Habit habit;
	
	private JPanel panelButtons;
	private JPanel panelInfo;
	private JPanel panelRight;
	private JLabel lblScore;
	
	/**
	 * Create the panel.
	 */
	public HabitPanel(Habit habit) {
		this.habit = habit;
		
		setLayout(new BorderLayout(0, 0));
		setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new EmptyBorder(5, 2, 5, 2)));
		setMaximumSize(new Dimension((int)Integer.MAX_VALUE, 70));
		
		panelButtons = new JPanel();
		add(panelButtons, BorderLayout.WEST);
		panelButtons.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton buttonIncrement = new JButton("+");
		buttonIncrement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Controller.givemeController().incrementHabitScore(habit);
				lblScore.setText(habit.getScore()+"");
				defineBackgroundColor();
			}
		});
		panelButtons.add(buttonIncrement);
		
		JButton buttonDecrement = new JButton("-");
		buttonDecrement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Controller.givemeController().decrementHabitScore(habit);
				lblScore.setText(habit.getScore()+"");
				defineBackgroundColor();
			}
		});
		panelButtons.add(buttonDecrement);
		
		panelInfo = new JPanel();
		add(panelInfo, BorderLayout.CENTER);
		panelInfo.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel lblDescription = new JLabel(habit.getDescription());
		lblDescription.setFont(new Font("Tahoma", Font.BOLD, 14));
		panelInfo.add(lblDescription);
		
		JLabel lblType = new JLabel(habit.getTypeString());
		panelInfo.add(lblType);
		
		JLabel lblDifficulty = new JLabel(Difficulty.convertToString(habit.getDifficulty()));
		panelInfo.add(lblDifficulty);
		
		panelRight = new JPanel();
		add(panelRight, BorderLayout.EAST);
		panelRight.setLayout(new GridLayout(0, 1, 0, 0));
		
		lblScore = new JLabel(habit.getScore()+"");
		lblScore.setHorizontalAlignment(SwingConstants.CENTER);
		panelRight.add(lblScore);
		lblScore.setFont(new Font("Tahoma", Font.BOLD, 20));
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Controller.givemeController().deleteHabit(habit);
			}
		});
		panelRight.add(btnDelete);

		defineBackgroundColor();
	}
	
	public void defineBackgroundColor(){
		int score = habit.getScore();
		if(score < 0){
			setBackground(Color.RED);
			panelInfo.setBackground(Color.RED);
			panelRight.setBackground(Color.RED);			
		}
		else if(score < 10){
			setBackground(Color.ORANGE);
			panelInfo.setBackground(Color.ORANGE);
			panelRight.setBackground(Color.ORANGE);	
		}
		else if(score < 40){
			setBackground(Color.YELLOW);
			panelInfo.setBackground(Color.YELLOW);
			panelRight.setBackground(Color.YELLOW);	
		}
		else if(score < 50){
			setBackground(Color.GREEN);
			panelInfo.setBackground(Color.GREEN);
			panelRight.setBackground(Color.GREEN);	
		}
		else{
			setBackground(Color.BLUE);
			panelInfo.setBackground(Color.BLUE);
			panelRight.setBackground(Color.BLUE);	
		}
	}

}
