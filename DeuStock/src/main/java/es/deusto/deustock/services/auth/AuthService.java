package es.deusto.deustock.services.auth;

import es.deusto.deustock.dao.TokenDAO;
import es.deusto.deustock.dao.UserDAO;
import es.deusto.deustock.data.User;
import es.deusto.deustock.data.auth.Token;
import es.deusto.deustock.data.dto.UserDTO;
import es.deusto.deustock.services.auth.exceptions.AuthException;
import es.deusto.deustock.services.auth.exceptions.InvalidTokenException;
import es.deusto.deustock.services.auth.exceptions.LoginException;
import es.deusto.deustock.services.auth.exceptions.RegisterException;


import org.apache.log4j.Logger;
import es.deusto.deustock.util.crypto.Crypto;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.sql.SQLException;

/**
 * Service for the Authorization process.
 *
 * @author Erik B. Terres
 */
public class AuthService {

    private UserDAO userDAO;
    private TokenDAO tokenDAO;
    private final Logger logger = Logger.getLogger(AuthService.class);

    public AuthService(){
        userDAO = UserDAO.getInstance();
        tokenDAO = TokenDAO.getInstance();
    }

    public void setUserDAO(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    public void setTokenDAO(TokenDAO tokenDAO){
        this.tokenDAO = tokenDAO;
    }

    public String createToken(User user){
        String encryptedToken;
        try{
            do{
                byte[] array = new byte[7]; // length is bounded by 7
                new SecureRandom().nextBytes(array);
                String randomToken = new String(array, StandardCharsets.UTF_8);
                encryptedToken = Crypto.getHash(randomToken);
            }while(tokenDAO.has(encryptedToken));
        }catch (SQLException e){
            throw new RegisterException("Could not create token");
        }


        var token = new Token(encryptedToken, user);

        try{
            tokenDAO.store(token);
        }catch (SQLException e){
            throw new RegisterException("Could not create token");
        }

        return encryptedToken;
    }

    public String validateToken(String token) throws InvalidTokenException {
        try{
            if(tokenDAO.has(token)){
                var tempToken = tokenDAO.get(token);
                return tempToken.getUser().getUsername();
            }else{
                throw new InvalidTokenException("No token found");
            }
        }catch (SQLException e){
            throw new InvalidTokenException(e.getMessage());
        }
    }

    public String login(String username, String password) throws AuthException {
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

        return createToken(user);

    }

    public void register(UserDTO userDTO) throws AuthException{
        if(userDTO.getUsername().isBlank()){
            throw new RegisterException("Blank user");
        }
        try{
            if(userDAO.has(userDTO.getUsername())){
                throw new RegisterException("User already registered");
            }
            User user = userDAO.create(userDTO);
            if(user == null){
                throw new RegisterException("Error adding user");
            }
            
            userDAO.store(userDAO.create(userDTO));
        }catch (SQLException e){
            throw new RegisterException("Error adding user");
        }
    }
}
