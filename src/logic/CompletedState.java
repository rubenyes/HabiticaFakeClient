package logic;

public enum CompletedState {
	NOTYET,
	BEFORE,
	ONTIME,
	LATE;
	
	public static CompletedState convertToEnum(String s){
		if(s.toLowerCase().equals("notyet")) return NOTYET;
		if(s.toLowerCase().equals("before")) return BEFORE;
		if(s.toLowerCase().equals("ontime")) return ONTIME;
		if(s.toLowerCase().equals("late")) return LATE;
		return null;
	}
	
	public static String convertToString(CompletedState c){
		switch(c){
			case NOTYET:	return "NotYet";
			case BEFORE:	return "Before";
			case ONTIME:	return "OnTime";
			case LATE:		return "Late";
		}
		return null;
	}
}
