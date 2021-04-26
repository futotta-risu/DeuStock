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
int i = 0;
        System.out.println("T-" + i++);
        stockListButton.setOnMouseClicked(
            mouseevent -> MainController.getInstance().loadAndChangePane(
                    ViewPaths.StockListViewPath
            )
        );
        System.out.println("T-" + i++);
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
        System.out.println("T-" + i++);
        aboutButton.setOnMouseClicked(
                mouseevent -> MainController.getInstance().loadAndChangePane(
                        ViewPaths.AboutUsViewPath
                )
        );
        System.out.println("T-" + i++);
        homeButton.setOnMouseClicked(
                mouseEvent -> MainController.getInstance().loadAndChangePane(
                        ViewPaths.HomeViewPath)
        );
        System.out.println("T-" + i++);
        myStocksButton.setOnMouseClicked(
                mouseevent -> MainController.getInstance().loadAndChangePaneWithParams(
                        ViewPaths.ActualBalanceView, new HashMap<>() {{ put("username", username); }}
                )
        );

        System.out.println("T-" + i++);
    }

    public String getUsername() {
        return this.username;
    }



    @Override
    public void setParams(HashMap<String, Object> params) {
        int i = 0;
        System.out.println("Q-" + i++);
        if(params.containsKey("username"))
            this.username = String.valueOf(params.get("username"));
        System.out.println("Q-" + i++);
        profileButton.setText(username);
        System.out.println("Q-" + i++);
    }
}
