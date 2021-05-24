package es.deusto.deustock.client.controllers;

import es.deusto.deustock.client.visual.ViewPaths;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.HashMap;

/**
 * class that contains functions for the control of the ControlButtonView.fxml view
 *
 * @author Erik B. Terres
 *
 */
public class ControlButtonController implements DSGenericController{


    @FXML
    private Button stockListButton, helpButton, profileButton, aboutButton, homeButton, myStocksButton;

    private String username = "";

    /**
     * Method that initializes the instances corresponding to the elements in the FXML file and the functions of the
     * stockList, help, profile, about, home and mySocks buttons.
     */

    @FXML
    private void initialize(){
        stockListButton.setOnMouseClicked(
            mouseevent -> MainController.getInstance().loadAndChangePane(
                    ViewPaths.StockListViewPath
            )
        );
        helpButton.setOnMouseClicked(
                mouseevent -> MainController.getInstance().loadAndChangePane(
                        ViewPaths.HelpViewPath
                )
        );
        profileButton.setText(username);
        profileButton.setOnMouseClicked(
                mouseevent -> MainController.getInstance().loadAndChangePaneWithParams(
                        ViewPaths.UserDetailViewPath, new HashMap<>() {{ put("username", username); }}
                )
        );

        aboutButton.setOnMouseClicked(
                mouseevent -> MainController.getInstance().loadAndChangePane(
                        ViewPaths.AboutUsViewPath
                )
        );

        homeButton.setOnMouseClicked(
                mouseEvent -> MainController.getInstance().loadAndChangePane(
                        ViewPaths.HomeViewPath)
        );

        myStocksButton.setOnMouseClicked(
                mouseevent -> MainController.getInstance().loadAndChangePaneWithParams(
                        ViewPaths.CurrentBalanceView,
                        new HashMap<>() {{ put("username", username); }}
                )
        );

        MainController.getInstance().getScene().getStylesheets().add("/views/button.css");
    }

    /**
     * Getter method
     * @return returns the username from the class as a String
     */
    public String getUsername() {
        return this.username;
    }



    /**
     * Method that sets the parameter username of the class and adds it to the profile button
     *
     * @param params collects all the received objects with their respective key in a HashMap
     */
    @Override
    public void setParams(HashMap<String, Object> params) {
        if(params.containsKey("username")) {
            this.username = String.valueOf(params.get("username"));
        }

        profileButton.setText(username);
    }
}
