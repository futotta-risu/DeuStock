package es.deusto.deustock.client.controllers;

import javax.swing.JOptionPane;

import es.deusto.deustock.client.data.User;
import es.deusto.deustock.client.gateways.DeustockGateway;
import es.deusto.deustock.client.visual.ViewPaths;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * Controller para LoginView 
 * <br><strong>Pattern</strong>
 * <ul>
 *      <li>Controller</li>
 * </ul>
 * 
 * @author landersanmillan
 */
public class LoginController {
	
	@FXML
    private TextField usernameTxt;
	
	@FXML
    private TextField passwordTxt;

	@FXML
	private Button loginButton;
	
	@FXML
	private Button registerBtn;

	public LoginController(){};
	 
	@FXML
	private void initialize(){

		loginButton.setOnMouseClicked(
				mouseEvent -> {
					User login = login();

					if(login==null) return;

					MainController.getInstance().initGenericStage(login.getUsername());
					MainController.getInstance().loadAndChangePane(
							ViewPaths.HomeViewPath
					);
				}
		);
		
		registerBtn.setOnMouseClicked(
				mouseEvent -> MainController.getInstance().loadAndChangeScene(
							ViewPaths.RegisterViewPath
					)
			);
	}

	
	@FXML
	private User login(){
		String username = usernameTxt.getText();
		String password = passwordTxt.getText();

		DeustockGateway dg = new DeustockGateway();
		if(username.isEmpty() || password.isEmpty())
			JOptionPane.showInternalConfirmDialog(
					null, "CAMPOS VACIOS", "ERROR", JOptionPane.YES_NO_OPTION
			);

		return dg.login(username, password);
	}


}
