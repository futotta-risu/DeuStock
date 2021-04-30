package es.deusto.deustock.services.auth;

import es.deusto.deustock.dao.UserDAO;
import es.deusto.deustock.data.User;
import es.deusto.deustock.data.dto.UserDTO;
import es.deusto.deustock.log.DeuLogger;
import es.deusto.deustock.services.auth.exceptions.AuthException;
import es.deusto.deustock.services.auth.exceptions.LoginException;
import es.deusto.deustock.services.auth.exceptions.RegisterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class AuthService {

    private UserDAO userDAO;
    private Logger logger = LoggerFactory.getLogger(AuthService.class);

    public void setUserDAO(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    public UserDTO login(String username, String password) throws AuthException {
        User user;

        try{
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
        try{
            if(userDAO.has(userDTO.getUsername())){
                throw new RegisterException("User already registered");
            }

            User user = userDAO.create(userDTO);
            userDAO.store(user);

        }catch (SQLException e){
            throw new RegisterException("Error adding user");
        }
    }
}
