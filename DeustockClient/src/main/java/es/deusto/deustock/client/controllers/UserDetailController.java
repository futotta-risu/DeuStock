package es.deusto.deustock.client.controllers;

import es.deusto.deustock.client.data.User;
import es.deusto.deustock.client.gateways.DeustockGateway;
import es.deusto.deustock.client.visual.ViewPaths;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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

    @FXML
    Button accountDeleteButton;
    
    @FXML
    Button editProfileButton;

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

    private void deleteUser(){
        DeustockGateway gateway = new DeustockGateway();
        if(gateway.deleteUser(this.username, user.getPassword())){
            MainController.getInstance().setUser(null);
            MainController.getInstance().loadAndChangeScene(
                    ViewPaths.LoginViewPath
            );
        }
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

        this.accountDeleteButton.setOnMouseClicked(
                mouseEvent -> deleteUser()
        );
        
        String username = this.username;
        this.editProfileButton.setOnMouseClicked(
        		moseEvent -> MainController.getInstance().loadAndChangePaneWithParams(ViewPaths.ChangeUserDetailViewPath,  new HashMap<String, Object>() {{ put("username", username ); }})
        );
    }
}
