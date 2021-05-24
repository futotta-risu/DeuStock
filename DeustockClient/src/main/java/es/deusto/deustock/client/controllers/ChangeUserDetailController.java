package es.deusto.deustock.client.controllers;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


import es.deusto.deustock.client.data.User;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
/**
 * Controller class that contains functions for the control of the ChangeUserDetailView.fxml view
 * @author amayi
 */

public class ChangeUserDetailController implements DSGenericController{
	
	private String username = null;
    private User user;
	
    @FXML 
    private Label usernameLabel;
    
	@FXML
    private TextField fullNameTxt;
	
	@FXML
    private DatePicker birthDatePicker;
	
	@FXML
    private TextArea aboutMeTxt;
	
	@FXML
    private ChoiceBox<String> countryChoice;
	
	@FXML
	private Button changeBtn;
	
	@FXML
	private Button cancelBtn;

	/**
	 * Default no-argument constructor
	 */

	public ChangeUserDetailController() {}

	/**
	 * Method that calls the initRoot method
	 *
	 * @see #initRoot()
	 */
	@FXML
	private void initialize() {
		initRoot();
	}

	/**
	 * Method that sets the parameter username of the class
	 *
	 * @param params collects all the received objects with their respective key in a HashMap
	 *
	 * @see #initRoot()
	 */
	public void setParams(HashMap<String, Object> params) {
		if(params.containsKey("username"))
			this.username = String.valueOf(params.get("username"));
		initRoot();
	}

	/**
	 * Method that updates all the variables of the user making sure some fields are not empty
	 */

	private void update(){

		Dialog<String> dialog = new Dialog<String>();
		dialog.setTitle("ERROR");
		ButtonType type = new ButtonType("Ok", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().add(type);

		String fullName = fullNameTxt.getText();
		LocalDate date = birthDatePicker.getValue();
		Date birthDate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
		String aboutMe = aboutMeTxt.getText();
		String country = countryChoice.getValue();

		DeustockGateway dg = new DeustockGateway();

		if(!fullName.equals("")  && !aboutMe.equals("") ) {
			User user = new User()
					.setCountry(country)
					.setDescription(aboutMe)
					.setFullName(fullName)
					.setUsername(username);
			if(dg.updateUser(user, MainController.getInstance().getToken())) {
				MainController.getInstance().loadAndChangePane(
						ViewPaths.UserDetailViewPath
				);
			}else {
				dialog.setContentText("NO SE HA POIDO REALIZAR EL CAMBIO");
				dialog.showAndWait();
			}
		}else {
			dialog.setContentText("CAMPOS NULOS");
			dialog.showAndWait();
		}

	}

	/**
	 * Method that devolves the user using a function of gateway with the username as a parameter
	 */
	private void getUser(){
		DeustockGateway gateway = new DeustockGateway();
		this.user = gateway.getUser(this.username);
	}

	/**
	 * Method that initializes the instances corresponding to the elements in the FXML file and the functions of the
     * change and cancel buttons.
	 *
	 * @see #getUser()
	 */
	private void initRoot(){

		if(this.username == null) return;

		if(this.user == null || !this.user.getUsername().equals(this.username)) {
			getUser();
		}
		//this.usernameLabel.setText(user.getUsername());

		//Comprobar que funciona la lista de countries
		List<String> countries = new ArrayList<String>();
		for (Locale locale : Locale.getAvailableLocales())
		{
			 countries.add(locale.getDisplayCountry());
		}

		countryChoice.setValue("Seleciona un pais");
		countryChoice.setTooltip(new Tooltip("Seleciona un pais"));
		countryChoice.setItems(FXCollections.observableArrayList(countries));

		birthDatePicker.setValue(java.time.LocalDate.now());

		changeBtn.setOnMouseClicked(
				mouseEvent -> {
						update();
				}
		);

		cancelBtn.setOnMouseClicked(
				MouseEvent -> MainController.getInstance().loadAndChangePane(ViewPaths.UserDetailViewPath)
		);

	}

}   