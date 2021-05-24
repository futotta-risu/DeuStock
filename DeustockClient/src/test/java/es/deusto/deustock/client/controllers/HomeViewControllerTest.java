package es.deusto.deustock.client.controllers;

import es.deusto.deustock.client.data.Stock;
import es.deusto.deustock.client.gateways.DeustockGateway;
import es.deusto.deustock.client.gateways.exceptions.ForbiddenException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.jcip.annotations.NotThreadSafe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testfx.api.FxToolkit.registerPrimaryStage;

@ExtendWith(ApplicationExtension.class)
class HomeViewControllerTest extends ApplicationTest {

    private HomeViewController homeViewController;
    private DeustockGateway gateway;
    private Stock stock;
    private VBox stockList;

    @BeforeEach
    public void setupSpec() throws TimeoutException {
        if (Boolean.getBoolean("headless")) {
            System.setProperty("testfx.robot", "glass");
            System.setProperty("testfx.headless", "true");
            System.setProperty("prism.order", "sw");
            System.setProperty("prism.text", "t2k");
            System.setProperty("java.awt.headless", "true");
        }
        registerPrimaryStage();
    }

    public void start(Stage stage) throws IOException {
        // set up the scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/HomeView.fxml"));

        Parent root = loader.load();
        homeViewController = loader.getController();
        Scene scene = new Scene(root);

        stockList = homeViewController.stockList;

        stage.setScene(scene);
        stage.show();

    }

    @BeforeEach
    void setUp(){
        this.gateway = mock(DeustockGateway.class);
    }

    @Test
    void testGetStockListHasSameNumberOfStocks(){
        //Given
        List<Stock> stockList2 = new ArrayList<>();
        Stock amz = new Stock("AMZ", 20.0);
        Stock rmx = new Stock("RMX", 20.0);
        stockList2.add(rmx);
        stockList2.add(amz);

        //When
        when(gateway.getStockList(anyString())).thenReturn(stockList2);
        homeViewController.setDeustockGateway(gateway);

        //Then
        assertEquals(gateway.getStockList("SHORT").size(), 2);
    }

    @Test
    void testGetStockListIsEmpty(){
        //Given
        List<Stock> stockList2 = new ArrayList<>();

        //When
        when(gateway.getStockList(anyString())).thenReturn(stockList2);
        homeViewController.setDeustockGateway(gateway);

        //Then
        assertEquals(gateway.getStockList("SHORT").size(), 0);
    }
}