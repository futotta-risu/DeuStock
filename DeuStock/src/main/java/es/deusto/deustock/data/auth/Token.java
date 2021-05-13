package es.deusto.deustock.data.auth;

import es.deusto.deustock.data.User;

import javax.jdo.annotations.*;

@PersistenceCapable
public class Token {

    @Persistent(defaultFetchGroup = "true")
    User user;

    @Unique
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
