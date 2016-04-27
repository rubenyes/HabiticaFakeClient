package logic;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.Calendar;

public class Task implements Comparable<Task>{
	
	public static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	public static final SimpleDateFormat sdfTime = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
	private String id;
	private String idUser;
	private String title;
	private String description;
	private Calendar date;
	private int reminderDaysAgo;
	private Calendar reminderTime;
	private CompletedState state;
	
	private Calendar reminder;
	private boolean completed;
	
	public Task(String idUser, String title, String description, Calendar date, int reminderDaysAgo, Calendar reminderTime, CompletedState state) {
		this.idUser = idUser;
		this.title = title;
		this.description = description;
		this.date = date;
		this.reminderDaysAgo = reminderDaysAgo;
		this.reminderTime = reminderTime;
		this.state = state;
		
		//We create a calendar with the exactly reminder date for make easier the comparations
		this.reminder = Calendar.getInstance();
		this.reminder.setTime(date.getTime());
		this.reminder.add(Calendar.DATE, -reminderDaysAgo);
		this.reminder.set(Calendar.HOUR_OF_DAY, reminderTime.get(Calendar.HOUR_OF_DAY));
		this.reminder.set(Calendar.MINUTE, reminderTime.get(Calendar.MINUTE));
	}
		

	public String getDateString(){
		return sdf.format(date.getTime());
	}
	
	public String getReminderString(){
		return sdfTime.format(reminder.getTime());
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public int getReminderDaysAgo() {
		return reminderDaysAgo;
	}

	public void setReminderDaysAgo(int reminderDaysAgo) {
		this.reminderDaysAgo = reminderDaysAgo;
	}

	public Calendar getReminderTime() {
		return reminderTime;
	}

	public void setReminderTime(Calendar reminderTime) {
		this.reminderTime = reminderTime;
	}

	public Calendar getReminder() {
		return reminder;
	}
	
	public void setReminder(Calendar reminder) {
		this.reminder = reminder;
	}

	public CompletedState getState() {
		return state;
	}
	
	public void setState(CompletedState state) {
		this.state = state;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
		
		//Now we set the CompletedState
		if(completed){
			Calendar now = Calendar.getInstance();
			int res = compareWithoutTime(date, now);
			if(res < 0) this.state = CompletedState.LATE;
			else if(res > 0) this.state = CompletedState.BEFORE;
			else this.state = CompletedState.ONTIME;
		}
		else{
			this.state = CompletedState.NOTYET;
		}
	}

	public int compareWithoutTime(Calendar c1, Calendar c2) {
	    if (c1.get(Calendar.YEAR) != c2.get(Calendar.YEAR)) 
	        return c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR);
	    if (c1.get(Calendar.MONTH) != c2.get(Calendar.MONTH)) 
	        return c1.get(Calendar.MONTH) - c2.get(Calendar.MONTH);
	    return c1.get(Calendar.DAY_OF_MONTH) - c2.get(Calendar.DAY_OF_MONTH);
	  }

	@Override
	public int compareTo(Task other) {
		return -this.date.compareTo(other.getDate());
	}
	
}
