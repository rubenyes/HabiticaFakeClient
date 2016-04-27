package logic;

import java.util.Collections;
import java.util.List;

import persistance.DAL;

public class Main {

	private Account account;
	private List<Task> tasks;
	private List<Habit> habits;
	
	public Main(){}
	
	public boolean correctLogin(String email, String password){
		Account a = DAL.givemeDAL().getAccount(email, password);
		if(a != null){
			setAccount(a);
			tasks = DAL.givemeDAL().getTasksList(account.getEmail());
			Collections.sort(tasks);
			habits = DAL.givemeDAL().getHabitsList(account.getEmail());
			Collections.sort(habits);
			return true;
		}
		return false;
	}
	
	public boolean registerUser(Account account){
		return DAL.givemeDAL().createAccount(account);
	}
	
	/** Return the index where was added the new task, or -1 if the createTask failed **/
	public int createTask(Task task){
		boolean res = DAL.givemeDAL().createTask(task);
		int index = -1;
		if(res){
			if(tasks.size()==0) return 0;
			for(int i=tasks.size()-1; i>=0; i--){
				if(tasks.get(i).compareTo(task)<=0){
					tasks.add(task);
					index = i+1;
					break;
				}
			}
		}
		return index;
	}
	
	public int deleteTask(Task task){
		int index = tasks.indexOf(task);
		tasks.remove(task);
		DAL.givemeDAL().deleteTask(task.getId());
		return index;
	}
	
	/** Return the index where was added the new habit, or -1 if the createHabit failed **/
	public int createHabit(Habit habit){
		boolean res = DAL.givemeDAL().createHabit(habit);
		int index = -1;
		if(res){
			if(habits.size()==0) return 0;
			for(int i=habits.size()-1; i>=0; i--){
				if(habits.get(i).compareTo(habit)<=0){
					habits.add(habit);
					index = i+1;
					break;
				}
			}
		}
		return index;
	}
	
	public int deleteHabit(Habit habit){
		int index = habits.indexOf(habit);
		habits.remove(index);
		DAL.givemeDAL().deleteHabit(habit.getId());
		return index;
	}
	
	public void incrementHabitScore(Habit habit){
		habit.incrementScore();
		DAL.givemeDAL().incrementHabitScore(habit.getId());
	}
	
	public void decrementHabitScore(Habit habit){
		habit.decrementScore();
		DAL.givemeDAL().decrementHabitScore(habit.getId());
	}
	
	public void completedTask(Task task, boolean completed){
		task.setCompleted(completed);
		DAL.givemeDAL().completedTask(task.getId(), completed);
	}
	
	public void deleteAccount(){
		DAL.givemeDAL().deleteAccount(account.getEmail());
	}
	
	public TasksReport generateTasksReport(){
		return DAL.givemeDAL().generateTasksReport(account.getEmail());
	}
	
	public HabitsReport generateHabitsReport(){
		return DAL.givemeDAL().generateHabitsReport(account.getEmail());
	}
	
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public List<Habit> getHabits() {
		return habits;
	}
	
}
