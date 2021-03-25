package es.deusto.deustock.client.visual;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application{

    private Stage primaryStage;
    private VBox rootLayout;

    public static void main(String[] args){
        Application.launch(args);
        System.out.println("Erik");
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Deustock2");
        this.primaryStage = stage;
        initRootLayout();
    }

    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(getClass().getResource("/views/StockView.fxml"));
            rootLayout = (VBox) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
