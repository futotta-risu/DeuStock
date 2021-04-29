package es.deusto.deustock.client.visual.stocks.model;

import javafx.beans.property.SimpleStringProperty;

public class StockModel {
    private final SimpleStringProperty price;
    private final SimpleStringProperty media;
    private final SimpleStringProperty desviacionEstandar;

    public StockModel(String price, String media, String desviacionEstandar) {
        this.price = new SimpleStringProperty(price);
        this.media = new SimpleStringProperty(media);
        this.desviacionEstandar = new SimpleStringProperty(desviacionEstandar);
    }

	public String getPrice() {
		return price.get();
	}

	public String getMedia() {
		return media.get();
	}

	public String getDesviacionEstandar() {
		return desviacionEstandar.get();
	}
}