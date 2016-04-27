package logic;

public class Habit implements Comparable<Habit>{
	public final static int GOOD = 1;
	public final static int BAD = 2;
	public final static int BOTH = 3;
	
	private String id;
	private String idUser;
	private String description;
	private Difficulty difficulty;
	private int type;
	private int score;
	
	public Habit(String idUser, String description, Difficulty difficulty, int type) {
		this.idUser = idUser;
		this.description = description;
		this.difficulty = difficulty;
		this.type = type;
		this.score = 0;
	}
	
	public Habit(String idUser, String description, Difficulty difficulty, int type, int score) {
		this.idUser = idUser;
		this.description = description;
		this.difficulty = difficulty;
		this.type = type;
		this.score = score;
	}
	
	public static int getTypeFromString(String s){
		if(s.toLowerCase().equals("good")) return GOOD;
		if(s.toLowerCase().equals("bad")) return BAD;
		if(s.toLowerCase().equals("both")) return BOTH;
		return -1;
	}
	
	public String getTypeString(){
		if(type == GOOD) return "Good";
		if(type == BAD) return "Bad";
		if(type == BOTH) return "Both";
		return null;
	}
	
	@Override
	public String toString() {
		String res = "Description: "+description+System.lineSeparator();
		res += "Type: "+getTypeString()+System.lineSeparator();
		res += "Difficulty: "+Difficulty.convertToString(difficulty)+System.lineSeparator();
		res += "Score: "+score+System.lineSeparator();
		return res;
	}

	public void incrementScore(){
		int points = Difficulty.getPoints(difficulty);
		if(score > 50) this.score += 1;
		else if(score > 40) this.score += points/2;
		else this.score += points;
	}
	
	public void decrementScore(){
		int points = Difficulty.getPoints(difficulty);
		if(score < 0) this.score -= points*2;
		else if(score < 10) this.score -= points*1.5;
		else this.score -= points;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Difficulty getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public int compareTo(Habit other) {
		if(this.difficulty == Difficulty.HARD) return +1;
		if(this.difficulty == Difficulty.EASY) return -1;
		if(other.getDifficulty() == Difficulty.EASY) return +1;
		if(other.getDifficulty() == Difficulty.HARD) return -1;
		return 0;
	}
	
}
