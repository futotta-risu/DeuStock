package es.deusto.deustock.client.data;

import java.util.Calendar;
import java.util.Date;

public class User {
	
	String username;
	String password;
	String fullName;
	Date birthDate;
	String country;
	String description;
	Date registerDate;
	Date lastActivity;
	
	
	public String getUsername() { return username; }
	public User setUsername(String username) {
		this.username = username; return this;
	}
	public String getPassword() { return password; }
	public User setPassword(String password) {
		this.password = password; return this;
	}
	public String getFullName() { return fullName; }
	public User setFullName(String fullName) {
		this.fullName = fullName; return this;
	}
	public Date getBirthDate() { return birthDate; }
	public User setBirthDate(Date birthDate) {
		this.birthDate = birthDate; return this;
	}
	public String getCountry() { return country; }
	public User setCountry(String country) {
		this.country = country; return this;
	}
	public String getDescription() { return description; }
	public User setDescription(String description) {
		this.description = description; return this;
	}
	public Date getRegisterDate() { return registerDate; }
	public User setRegisterDate(Date registerDate) {
		this.registerDate = registerDate; return this;
	}
	public Date getLastActivity() { return lastActivity; }
	public User setLastActivity(Date lastActivity) {
		this.lastActivity = lastActivity; return this;
	}
	public User setLastActivity() {
		this.lastActivity = Calendar.getInstance().getTime(); return this;
	}
	
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
		this.registerDate = Calendar.getInstance().getTime();
		this.lastActivity = this.registerDate;
	}
	
	public User() {
		this.username = "NULL";
		this.password = "NULL";
		this.fullName = "NULL";
		this.birthDate = Calendar.getInstance().getTime();
		this.country = "NULL";
		this.description = "NULL";
		this.registerDate = Calendar.getInstance().getTime();
		this.lastActivity = this.registerDate;
	}

	
	public void updateInfo(User u) {
		this.password = u.password;
		this.fullName = u.fullName;
		this.birthDate = u.birthDate;
		this.country = u.country;
		this.description = u.description;
		this.registerDate = u.registerDate;
		this.lastActivity = u.lastActivity;
	}
	
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", fullName=" + fullName + ", birthDate="
				+ birthDate + ", country=" + country + ", description=" + description + ", registerDate=" + registerDate
				+ ", lastActivity=" + lastActivity + "]";
	}
	

	
	
	
	
	

}
