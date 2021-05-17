package es.deusto.deustock.client.controllers;




import es.deusto.deustock.client.controllers.exceptions.EmptyFieldsException;
import es.deusto.deustock.client.data.User;
import es.deusto.deustock.client.gateways.DeustockGateway;
import es.deusto.deustock.client.gateways.exceptions.ForbiddenException;
import es.deusto.deustock.client.visual.ViewPaths;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.ButtonBar.ButtonData;
import org.mortbay.log.Log;

import java.lang.reflect.Method;

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
    public TextField usernameTxt;
	
	@FXML
	public TextField passwordTxt;

	@FXML
	public Button loginButton;
	
	@FXML
	public Button registerBtn;

	@FXML
	private Button pruebaButton;

	@FXML
	public Label loginErrorLabel;

	private MainController mainController;

	private DeustockGateway gateway;

	public LoginController(){
		mainController = MainController.getInstance();
		gateway = new DeustockGateway();
	}

	public void setMainController(MainController mainController){
		this.mainController = mainController;
	}

	public void setGateway(DeustockGateway gateway){
		this.gateway = gateway;
	}

	@FXML
	private void initialize(){
		loginButton.setOnMouseClicked( mouseEvent -> login() );
		
		registerBtn.setOnMouseClicked(
				mouseEvent -> mainController.loadAndChangeScene(ViewPaths.RegisterViewPath)
		);
	}

	
	@FXML
	private void login() {
		String username = usernameTxt.getText();
		String password = passwordTxt.getText();

		if(username.isEmpty() || password.isEmpty()) {
			loginErrorLabel.setText("Campos vacios");
			return;
		}
		String token;

		try{
			token = gateway.login(username, password);
		}catch (ForbiddenException e){
			loginErrorLabel.setText("Datos Incorrectos");
			return;
		}

		mainController.initGenericStage(usernameTxt.getText(), token);
		mainController.loadAndChangePane(ViewPaths.HomeViewPath);
	}


}
