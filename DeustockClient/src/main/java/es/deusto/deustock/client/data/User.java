package es.deusto.deustock.client.data;

/**
 * @author Erik B. Terres
 */
public class User {

    String username = "", name = "", description = "", birthday = "";
    boolean sex = true;


    public User(String username){
        this.username = username;
    }

    public User setUsername(String username){
        this.username = username;
        return this;
    }

    public User setName(String name){
        this.name = name;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public User setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getBirthday() {
        return birthday;
    }

    public User setBirthday(String birthday) {
        this.birthday = birthday;
        return this;
    }

    public boolean isSex() {
        return sex;
    }

    public User setSex(boolean sex) {
        this.sex = sex;
        return this;
    }
}
