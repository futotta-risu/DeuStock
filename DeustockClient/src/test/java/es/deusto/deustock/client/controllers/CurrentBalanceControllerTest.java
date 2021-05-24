package es.deusto.deustock.client.controllers;

import es.deusto.deustock.client.data.User;
import es.deusto.deustock.client.data.stocks.StockHistory;
import es.deusto.deustock.client.gateways.DeustockGateway;
import es.deusto.deustock.client.simulation.investment.operations.OperationType;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import net.jcip.annotations.NotThreadSafe;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testfx.api.FxToolkit.registerPrimaryStage;

@NotThreadSafe
@ExtendWith(ApplicationExtension.class)
public class CurrentBalanceControllerTest extends ApplicationTest {

    private CurrentBalanceController controller;
    private Label balanceLabel;
    private DeustockGateway mockGateway;

    @BeforeAll
    public static void setupSpec() throws Exception {
        if (Boolean.getBoolean("headless")) {
            System.setProperty("testfx.robot", "glass");
            System.setProperty("testfx.headless", "true");
            System.setProperty("prism.order", "sw");
            System.setProperty("prism.text", "t2k");
            System.setProperty("java.awt.headless", "true");
        }
        registerPrimaryStage();
    }

    @Override
    public void start(Stage stage) throws IOException {
        // set up the scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/investment/wallet/CurrentBalanceView.fxml"));
        System.out.println(loader);
        Parent root = loader.load();
        controller = loader.getController();
        Scene scene = new Scene(root);

        balanceLabel = controller.moneyLabel;
        stage.setScene(scene);
        stage.show();

        this.mockGateway = mock(DeustockGateway.class);
    }

    @BeforeEach
    void setUp(){
        this.mockGateway = mock(DeustockGateway.class);
    }

    @Test
    void testOpenOnUser() {
        // Given
        User user = new User();
        user.setUsername("Test2");
        HashMap<String, Object> params = new HashMap<>();
        params.put("username", "Test");
        List<StockHistory> sh = new ArrayList<StockHistory>();

        when(mockGateway.getUser(anyString())).thenReturn(user);
        when(mockGateway.getBalance(anyString())).thenReturn(9999.0);
        when(mockGateway.getHoldings(anyString())).thenReturn(sh);
        controller.setDeustockGateway(mockGateway);

        // When & Then
        assertDoesNotThrow( () -> Platform.runLater(() -> controller.setParams(params)) );

    }

    @Test
    void testOpenOnNullUser() {
        // Given
        HashMap<String, Object> params = new HashMap<>();
        params.put("grfhrt", "Tetrhtst");

        when(mockGateway.getUser(anyString())).thenReturn(null);
        controller.setDeustockGateway(mockGateway);

        // When & Then
        assertDoesNotThrow( () -> Platform.runLater(() -> controller.setParams(params)) );
    }


    @Test
    void testRefreshStocks() {
        // Given
        User user = new User();
        user.setUsername("Test2");
        HashMap<String, Object> params = new HashMap<>();
        params.put("username", "Test");

        StockHistory sh1 = new StockHistory().setActualPrice(20.3).setOpenPrice(3.5).setSymbol("BB").setAmount(4).setOperation(OperationType.SHORT);
        List<StockHistory> sh = new ArrayList<StockHistory>();
        sh.add(sh1);

        when(mockGateway.getUser(anyString())).thenReturn(user);
        when(mockGateway.getBalance(anyString())).thenReturn(9999.0);
        when(mockGateway.getHoldings(anyString())).thenReturn(sh);
        controller.setDeustockGateway(mockGateway);

        // When & Then
        Platform.runLater( () -> {
            controller.setParams(params);
            assertEquals(9999.0+" €", balanceLabel.getText());
        });

    }


}
