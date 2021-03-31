package es.deusto.deustock.client.controllers;

import es.deusto.deustock.client.data.User;
import es.deusto.deustock.client.gateways.DeustockGateway;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Erik B. Terres
 */
public class UserDetailController implements DSGenericController{

    private String username = null;
    private User user;

    @FXML
    Label usernameLabel;

    @FXML
    Label birthdayLabel;

    @FXML
    Label sexLabel;

    @FXML
    Text descriptionLabel;

    public  UserDetailController(){}

    @FXML
    public void initialize(){
        initRoot();
    }

    @Override
    public void setParams(HashMap<String, Object> params) {
        if(params.containsKey("username"))
            this.username = String.valueOf(params.get("username"));

        initRoot();

    }

    private void getUser(){
        DeustockGateway gateway = new DeustockGateway();
        this.user = gateway.getUser(this.username);
    }

    private void initRoot(){
        if(this.username==null) return;

        if(this.user == null || !this.user.getUsername().equals(this.username))
            getUser();

        // Error on retrieving user
        if(this.user==null) return;

        this.usernameLabel.setText(user.getUsername());
        //this.sexLabel.setText(String.valueOf(user.isSex()));
        this.descriptionLabel.setText(user.getDescription());
        this.birthdayLabel.setText(user.getBirthDate().toString());
    }
}
