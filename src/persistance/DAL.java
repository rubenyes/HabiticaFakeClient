package persistance;

import java.util.List;
import java.util.Map;

import logic.Account;
import logic.Habit;
import logic.HabitsReport;
import logic.Task;
import logic.TasksReport;

public class DAL {
	
	private static DAL dal;
	
	private IAccountsDAO accountsDAO;
	private IHabitsDAO habitsDAO;
	private ITasksDAO tasksDAO;
	
	private DAL(){
		accountsDAO = AccountsDAO.givemeDAO();
		habitsDAO = HabitsDAO.givemeDAO();
		tasksDAO = TasksDAO.givemeDAO();
	}
	
	public static DAL givemeDAL(){
		if(dal == null) dal = new DAL();
		//dal.setHosts(); //we set the hosts each time we access the persistance layer
		return dal;
	}
	
	public void setHosts(){
		Map<String, Map<String, String>> map = DnsDAO.getHosts();
		
		accountsDAO.setHost("http://"+map.get("accounts").get("host")+":"+map.get("account").get("port")+"/api/user/");
		tasksDAO.setHost("http://"+map.get("tasks").get("host")+":"+map.get("tasks").get("port")+"/api/tasks/");
		habitsDAO.setHost("http://"+map.get("habits").get("host")+":"+map.get("habits").get("port")+"/api/habits/");
	}
	
	public Account getAccount(String email, String password){
		return accountsDAO.getAccount(email, password);
	}
	
	public boolean createAccount(Account account){
		return accountsDAO.createAccount(account);
	}

	public List<Habit> getHabitsList(String idUser){
		return habitsDAO.getHabitsList(idUser);
	}
	
	public List<Task> getTasksList(String idUser){
		return tasksDAO.getTasksList(idUser);
	}
	
	public boolean createTask(Task task){
		return tasksDAO.createTask(task);
	}
	
	public void deleteTask(String idTask){
		tasksDAO.deleteTask(idTask);
	}
	
	public boolean createHabit(Habit habit){
		return habitsDAO.createHabit(habit);
	}
	
	public void deleteHabit(String idHabit){
		habitsDAO.deleteHabit(idHabit);
	}
	
	public void incrementHabitScore(String idHabit){
		habitsDAO.incrementScore(idHabit);
	}
	
	public void decrementHabitScore(String idHabit){
		habitsDAO.decrementScore(idHabit);
	}
	
	public void completedTask(String idTask, boolean completed){
		tasksDAO.completedTask(idTask, completed);
	}
	
	public void deleteAccount(String email){
		accountsDAO.deleteAccount(email);
	}
	
	public TasksReport generateTasksReport(String idUser){
		return tasksDAO.getTasksReport(idUser);
	}
	
	public HabitsReport generateHabitsReport(String idUser){
		return habitsDAO.getHabitsReport(idUser);
	}
}
