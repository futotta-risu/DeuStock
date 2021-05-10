package es.deusto.deustock.services.auth;

import es.deusto.deustock.dao.UserDAO;
import es.deusto.deustock.data.User;
import es.deusto.deustock.data.dto.UserDTO;
import es.deusto.deustock.services.auth.exceptions.AuthException;
import es.deusto.deustock.services.auth.exceptions.LoginException;
import es.deusto.deustock.services.auth.exceptions.RegisterException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class AuthService {

    private UserDAO userDAO;
    private final Logger logger = LoggerFactory.getLogger(AuthService.class);

    public AuthService(){
        userDAO = UserDAO.getInstance();
    }

    public void setUserDAO(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    public UserDTO login(String username, String password) throws AuthException {
        if(username.isBlank()){
            throw new LoginException("Blank user");
        }

        User user;
        try{
            if(!userDAO.has(username)){
                throw new LoginException("User not in DB");
            }
            user = userDAO.get(username);
        }catch (SQLException e){
            throw new LoginException("User not in DB");
        }

        if(!user.checkPassword(password)) {
            throw new LoginException("Incorrect password");
        }

        return userDAO.getDTO(user);

    }

    public void register(UserDTO userDTO) throws AuthException{
        if(userDTO.getUsername().isBlank()){
            throw new RegisterException("Blank user");
        }

        try{
            if(userDAO.has(userDTO.getUsername())){
                throw new RegisterException("User already registered");
            }
            userDAO.store(userDAO.create(userDTO));
        }catch (SQLException e){
            throw new RegisterException("Error adding user");
        }
    }
}
