package es.deusto.deustock.dao;

import java.util.ArrayList;

import es.deusto.deustock.data.User;

/**
 * Clase de acceso a datos de Usuarios en la BD.<br>
 * <strong>Patterns:</strong>
 * <ul>
 *      <li>DAO</li>
 *      <li>Singleton</li>
 * </ul>
 * 
 * @see GenericDAO
 * @author landersanmillan
 */
public class UserDAO extends GenericDAO {

	private static UserDAO INSTANCE;

	/**
	 * Se obtiene la unica instancia de la clase UserDAO
	 * 
	 * @return <strong>UserDAO</strong> -> Instancia de la clase User
	 */
	public static UserDAO getInstance() {
		if (INSTANCE == null)
			INSTANCE = new UserDAO();
		return INSTANCE;
	}
	
	public User getUser(String username) {
		String whereCondition = "username == '" + username + "'";
		return (User) getObject(User.class, whereCondition);
	
	}
	public void deleteUser(String username) {
		String whereCondition = "username  == '" + username + "'";
		deleteObject(User.class, whereCondition);
	}
	
	public void storeUser(User user) {
		storeObject(user);
	}
	
	public ArrayList<User> getUsers(){
		ArrayList<User> usersList  = new ArrayList<User>();
		for (Object users : getObjects(User.class)) {
			usersList.add((User) users);
		}
		return usersList;
	}
	
	public void updateUser(User user) {
		updateObject(user);
	}
	
}
