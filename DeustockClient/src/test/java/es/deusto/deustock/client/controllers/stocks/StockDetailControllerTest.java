package es.deusto.deustock.client.controllers.stocks;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testfx.api.FxToolkit.registerPrimaryStage;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;

import es.deusto.deustock.client.controllers.MainController;
import es.deusto.deustock.client.data.Stock;
import es.deusto.deustock.client.data.User;
import es.deusto.deustock.client.gateways.DeustockGateway;
import es.deusto.deustock.client.gateways.exceptions.ForbiddenException;
import es.deusto.deustock.client.visual.stocks.list.StockInfoLine;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.jcip.annotations.NotThreadSafe;

@NotThreadSafe
@Execution(ExecutionMode.SAME_THREAD)
@ExtendWith(ApplicationExtension.class)
class StockDetailControllerTest extends ApplicationTest{

	private StockDetailController controller;
	
	private Label acronymLabel;
    private Label priceLabel;
    private Label mediaLabel;
    private Label SDLabel;
    private Button downloadButton;
    private Button backButton, buyButton;
    private LineChart<String, Number> lineChart;
    private CategoryAxis xAxis;
    private NumberAxis yAxis;
	
	private DeustockGateway mockGateway;
    private MainController mockMainController;

    @BeforeAll
    public static void setupSpec() throws TimeoutException {
        if (Boolean.getBoolean("headless")) {
            System.setProperty("testfx.robot", "glass");
            System.setProperty("testfx.headless", "true");
            System.setProperty("prism.order", "sw");
            System.setProperty("prism.text", "t2k");
            System.setProperty("java.awt.headless", "true");
        }
    }

    @Override
    public void init() throws Exception {
        FxToolkit.registerStage(Stage::new);
    }

    @Override
    public void stop() throws Exception {
        FxToolkit.hideStage();
    }

    @Override
    public void start(Stage stage) throws IOException {
        // set up the scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/StockDetailView.fxml"));

        Parent root = loader.load();
        controller = loader.getController();
        Scene scene = new Scene(root);
        
        acronymLabel = controller.acronymLabel;
        priceLabel = controller.priceLabel;
        mediaLabel = controller.mediaLabel;
        SDLabel = controller.SDLabel;
        downloadButton = controller.downloadButton;
        backButton = controller.backButton;
        buyButton = controller.buyButton;
        lineChart = controller.lineChart;
        xAxis = controller.xAxis;
        yAxis = controller.yAxis;

        stage.setScene(scene);
        stage.show();


    }
    
    @BeforeEach
    void setUp(){
        this.mockGateway = mock(DeustockGateway.class);
        this.mockMainController = mock(MainController.class);
    }
    
    @Test
    void testOpenOnStock() throws ForbiddenException {
        // Given
        Stock stock = new Stock();
        stock.setAcronym("Test2");
        HashMap<String, Object> params = new HashMap<>();
        params.put("acronym", "Test");

        when(mockGateway.getStock(anyString(), anyString())).thenReturn(stock);
        controller.setDeustockGateway(mockGateway);
        controller.setMainController(mockMainController);

        // When & Then
        assertDoesNotThrow( () -> Platform.runLater(() -> controller.setParams(params)) );

    }

    @Test
    void testOpenOnNullStock() throws ForbiddenException {
        HashMap<String, Object> params = new HashMap<>();
        params.put("grfhrt", "Tetrhtst");

        when(mockGateway.getStock(anyString(), anyString())).thenReturn(null);
        controller.setDeustockGateway(mockGateway);
        controller.setMainController(mockMainController);

        // When & Then
        assertDoesNotThrow( () -> Platform.runLater(() -> controller.setParams(params)) );


    }
    
    @Test
    void testBackButton(FxRobot robot) throws ForbiddenException {
        // Given
        Stock stock = new Stock();
        stock.setAcronym("Test2");
        HashMap<String, Object> params = new HashMap<>();
        params.put("acronym", "Test");

        when(mockGateway.getStock(anyString(), anyString())).thenReturn(stock);
        controller.setDeustockGateway(mockGateway);
        controller.setMainController(mockMainController);

        // When & Then
        assertDoesNotThrow( () -> clickOn(backButton) );
    }
    
    @Test
    void testBuyButton(FxRobot robot) throws ForbiddenException {
        // Given
        Stock stock = new Stock();
        stock.setAcronym("Test2");
        HashMap<String, Object> params = new HashMap<>();
        params.put("acronym", "Test");

        when(mockGateway.getStock(anyString(), anyString())).thenReturn(stock);
        controller.setDeustockGateway(mockGateway);
        controller.setMainController(mockMainController);

        // When & Then
        assertDoesNotThrow( () -> clickOn(buyButton) );
    }
    
    @Test
    void testDownloadButton(FxRobot robot) throws ForbiddenException {
        // Given
        Stock stock = new Stock();
        stock.setAcronym("Test2");
        HashMap<String, Object> params = new HashMap<>();
        params.put("acronym", "Test");

        when(mockGateway.getStock(anyString(), anyString())).thenReturn(stock);
        controller.setDeustockGateway(mockGateway);
        controller.setMainController(mockMainController);

        // When & Then
        assertDoesNotThrow( () -> clickOn(downloadButton) );
        
    }
}
