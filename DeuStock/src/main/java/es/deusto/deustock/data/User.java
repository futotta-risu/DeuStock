package es.deusto.deustock.data;

import es.deusto.deustock.data.stocks.Wallet;

import java.io.Serial;
import java.util.Calendar;
import java.io.Serializable;
import java.util.Date;
import javax.jdo.annotations.*;

/**
 * Clase usuario persistente, el nombre de usuario sera unico y se generara
 *  un ID por cada instancia que se almacene en la BD
 * @author landersanmillan
 */
@PersistenceCapable(detachable = "true")
public class User implements Serializable{
	@Serial
	private static final long serialVersionUID = 1L;
	@Unique
	String username;
	String password;
	String fullName;
	@NotPersistent
	Date birthDate;
	String country;
	String description;
	@NotPersistent
	Date registerDate;
	@NotPersistent
	Date lastActivity;

	@Persistent
	Wallet wallet;
	
	
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
	public Wallet getWallet(){
		return this.wallet;
	}
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
		this.registerDate = Calendar.getInstance().getTime();
		this.lastActivity = this.registerDate;
		this.wallet = new Wallet();
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
	
	
	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", fullName=" + fullName + ", birthDate="
				+ birthDate + ", country=" + country + ", description=" + description + ", registerDate=" + registerDate
				+ ", lastActivity=" + lastActivity + ", wallet=" + wallet + "]";
	}
	public boolean checkPassword(String password){
		return this.password.equals(password);
	}
	
}
