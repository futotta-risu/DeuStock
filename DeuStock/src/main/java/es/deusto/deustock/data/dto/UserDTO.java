package es.deusto.deustock.data.dto;

import java.io.Serializable;

/**
 * @author Erik B. Terres
 */
public class UserDTO implements Serializable {

    private String username;
    private String fullName;
    private String country;
    private String description;
    private String password;

    public UserDTO() {}

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }

    public String getCountry() {
        return country;
    }

    public String getDescription() {
        return description;
    }

    public String getPassword() {
        return password;
    }

    public UserDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public UserDTO setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public UserDTO setCountry(String country) {
        this.country = country;
        return this;
    }

    public UserDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public UserDTO setPassword(String password) {
        this.password = password;
        return this;
    }


    @Override
    public String toString() {
        return "UserDTO{" +
                "username='" + username + '\'' +
                ", fullName='" + fullName + '\'' +
                ", country='" + country + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String format(){
        return fullName + "(" + username +")";
    }
}
