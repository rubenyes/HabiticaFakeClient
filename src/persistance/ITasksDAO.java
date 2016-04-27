package persistance;

import java.util.List;

import logic.Task;
import logic.TasksReport;

public interface ITasksDAO {
	public void setHost(String host);
	public boolean createTask(Task task);
	public Task getTask(String idTask);
	public List<Task> getTasksList(String idUser);
	public void deleteTask(String idTask);
	public void completedTask(String idTask, boolean completed);
	public TasksReport getTasksReport(String idUser);
}
