package presentation;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JDialog;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JSplitPane;
import javax.swing.JSeparator;
import javax.swing.JLabel;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JScrollPane;

import logic.CompletedState;
import logic.Difficulty;
import logic.Habit;
import logic.HabitsReport;
import logic.Task;
import logic.TasksReport;

import java.awt.ScrollPane;
import java.awt.Component;

import javax.swing.Box;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AppJFrame extends JFrame {

	private JPanel contentPane;
	private static JPanel tasksPanel;
	private static JPanel habitsPanel;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppJFrame frame = new AppJFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public AppJFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 601, 399);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnAccount = new JMenu("Account");
		menuBar.add(mnAccount);
		
		JMenuItem mntmDeleteAccount = new JMenuItem("Delete account");
		mntmDeleteAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Controller.givemeController().deleteAccount();
				dispose();
				LoginJDialog dialog = new LoginJDialog();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
		
		JMenuItem mntmLogOut = new JMenuItem("Log out");
		mntmLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				LoginJDialog dialog = new LoginJDialog();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
		mnAccount.add(mntmLogOut);
		mnAccount.add(mntmDeleteAccount);
		
		JMenu mnTasks = new JMenu("Tasks");
		menuBar.add(mnTasks);
		
		JMenuItem mntmCreateTask = new JMenuItem("Create task ...");
		mntmCreateTask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreateTaskJDialog dialog = new CreateTaskJDialog();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
		mnTasks.add(mntmCreateTask);
		
		JMenuItem mntmTasksReport = new JMenuItem("Tasks report");
		mntmTasksReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TasksReport report = Controller.givemeController().generateTasksReport();
				JOptionPane.showMessageDialog(AppJFrame.this,
					    report.toString(),
					    "Tasks report",
					    JOptionPane.PLAIN_MESSAGE);
			}
		});
		mnTasks.add(mntmTasksReport);
		
		JMenu mnHabits = new JMenu("Habits");
		menuBar.add(mnHabits);
		
		JMenuItem mntmCreateHabit = new JMenuItem("Create habit ...");
		mntmCreateHabit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreateHabitJDialog dialog = new CreateHabitJDialog();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
		mnHabits.add(mntmCreateHabit);
		
		JMenuItem mntmHabitsReport = new JMenuItem("Habits report");
		mntmHabitsReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HabitsReport report = Controller.givemeController().generateHabitsReport();
				/*Habit best = new Habit("a@a.com", "descripcin del habito", Difficulty.EASY, 1, 23);
				Habit worst = best;
				HabitsReport report = new HabitsReport(new int[]{0,1,2,3,4}, best, worst);*/
				JOptionPane.showMessageDialog(AppJFrame.this,
					    report.toString(),
					    "Habits report",
					    JOptionPane.PLAIN_MESSAGE);
			}
		});
		mnHabits.add(mntmHabitsReport);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblTasks = new JLabel("TASKS");
		lblTasks.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblTasks);
		lblTasks.setVerticalAlignment(SwingConstants.TOP);
		
		
		JLabel lblHabits = new JLabel("HABITS");
		lblHabits.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblHabits);
		lblHabits.setVerticalAlignment(SwingConstants.TOP);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 2, 0, 0));
		
		tasksPanel = new JPanel();
		JScrollPane tasksScrollPane = new JScrollPane(tasksPanel);
		tasksPanel.setLayout(new BoxLayout(tasksPanel, BoxLayout.Y_AXIS));
		panel_1.add(tasksScrollPane);
		
		habitsPanel = new JPanel();
		JScrollPane habitsScrollPane = new JScrollPane(habitsPanel);
		habitsPanel.setLayout(new BoxLayout(habitsPanel, BoxLayout.Y_AXIS));
		panel_1.add(habitsScrollPane);
		
		loadTasks();
		loadHabits();
	}
	
	public static void addTask(Task task, int index){
		tasksPanel.add(new TaskPanel(task), index);
		tasksPanel.revalidate();
		tasksPanel.repaint();
	}
	
	public static void addHabit(Habit habit, int index){
		habitsPanel.add(new HabitPanel(habit), index);
		habitsPanel.revalidate();
		habitsPanel.repaint();
	}
	
	public static void removeTask(int index){
		tasksPanel.remove(index);
		tasksPanel.revalidate();
		tasksPanel.repaint();
	}
	
	public static void removeHabit(int index){
		habitsPanel.remove(index);
		habitsPanel.revalidate();
		habitsPanel.repaint();
	}
	
	private void loadTasks(){
		List<Task> list = Controller.givemeController().getTasksList();
		//List<Task> list = fakeTasks();
		for(Task task : list){
			tasksPanel.add(new TaskPanel(task));
		}
	}

	private void loadHabits(){
		List<Habit> list = Controller.givemeController().getHabitsList();
		//List<Habit> list = fakeHabits();
		for(Habit habit : list){
			habitsPanel.add(new HabitPanel(habit));
		}
	}
	
	/*private List<Task> fakeTasks(){
		List<Task> list = new ArrayList<Task>();
		for(int i=0; i<10; i++){
			Task t = new Task("a@a.com", "hacer la tarea de ISW", "descripcin de la tarea", Calendar.getInstance(), 2, Calendar.getInstance(), CompletedState.NOTYET);
			list.add(t);
		}
		return list;
	}
	
	private List<Habit> fakeHabits(){
		List<Habit> list = new ArrayList<Habit>();
		for(int i=0; i<10; i++){
			Habit h = new Habit("a@a.com", "descripcin del habito", Difficulty.EASY, 1, 23);
			list.add(h);
		}
		return list;
	}*/
}
