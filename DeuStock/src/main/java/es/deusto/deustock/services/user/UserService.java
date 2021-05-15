package es.deusto.deustock.services.user;

import es.deusto.deustock.dao.UserDAO;
import es.deusto.deustock.data.dto.UserDTO;
import es.deusto.deustock.services.user.exceptions.UserException;

import org.apache.log4j.Logger;

import java.sql.SQLException;

public class UserService {

    private UserDAO userDAO;

    private static final Logger logger = Logger.getLogger(UserService.class);

    public UserService(){
        userDAO = UserDAO.getInstance();
    }

    public void setUserDAO(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    public UserDTO getUserByUsername(String username) throws UserException {
        try{
            var user = userDAO.get(username);

            if(user == null){
                logger.error("Cannot get user  information");
                throw new UserException("User not found");
            }

            return userDAO.getDTO(user);
        }catch (SQLException e){
            throw new UserException("User not found");
        }
    }

    public void updateUser(UserDTO userDTO) throws UserException {
        try{
            var user = userDAO.get(userDTO.getUsername());

            if(user==null){
                logger.error("Cannot get user  information");
                throw new UserException("User not found");
            }
            user.updateInfo(userDTO);

            userDAO.update(user);
        }catch (SQLException e){
            throw new UserException("Error Updating User");
        }
    }

    public void deleteUser(String username) throws UserException {
        try{
            var user = userDAO.get(username);

            if (user == null) {
                logger.warn("User not found in DB while deleting");
                throw new UserException("User not found");
            }

            userDAO.delete(username);
        }catch (SQLException e){
            throw new UserException("Could not delete user");
        }




    }
}
