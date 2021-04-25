package es.deusto.deustock.dao;

import java.util.ArrayList;
import java.util.List;

import es.deusto.deustock.data.User;
import es.deusto.deustock.data.dto.UserDTO;

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
public class UserDAO {

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
		return (User) DBManager.getInstance().getObject(User.class, whereCondition);
	
	}
	public void deleteUser(String username) {
		String whereCondition = "username  == '" + username + "'";
		DBManager.getInstance().deleteObject(User.class, whereCondition);
	}
	
	public void storeUser(User user) {
		DBManager.getInstance().storeObject(user);
	}
	
	public List<User> getUsers(){
		List<User> usersList  = new ArrayList<>();
		for (Object users : DBManager.getInstance().getObjects(User.class)) {
			usersList.add((User) users);
		}
		return usersList;
	}
	
	public void updateUser(User user) {
		DBManager.getInstance().updateObject(user);
	}

	public UserDTO getDTO(User user){
		return new UserDTO()
				.setUsername(user.getUsername())
				.setFullName(user.getFullName())
				.setCountry(user.getCountry())
				.setDescription(user.getDescription());
	}
	
}
