package persistance;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import logic.Account;
import logic.CompletedState;
import logic.Difficulty;
import logic.Task;
import logic.TasksReport;

public class TasksDAO implements ITasksDAO{

	private String host = "http://localhost:9002/api/tasks/";

	private static TasksDAO dao;
	
	private TasksDAO(){}
	
	public static TasksDAO givemeDAO(){
		if(dao == null) dao = new TasksDAO();
		return dao;
	}

	@Override
	public void setHost(String host){
		this.host = host;
	}
	
	@Override
	public boolean createTask(Task task) {
		try{
			URL url = new URL(host+task.getIdUser());
			HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
			httpcon.setDoOutput(true);
			httpcon.setDoInput(true);
			httpcon.setRequestProperty("Content-Type", "application/json");
			httpcon.setRequestProperty("Accept", "application/json");
			httpcon.setRequestMethod("POST");
			httpcon.connect();
	
			String json = "{\"Title\": \""+task.getTitle()+"\""+
							", \"Description\": \""+task.getDescription()+"\""+
							", \"Owner\": \""+task.getIdUser()+"\""+
							", \"Completed\": \""+CompletedState.convertToString(task.getState())+"\""+
							", \"Duedate\": \""+task.getDate().getTimeInMillis()+"\""+
							", \"Daysbefore\": \""+task.getReminderDaysAgo()+"\""+
							", \"Time\": \""+task.getReminderTime().getTimeInMillis()+"\""+"}";
			
			byte[] outputBytes = json.getBytes("UTF-8");
			OutputStream os = httpcon.getOutputStream();
			os.write(outputBytes);
			os.close();
			
			int code = httpcon.getResponseCode();System.out.println(code+httpcon.getResponseMessage());
			if(code == 201){
				//We set the ID
				InputStream input = httpcon.getInputStream();
				Map<String, String> map = new Gson().fromJson(new InputStreamReader(input, "UTF-8"), new TypeToken<Map<String, String>>(){}.getType());
				task.setId(map.get("_id"));
				return true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public Task getTask(String idTask) {
		Task task = null;
		try{
			URL url = new URL(host+idTask);
			InputStream input = url.openStream();
			Map<String, String> map = new Gson().fromJson(new InputStreamReader(input, "UTF-8"), new TypeToken<Map<String, String>>(){}.getType());
			Calendar date = Calendar.getInstance();
			date.setTimeInMillis(Long.parseLong(map.get("Duedate")));
			Calendar reminderTime = Calendar.getInstance();
			reminderTime.setTimeInMillis(Long.parseLong(map.get("Time")));
			CompletedState state = CompletedState.convertToEnum(map.get("Completed"));
			int reminderDaysAgo = Integer.parseInt(map.get("Daysbefore"));
			task = new Task(map.get("Owner"), map.get("Title"), map.get("Description"), date, reminderDaysAgo, reminderTime, state);
			task.setId(map.get("_id"));
		}catch(Exception e){
			e.printStackTrace();
		}
		return task;
	}
	
	@Override
	public List<Task> getTasksList(String idUser){
		List<Task> res = null;
		try{
			URL url = new URL(host+"user/"+idUser);
			InputStream input = url.openStream();
			List<Map<String, String>> list = new Gson().fromJson(new InputStreamReader(input, "UTF-8"), new TypeToken<List<Map<String, String>>>(){}.getType());
			res = new ArrayList<Task>(list.size());
			for(Map<String, String> map : list){
				Calendar date = Calendar.getInstance();
				date.setTimeInMillis(Long.parseLong(map.get("Duedate")));
				Calendar reminderTime = Calendar.getInstance();
				reminderTime.setTimeInMillis(Long.parseLong(map.get("Time")));
				CompletedState state = CompletedState.convertToEnum(map.get("Completed"));
				System.out.println(state);
				int reminderDaysAgo = Integer.parseInt(map.get("Daysbefore"));
				Task task = new Task(map.get("Owner"), map.get("Title"), map.get("Description"), date, reminderDaysAgo, reminderTime, state);
				task.setId(map.get("_id"));
				res.add(task);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public void deleteTask(String idTask) {
		try{
			URL url = new URL(host+idTask);
			HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
			httpCon.setDoOutput(true);
			httpCon.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			httpCon.setRequestMethod("DELETE");
			httpCon.connect();
			System.out.println(httpCon.getResponseCode()+" "+httpCon.getResponseMessage());
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void completedTask(String idTask, boolean completed) {
		try{
			System.out.println(idTask);
			URL url = new URL(host+idTask+"/"+completed);
			HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
			httpcon.setDoOutput(true);
			httpcon.setRequestMethod("POST");
			httpcon.connect();
				
			byte[] outputBytes = "completed".getBytes("UTF-8");
			OutputStream os = httpcon.getOutputStream();
			os.write(outputBytes);	
			os.close();			

			System.out.println(httpcon.getResponseCode()+" "+httpcon.getResponseMessage());
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public TasksReport getTasksReport(String idUser) {
		TasksReport report = null;
		try{
			URL url = new URL(host+"report/"+idUser);
			InputStream input = url.openStream();
			Map<String, int[]> map = new Gson().fromJson(new InputStreamReader(input, "UTF-8"), new TypeToken<Map<String, int[]>>(){}.getType());
			report = new TasksReport(map.get("Completed Tasks"), map.get("Available Tasks"), map.get("List of completed Tasks"));
		}catch(Exception e){
			e.printStackTrace();
		}
		return report;
	}

}
