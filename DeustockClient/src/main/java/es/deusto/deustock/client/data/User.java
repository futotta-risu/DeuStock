package es.deusto.deustock.client.data;

/**
 * Client object of user 
 * @author aritz
 *
 */
public class User {
	
	String username;
	String password;
	String fullName;
	String country;
	String description;

	/**
	 * User object constructor
	 */
	public User() {}

	/**
	 * User username getter
	 * @return A string with the user username
	 */
	public String getUsername() { return username; }
	/**
	 * User username setter
	 * @param username String for the User username
	 * @return The user we are setting the username
	 */
	public User setUsername(String username) {
		this.username = username; return this;
	}
	
	/**
	 * User password getter
	 * @return A string with the user password
	 */
	public String getPassword() { return password; }
	/**
	 * User password setter
	 * @param password String for the User password
	 * @return The user we are setting the password
	 */
	public User setPassword(String password) {
		this.password = password; return this;
	}
	
	/**
	 * User full name getter
	 * @return A string with the user full name
	 */
	public String getFullName() { return fullName; }
	/**
	 * User full name setter
	 * @param fullName String for the User full name
	 * @return The user we are setting the full name
	 */
	public User setFullName(String fullName) {
		this.fullName = fullName; return this;
	}
	
	/**
	 * User country getter
	 * @return A string with the user country
	 */
	public String getCountry() { return country; }
	/**
	 * User country setter
	 * @param country String for the User country
	 * @return The user we are setting the country
	 */
	public User setCountry(String country) {
		this.country = country; return this;
	}
	
	/**
	 * User description getter
	 * @return A string with the user description
	 */
	public String getDescription() { return description; }
	/**
	 * User description setter
	 * @param description String for the User description
	 * @return The user we are setting the description
	 */
	public User setDescription(String description) {
		this.description = description; return this;
	}
	
	/**
	 * Method for updating the information of a user
	 * @param u User we want to change the information 
	 */
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
