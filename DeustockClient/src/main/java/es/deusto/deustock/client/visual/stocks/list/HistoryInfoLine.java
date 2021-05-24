package es.deusto.deustock.client.visual.stocks.list;

import es.deusto.deustock.client.data.stocks.StockHistory;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class HistoryInfoLine extends GridPane {

    StockHistory stockHistory;
    Double difference;
    Label symbol, openPrice, amount, differenceLabel;

    public HistoryInfoLine(StockHistory stockHistory) {
        this.stockHistory = stockHistory;
        initPane();
    }

    private void initPane() {
        this.symbol = new Label(stockHistory.getSymbol());
        this.symbol.getStyleClass().add("history-line-name");

        this.openPrice = new Label(stockHistory.getOpenPrice() + " €");
        this.openPrice.getStyleClass().add("history-line-price");

        //this.actualPrice = new Label(stockHistory.getActualPrice() + " €");
        //this.actualPrice.getStyleClass().add("history-line-price");

        this.amount = new Label("X " + stockHistory.getAmount());

        this.difference = stockHistory.getActualPrice() - stockHistory.getAmount();

        this.differenceLabel = new Label(this.difference + " €");
        if(difference<0){
            this.differenceLabel.setTextFill(Color.RED);
        }else{
            this.differenceLabel.setTextFill(Color.GREEN);
        }

        add(symbol,0,0);
        add(openPrice,1,0);
        //add(actualPrice,2,0);
        add(amount,2,0);
        add(differenceLabel,3,0);

        for (int i = 0; i<4; i++){
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100.0/5);
            getColumnConstraints().add(column);
        }


    }
}