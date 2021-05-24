package es.deusto.deustock.client;

import es.deusto.deustock.client.controllers.MainController;
import es.deusto.deustock.client.visual.ViewPaths;
import javafx.application.Application;

import javafx.stage.Stage;

/**
 *
 * @author Erik B. Terres
 */
public class Main extends Application{

    private MainController controller = MainController.getInstance();

    public void setMainController(MainController controller){
        this.controller = controller;
    }

    public static void main(String[] args){
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        controller.setStage(stage);
        controller.setDefaultStageParams();

        initRootLayout();
    }


    public void initRootLayout() {
        controller.loadAndChangeScene(ViewPaths.LoginViewPath);
    }
}
