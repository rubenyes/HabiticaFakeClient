package logic;

import java.util.Calendar;

public class Account {
	private String name;
	private String email;
	private String password;
	private Calendar birthday;
	
	public Account(String name, String email, String password, Calendar birthday) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.birthday = birthday;
	}
	
	public Account(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Calendar getBirthday() {
		return birthday;
	}

	public void setBirthday(Calendar birthday) {
		this.birthday = birthday;
	}
	
}
