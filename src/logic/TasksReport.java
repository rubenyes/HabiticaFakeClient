package logic;

import java.util.Arrays;
import java.util.List;

public class TasksReport {
	//0-"Completed on time", 1-"Completed before time", 2-"Completed after due date"
	private int[] completedTasks;
	
	//0-"Tasks on reminder time", 1-"Tasks to be done", 2-"Tasks for today"
	private int[] availableTasks;	
	
	private int[] completedTasksIDs; //IDs of the tasks completed

	
	public TasksReport(int[] completedTasks, int[] availableTasks, int[] completedTasksIDs) {
		this.completedTasks = completedTasks;
		this.availableTasks = availableTasks;
		this.completedTasksIDs = completedTasksIDs;
	}
	
	
	@Override
	public String toString() {
		String res = "COMPLETED TASKS:"+System.lineSeparator();
		res += "\tCompleted on time: "+completedTasks[0]+System.lineSeparator();
		res += "\tCompleted before time: "+completedTasks[1]+System.lineSeparator();
		res += "\tCompleted after due date: "+completedTasks[2]+System.lineSeparator();
		res += System.lineSeparator();
		res += "AVAILABLE TASKS:"+System.lineSeparator();
		res += "\tTasks on reminder time: "+availableTasks[0]+System.lineSeparator();
		res += "\tTasks to be done: "+availableTasks[1]+System.lineSeparator();
		res += "\tTasks for today: "+availableTasks[2]+System.lineSeparator();
		res += System.lineSeparator();
		res += "LIST OF COMPLETED TASKS"+System.lineSeparator();
		res += "\t"+Arrays.toString(completedTasksIDs);
		return res;		
	}

	public int[] getCompletedTasks() {
		return completedTasks;
	}

	public void setCompletedTasks(int[] completedTasks) {
		this.completedTasks = completedTasks;
	}

	public int[] getAvailableTasks() {
		return availableTasks;
	}

	public void setAvailableTasks(int[] availableTasks) {
		this.availableTasks = availableTasks;
	}

	public int[] getcompletedTasksIDs() {
		return completedTasksIDs;
	}

	public void setcompletedTasksIDs(int[] completedTasksIDs) {
		this.completedTasksIDs = completedTasksIDs;
	}
	
}
