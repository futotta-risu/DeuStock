package es.deusto.deustock.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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
public class UserDAO implements IDAO<User> {

	private static UserDAO instance = null;

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
		var user = new User(userDTO.getUsername(), userDTO.getPassword());
		user.setCountry(userDTO.getCountry());
		user.setDescription(userDTO.getDescription());
		user.setFullName(userDTO.getFullName());
		user.setWallet(new Wallet());

		return user;
	}

	@Override
	public User get(Object identity) throws SQLException {
		String whereCondition = "username == :username";
		HashMap<String,Object> params = new HashMap<>();
		params.put("username", identity);
		return (User) dbManager.get(User.class, whereCondition, params);
	
	}
	public void delete(String username) throws SQLException {
		var whereCondition = "username == :username";
		HashMap<String,String> params = new HashMap<>();
		params.put("username", username);
		dbManager.delete(User.class, whereCondition, params);
	}
	public void delete(User user) {
		dbManager.delete(user);
	}




	@Override
	public boolean has(Object identity) throws SQLException {
		return get(identity) != null;
	}

	public void store(User user) throws SQLException {
		dbManager.store(user);
	}
	
	public List<User> getAll(){
		List<User> usersList  = new ArrayList<>();
		for (Object users : dbManager.getAll(User.class)) {
			usersList.add((User) users);
		}
		return usersList;
	}
	
	public void update(User user) throws SQLException {
		dbManager.update(user);
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
