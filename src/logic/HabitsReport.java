package logic;

public class HabitsReport {
	//The order of the ranges is: Red, Orange, Yellow, Green, Blue.
	private int[] rangeHabits;
	private Habit bestHabit;
	private Habit worstHabit;
	
	public HabitsReport(int[] rangeHabits, Habit bestHabit, Habit worstHabit) {
		this.rangeHabits = rangeHabits;
		this.bestHabit = bestHabit;
		this.worstHabit = worstHabit;
	}

	
	@Override
	public String toString() {
		String res = "RANGES:"+System.lineSeparator();
		res += "\tRed: "+rangeHabits[0]+System.lineSeparator();
		res += "\tOrange: "+rangeHabits[1]+System.lineSeparator();
		res += "\tYellow: "+rangeHabits[2]+System.lineSeparator();
		res += "\tGreen: "+rangeHabits[3]+System.lineSeparator();
		res += "\tBlue: "+rangeHabits[4]+System.lineSeparator();
		res += System.lineSeparator();
		res += "BEST HABIT:"+System.lineSeparator();
		res += bestHabit.toString();
		res += System.lineSeparator();
		res += "WORST HABIT:"+System.lineSeparator();
		res += worstHabit.toString();
		return res;
	}
	
	public int[] getRangeHabits() {
		return rangeHabits;
	}

	public void setRangeHabits(int[] rangeHabits) {
		this.rangeHabits = rangeHabits;
	}

	public Habit getBestHabit() {
		return bestHabit;
	}

	public void setBestHabit(Habit bestHabit) {
		this.bestHabit = bestHabit;
	}

	public Habit getWorstHabit() {
		return worstHabit;
	}

	public void setWorstHabit(Habit worstHabit) {
		this.worstHabit = worstHabit;
	}
	
	
}
