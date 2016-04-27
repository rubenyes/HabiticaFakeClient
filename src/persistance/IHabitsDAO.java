package persistance;

import java.util.List;

import logic.Habit;
import logic.HabitsReport;


public interface IHabitsDAO {
	public void setHost(String host);
	public boolean createHabit(Habit habit);
	public Habit getHabit(String idHabit);
	public List<Habit> getHabitsList(String idUser);
	public void deleteHabit(String idHabit);
	public void incrementScore(String idHabit);
	public void decrementScore(String idHabit);
	public HabitsReport getHabitsReport(String idUser);
}
