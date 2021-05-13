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

import es.deusto.deustock.util.crypto.Crypto;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Random;

public class AuthService {

    private UserDAO userDAO;
    private TokenDAO tokenDAO;

    private final Logger logger = LoggerFactory.getLogger(AuthService.class);

    public AuthService(){
        userDAO = UserDAO.getInstance();
        tokenDAO = TokenDAO.getInstance();
    }

    public void setUserDAO(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    public String createToken(User user){
        String encryptedToken;
        try{
            do{
                byte[] array = new byte[7]; // length is bounded by 7
                new Random().nextBytes(array);
                String randomToken = new String(array, StandardCharsets.UTF_8);
                encryptedToken = Crypto.getHash(randomToken);
            }while(tokenDAO.has(encryptedToken));
        }catch (SQLException e){
            throw new RegisterException("Could not create token");
        }


        Token token = new Token(encryptedToken, user);

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
                Token tempToken = tokenDAO.get(token);
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
            createToken(user);
            if(user == null){
                return;
            }
            
            userDAO.store(userDAO.create(userDTO));
        }catch (SQLException e){
            throw new RegisterException("Error adding user");
        }
    }
}
