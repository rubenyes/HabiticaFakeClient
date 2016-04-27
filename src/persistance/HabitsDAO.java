package persistance;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import logic.Difficulty;
import logic.Habit;
import logic.HabitsReport;
import logic.Task;
import logic.TasksReport;

public class HabitsDAO implements IHabitsDAO{

	private String host = "http://localhost:9001/api/habits/";

	private static HabitsDAO dao;
	
	private HabitsDAO(){}
	
	public static HabitsDAO givemeDAO(){
		if(dao == null) dao = new HabitsDAO();
		return dao;
	}

	@Override
	public void setHost(String host){
		this.host = host;
	}
	
	@Override
	public boolean createHabit(Habit habit) {
		try{
			URL url = new URL(host+habit.getIdUser());
			HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
			httpcon.setDoOutput(true);
			httpcon.setDoInput(true);
			httpcon.setRequestProperty("Content-Type", "application/json");
			httpcon.setRequestProperty("Accept", "application/json");
			httpcon.setRequestMethod("POST");
			httpcon.connect();
	
			String json = "{\"Owner\": \""+habit.getIdUser()+"\""+
							", \"Type\": \""+habit.getTypeString()+"\""+
							", \"Difficulty\": \""+Difficulty.convertToString(habit.getDifficulty())+"\""+
							", \"Description\": \""+habit.getDescription()+"\""+
							", \"Score\": \""+habit.getScore()+"\""+"}";

			byte[] outputBytes = json.getBytes("UTF-8");
			OutputStream os = httpcon.getOutputStream();
			os.write(outputBytes);	
			os.close();
			
			int code = httpcon.getResponseCode();
			if(code == 201){
				//We set the ID
				InputStream input = httpcon.getInputStream();
				Map<String, String> map = new Gson().fromJson(new InputStreamReader(input, "UTF-8"), new TypeToken<Map<String, String>>(){}.getType());
				habit.setId(map.get("_id"));
				return true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public Habit getHabit(String idHabit) {
		Habit habit = null;
		try{
			URL url = new URL(host+idHabit);
			InputStream input = url.openStream();
			Map<String, String> map = new Gson().fromJson(new InputStreamReader(input, "UTF-8"), new TypeToken<Map<String, String>>(){}.getType());
			String idUser = map.get("Owner");
			String description = map.get("Description");
			Difficulty difficulty = Difficulty.convertToEnum(map.get("Difficulty"));
			int type = Habit.getTypeFromString(map.get("Type"));
			int score = Integer.parseInt(map.get("Score"));
			habit = new Habit(idUser, description, difficulty, type, score);
			habit.setId(map.get("_id"));
		}catch(Exception e){
			e.printStackTrace();
		}
		return habit;
	}

	@Override
	public List<Habit> getHabitsList(String idUser) {
		List<Habit> res = null;
		try{
			URL url = new URL(host+"user/"+idUser);
			InputStream input = url.openStream();
			List<Map<String, String>> list = new Gson().fromJson(new InputStreamReader(input, "UTF-8"), new TypeToken<List<Map<String, String>>>(){}.getType());
			res = new ArrayList<Habit>(list.size());
			for(Map<String, String> map : list){
				String description = map.get("Description");
				Difficulty difficulty = Difficulty.convertToEnum(map.get("Difficulty"));
				int type = Habit.getTypeFromString(map.get("Type"));
				int score = Integer.parseInt(map.get("Score"));
				Habit habit = new Habit(idUser, description, difficulty, type, score);
				habit.setId(map.get("_id"));
				res.add(habit);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public void deleteHabit(String idHabit) {
		try{
			URL url = new URL(host+idHabit);
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
	public void incrementScore(String idHabit) {
		try{
			URL url = new URL(host+"increment/"+idHabit);
			HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
			httpcon.setDoOutput(true);
			httpcon.setRequestMethod("POST");
			httpcon.connect();
				
			byte[] outputBytes = "increment".getBytes("UTF-8");
			OutputStream os = httpcon.getOutputStream();
			os.write(outputBytes);	
			os.close();			

			System.out.println(httpcon.getResponseCode()+" "+httpcon.getResponseMessage());
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void decrementScore(String idHabit) {
		try{
			URL url = new URL(host+"decrement/"+idHabit);
			HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
			httpcon.setDoOutput(true);
			httpcon.setRequestMethod("POST");
			httpcon.connect();
				
			byte[] outputBytes = "decrement".getBytes("UTF-8");
			OutputStream os = httpcon.getOutputStream();
			os.write(outputBytes);	
			os.close();
			
			System.out.println(httpcon.getResponseCode()+" "+httpcon.getResponseMessage());
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public HabitsReport getHabitsReport(String idUser) {
		HabitsReport report = null;
		try{
			URL url = new URL(host+"report/"+idUser);
			InputStream input = url.openStream();
			Map<String, Map<String, String>> map = new Gson().fromJson(new InputStreamReader(input, "UTF-8"), new TypeToken<Map<String, Map<String, String>>>(){}.getType());
			
			Map<String, String> range = map.get("Range");
			int[] rangeInt = {Integer.parseInt(range.get("Red")), 
							  Integer.parseInt(range.get("Orange")), 
							  Integer.parseInt(range.get("Yellow")), 
							  Integer.parseInt(range.get("Green")), 
							  Integer.parseInt(range.get("Blue"))};
			
			String descriptionBest = map.get("Best").get("Description");
			Difficulty difficultyBest = Difficulty.convertToEnum(map.get("Best").get("Difficulty"));
			int typeBest = Habit.getTypeFromString(map.get("Best").get("Type"));
			int scoreBest = Integer.parseInt(map.get("Best").get("Score"));
			Habit bestHabit = new Habit(idUser, descriptionBest, difficultyBest, typeBest, scoreBest);
			bestHabit.setId(map.get("Best").get("_id"));
			
			String descriptionWorst = map.get("Worst").get("Description");
			Difficulty difficultyWorst = Difficulty.convertToEnum(map.get("Worst").get("Difficulty"));
			int typeWorst = Habit.getTypeFromString(map.get("Worst").get("Type"));
			int scoreWorst = Integer.parseInt(map.get("Worst").get("Score"));
			Habit worstHabit = new Habit(idUser, descriptionWorst, difficultyWorst, typeWorst, scoreWorst);
			worstHabit.setId(map.get("Worst").get("_id"));
			
			report = new HabitsReport(rangeInt, bestHabit, worstHabit);
		}catch(Exception e){
			e.printStackTrace();
		}
		return report;
	}

}
