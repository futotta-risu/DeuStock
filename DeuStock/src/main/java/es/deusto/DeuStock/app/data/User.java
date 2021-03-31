package es.deusto.DeuStock.app.data;

import java.util.Calendar;
import java.io.Serializable;
import java.util.Date;
import javax.jdo.annotations.*;

@PersistenceCapable
public class User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Unique
	String username;
	String password;
	String fullName;
	Date birthDate;
	String country;
	String description;
	Date registerDate;
	Date lastActivity;
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	public Date getLastActivity() {
		return lastActivity;
	}
	public void setLastActivity(Date lastActivity) {
		this.lastActivity = lastActivity;
	}
	public void setLastActivity() {
		this.lastActivity = Calendar.getInstance().getTime();
	}
	
	
	public User(String username, String password, String fullName, Date birthDate, String country, String description) {
		this.username = username;
		this.password = password;
		this.fullName = fullName;
		this.birthDate = birthDate;
		this.country = country;
		this.description = description;
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
