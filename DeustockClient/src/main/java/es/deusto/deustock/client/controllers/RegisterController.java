package es.deusto.deustock.client.controllers;

import java.util.Arrays;
import java.util.Locale;

import es.deusto.deustock.client.gateways.DeustockGateway;
import es.deusto.deustock.client.visual.ViewPaths;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Controller class that contains functions for the control of the RegisterView.fxml view
 *
 * @author landersanmillan
 */
public class RegisterController {

	@FXML
	private Label registerErrorLabel;

	@FXML
    private TextField usernameTxt;
	
	@FXML
    private TextField passwordTxt;
	
	@FXML
    private TextField fullNameTxt;
	
	@FXML
    private DatePicker birthDatePicker;
	
	@FXML
    private TextArea aboutMeTxt;
	
	@FXML
    private ChoiceBox<String> countryChoice;
	
	@FXML
	private Button registerBtn;
	
	@FXML
	private Button cancelBtn;

	private DeustockGateway gateway;
	private MainController mainController;

	public RegisterController(){
		mainController = MainController.getInstance();
		gateway = new DeustockGateway();
	}

	public void setMainController(MainController mainController){
		this.mainController = mainController;
	}

	public void setGateway(DeustockGateway gateway){
		this.gateway = gateway;
	}


	/**
	 * Method that initializes the instances corresponding to the elements in the FXML file and the functions of the
	 * register and cancel button.
	 */
	@FXML
	private void initialize(){
		String[] countries = Arrays.copyOfRange(Locale.getISOCountries(), 1, 50);

		countryChoice.setValue("Seleciona un pais");
		countryChoice.setTooltip(new Tooltip("Seleciona un pais"));
		countryChoice.setItems(FXCollections.observableArrayList(countries));
		
		birthDatePicker.setValue(java.time.LocalDate.now());

		registerBtn.setOnMouseClicked( e -> register() );
		
		cancelBtn.setOnMouseClicked(			
				mouseEvent -> mainController.loadAndChangeScene(ViewPaths.LoginViewPath)
		);
		MainController.getInstance().getScene().getStylesheets().add("/views/button.css");
	}


	/**
	 * Register method, uses the text added by the user in the view and by a gateway function checks if the register is correct
	 * in case of incorrect login, the function returns a dialog with a warning
	 */
	private void register()  {
	    String username = usernameTxt.getText();
		String password = passwordTxt.getText();
		String fullName = fullNameTxt.getText();
		String aboutMe = aboutMeTxt.getText();

		if(username.isBlank() || password.isBlank() || fullName.isBlank()  || aboutMe.isBlank() ) {
			registerErrorLabel.setText("Casillas Vacias detectadas");
			return;
		}

		if(gateway.register(username, password, fullName,  aboutMe, "Spain")) {
			mainController.loadAndChangeScene(ViewPaths.LoginViewPath);
		}else {
			registerErrorLabel.setText("Registro invalido");
		}
	}

}
