package es.deusto.deustock.data.auth;

import es.deusto.deustock.data.User;

import javax.jdo.annotations.*;
import java.io.Serializable;

/**
 * Token class used for user authentication without sending passwords.
 *
 * @author Erik B. Terres
 */
@PersistenceCapable(detachable = "true")
public class Token  implements Serializable {

    @Persistent(defaultFetchGroup = "true")
    User user;

    @PrimaryKey
    String token;

    public Token(String token, User user){
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }
    public User getUser(){
        return this.user;
    }
}
