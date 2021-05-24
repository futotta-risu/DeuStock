package es.deusto.deustock.client.controllers;

import java.util.ArrayList;
import java.util.List;

import es.deusto.deustock.client.gateways.DeustockGateway;
import es.deusto.deustock.client.visual.ViewPaths;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

/**
 * Controller class that contains functions for the control of the RegisterView.fxml view
 *
 * @author landersanmillan
 */
public class RegisterController {
	
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

	/**
	 * Default no-argument constructor
	 */
	public RegisterController(){}

	/**
	 * Method that initializes the instances corresponding to the elements in the FXML file and the functions of the
	 * register and cancel button.
	 */
	@FXML
	private void initialize(){
		List<String> countries = new ArrayList<>();
		for (CountryEnum country : CountryEnum.values()) {
			countries.add(country.name());
		}
		countryChoice.setValue("Seleciona un pais");
		countryChoice.setTooltip(new Tooltip("Seleciona un pais"));
		countryChoice.setItems(FXCollections.observableArrayList(countries));
		
		birthDatePicker.setValue(java.time.LocalDate.now());

		registerBtn.setOnMouseClicked( e -> register() );
		
		cancelBtn.setOnMouseClicked(			
				mouseEvent -> MainController.getInstance().loadAndChangeScene(ViewPaths.LoginViewPath)
		);
	}


	/**
	 * Register method, uses the text added by the user in the view and by a gateway function checks if the register is correct
	 * in case of incorrect login, the function returns a dialog with a warning
	 */
	private void register()  {
	    Dialog<String> dialog = new Dialog<>();
	    dialog.setTitle("Error");
	    ButtonType type = new ButtonType("Ok", ButtonData.OK_DONE);
	    dialog.getDialogPane().getButtonTypes().add(type);
		
	    String username = usernameTxt.getText();
		String password = passwordTxt.getText();
		String fullName = fullNameTxt.getText();
		String aboutMe = aboutMeTxt.getText();


		DeustockGateway dg = new DeustockGateway();

		if(username.isBlank() || password.isBlank() || fullName.isBlank()  || aboutMe.isBlank() ) {
			dialog.setContentText("Null fields detected");
			dialog.showAndWait();
			return;
		}

		if(dg.register(username, password, fullName,  aboutMe, "Spain")) {
			MainController.getInstance().loadAndChangeScene(ViewPaths.LoginViewPath);
		}else {
			dialog.setContentText("Invalid register");
			dialog.showAndWait();
		}
	}

}
