package es.deusto.deustock.client.data;


public class User {
	
	String username;
	String password;
	String fullName;
	String country;
	String description;


	public User() {}

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

	public String getCountry() { return country; }
	public User setCountry(String country) {
		this.country = country; return this;
	}
	public String getDescription() { return description; }
	public User setDescription(String description) {
		this.description = description; return this;
	}
	
	public void updateInfo(User u) {
		this.fullName = u.fullName;
		this.country = u.country;
		this.description = u.description;
	}
	
	@Override
	public String toString() {
		return "User [username=" + username +  ", fullName=" + fullName +  ", country=" + country + ", description=" + description + "]";
	}
	
}
