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

    public static void main(String[] args){
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("DeuStock Client");
        MainController.getInstance().setStage(stage);

        initRootLayout();
    }

    public void initRootLayout() {
        MainController.getInstance().loadAndChangeScene(
                ViewPaths.LoginViewPath
        );
    }
}
