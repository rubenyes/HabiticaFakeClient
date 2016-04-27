package logic;

public enum Difficulty {
	EASY,
	MEDIUM,
	HARD;
	
	public static Difficulty convertToEnum(String s){
		if(s.toLowerCase().equals("easy")) return EASY;
		if(s.toLowerCase().equals("medium")) return MEDIUM;
		if(s.toLowerCase().equals("hard")) return HARD;
		return null;
	}
	
	public static String convertToString(Difficulty d){
		switch(d){
			case EASY:		return "Easy";
			case MEDIUM:	return "Medium";
			case HARD:		return "Hard";
		}
		return null;
	}
	
	public static int getPoints(Difficulty d){
		switch(d){
			case EASY:		return 2;
			case MEDIUM:	return 4;
			case HARD:		return 6;
		}
		return 0;
	}
}
