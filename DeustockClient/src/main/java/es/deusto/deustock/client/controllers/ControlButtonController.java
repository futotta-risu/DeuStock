package es.deusto.deustock.client.controllers;

import es.deusto.deustock.client.visual.ViewPaths;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.HashMap;

/**
 * @author Erik B. Terres
 */
public class ControlButtonController implements DSGenericController{


    @FXML
    private Button stockListButton, helpButton, profileButton, aboutButton, homeButton, myStocksButton;

    private String username = "";

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

    public String getUsername() {
        return this.username;
    }



    @Override
    public void setParams(HashMap<String, Object> params) {
        if(params.containsKey("username")) {
            this.username = String.valueOf(params.get("username"));
        }

        profileButton.setText(username);
    }
}
