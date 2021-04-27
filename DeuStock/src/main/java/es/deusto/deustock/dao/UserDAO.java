package es.deusto.deustock.dao;

import java.util.ArrayList;
import java.util.List;

import es.deusto.deustock.data.User;
import es.deusto.deustock.data.dto.UserDTO;
import es.deusto.deustock.data.stocks.Wallet;

/**
 * Clase de acceso a datos de Usuarios en la BD.<br>
 * <strong>Patterns:</strong>
 * <ul>
 *      <li>DAO</li>
 *      <li>Singleton</li>
 * </ul>
 *
 * @author landersanmillan
 */
public class UserDAO {

	private static UserDAO instance;

	private IDBManager dbManager;

	private UserDAO(){
		dbManager = DBManager.getInstance();
	}

	public void setDBManager(IDBManager dbManager){
		this.dbManager = dbManager;
	}

	/**
	 * Se obtiene la unica instancia de la clase UserDAO
	 * 
	 * @return <strong>UserDAO</strong> -> Instancia de la clase User
	 */
	public static UserDAO getInstance() {
		if (instance == null)
			instance = new UserDAO();
		return instance;
	}

	public User create(UserDTO userDTO){
		User user = new User(userDTO.getUsername(), userDTO.getPassword());
		user.setCountry(userDTO.getCountry());
		user.setDescription(userDTO.getDescription());
		user.setFullName(userDTO.getFullName());
		user.setWallet(new Wallet());

		return user;
	}

	public User getUser(String username) {
		String whereCondition = "username == '" + username + "'";
		return (User) dbManager.getObject(User.class, whereCondition);
	
	}
	public void deleteUser(String username) {
		String whereCondition = "username  == '" + username + "'";
		dbManager.deleteObject(User.class, whereCondition);
	}
	public void deleteUser(User user) {
		dbManager.deleteObject(user);
	}
	
	public void storeUser(User user) {
		dbManager.storeObject(user);
	}
	
	public List<User> getUsers(){
		List<User> usersList  = new ArrayList<>();
		for (Object users : dbManager.getObjects(User.class)) {
			usersList.add((User) users);
		}
		return usersList;
	}
	
	public void updateUser(User user) {
		dbManager.updateObject(user);
	}

	public UserDTO getDTO(User user){
		return new UserDTO()
				.setUsername(user.getUsername())
				.setPassword("")
				.setFullName(user.getFullName())
				.setCountry(user.getCountry())
				.setDescription(user.getDescription());
	}
	
}
