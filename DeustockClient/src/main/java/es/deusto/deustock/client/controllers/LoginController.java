package es.deusto.deustock.client.controllers;

import javax.swing.JOptionPane;

import es.deusto.deustock.client.data.User;
import es.deusto.deustock.client.gateways.DeustockGateway;
import es.deusto.deustock.client.visual.ViewPaths;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginController {
	
	@FXML
    private TextField usernameTxt;
	
	@FXML
    private TextField passwordTxt;

	@FXML
	private Button loginButton;

	public LoginController(){};
	 
	@FXML
	private void initialize(){

		loginButton.setOnMouseClicked(
				mouseEvent -> {
					User login = login();

					if(login==null) return;

					MainController.getInstance().loadAndChangeScene(
							ViewPaths.UserDetailViewPath
					);
				}
		);
	}
	
	
	@FXML
	private void openRegisterWindow(){
		MainController.getInstance().loadAndChangeScene(ViewPaths.RegisterViewPath);
	}
	
	@FXML
	private User login(){
		User result = null;
		String username = usernameTxt.getText();
		String password = passwordTxt.getText();

		DeustockGateway dg = new DeustockGateway();
		if(!username.equals("") && !password.equals(""))
			result = dg.login(username, password);
		else
			JOptionPane.showInternalConfirmDialog(null, "CAMPOS VACIOS", "ERROR", 0);

		return result;
	}


}
