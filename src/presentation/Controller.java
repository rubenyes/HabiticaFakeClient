package presentation;

import java.util.List;

import persistance.DAL;
import logic.Account;
import logic.Habit;
import logic.HabitsReport;
import logic.Main;
import logic.Task;
import logic.TasksReport;

public class Controller {
	private Main main;	
	private static Controller controller;

	private Controller() {
		main = new Main();
	}

	public static Controller givemeController() {
		if (controller == null) controller = new Controller();
		return controller;
	}
	
	public String getIdUser(){
		return main.getAccount().getEmail();
	}
	
	public boolean isCorrectLogin(String email, String password){
		return main.correctLogin(email, password);
	}
	
	public boolean registerUser(Account account){
		return main.registerUser(account);
	}
	
	public List<Task> getTasksList(){
		return main.getTasks();
	}
	
	public List<Habit> getHabitsList(){
		return main.getHabits();
	}
	
	public boolean createTask(Task task){
		int index = main.createTask(task);
		if(index != -1){
			AppJFrame.addTask(task, index);
			return true;
		}
		return false;
	}
	
	public void deleteTask(Task task){
		int index = main.deleteTask(task);
		AppJFrame.removeTask(index);
	}
	
	public boolean createHabit(Habit habit){
		int index = main.createHabit(habit);
		if(index != -1){
			AppJFrame.addHabit(habit, index);
			return true;
		}
		return false;
	}
	
	public void deleteHabit(Habit habit){
		int index = main.deleteHabit(habit);
		AppJFrame.removeHabit(index);
	}
	
	public void incrementHabitScore(Habit habit){
		main.incrementHabitScore(habit);
	}
	
	public void decrementHabitScore(Habit habit){
		main.decrementHabitScore(habit);
	}
	
	public void completedTask(Task task, boolean completed){
		main.completedTask(task, completed);
	}
	
	public void deleteAccount(){
		main.deleteAccount();
	}
	
	public TasksReport generateTasksReport(){
		return main.generateTasksReport();
	}
	
	public HabitsReport generateHabitsReport(){
		return main.generateHabitsReport();
	}
}
