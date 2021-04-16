package es.deusto.deustock.util.template;

import static es.deusto.deustock.dataminer.gateway.socialnetworks.SocialNetworkGatewayEnum.Twitter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.util.Calendar;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import org.knowm.xchart.BitmapEncoder;

import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.dataminer.Extractor;
import es.deusto.deustock.dataminer.gateway.socialnetworks.SocialNetworkQueryData;
import es.deusto.deustock.util.chart.TimeChart;

import yahoofinance.histquotes.HistoricalQuote;


public class PdfGenerator {
	
	private static PdfGenerator INSTANCE;
	
	public static PdfGenerator getInstance() {
		if (INSTANCE == null) 
			INSTANCE = new PdfGenerator();
		return INSTANCE;
	}
	
	public byte[] createPdfStockReport(DeuStock deustock) throws IOException {
	    try (PDDocument document = new PDDocument()) {
	      PDPage page = new PDPage(PDRectangle.A4);
	 
	      float pageWidth = page.getMediaBox().getWidth();
	      float pageHeight = page.getMediaBox().getHeight();
	 
	      PDPageContentStream contentStream = new PDPageContentStream(document, page);
	 
	      
	      
	      System.out.println("Height" + pageHeight + "Width" + pageWidth);
	      
	      PDImageXObject chartImage = JPEGFactory.createFromImage(document,
	    		  BitmapEncoder.getBufferedImage(TimeChart.getInstance().getTimeChart(deustock, 350, 500)));
		  System.out.println("DESPUES6");
	      contentStream.drawImage(chartImage, 25,350);
		  System.out.println("DESPUES7");
		  
	      contentStream.beginText();
	      contentStream.setFont(PDType1Font.TIMES_ROMAN, 24);
	      contentStream.newLineAtOffset(25, 800);
	      contentStream.showText(deustock.getAcronym());
	      contentStream.endText();
	      
	      contentStream.beginText();
	      contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
	      contentStream.newLineAtOffset(25, 750);
	      contentStream.showText("Precio [ " + Calendar.getInstance().getTime().toString() + " ] = " + deustock.getPrice() + " €");
	      contentStream.endText();
	      
	      contentStream.beginText();
	      contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
	      contentStream.newLineAtOffset(25, 300);
	      contentStream.showText("Media del precio = " + calcularMediaPrecio(deustock.getHistory())  + " €");
	      contentStream.endText();
	      
	      contentStream.beginText();
	      contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
	      contentStream.newLineAtOffset(25, 275);
	      contentStream.showText("Desviacion Estandar del precio = " + calcularSD(deustock.getHistory())  + " €");
	      contentStream.endText();
	      
//	      Extractor ext = new Extractor(Twitter);
//	      String sentiment = String.valueOf(ext.getSentimentTendency(new SocialNetworkQueryData(deustock.getAcronym())));
//	      System.out.println(sentiment);
//
//	      contentStream.beginText();
//	      contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
//	      contentStream.newLineAtOffset(25, 275);
//	      contentStream.showText("Sentimiento de twitter = " + sentiment);
//	      contentStream.endText();
	      
	       
	      contentStream.close();	 
	      document.addPage(page);
	 
	      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	      document.save(byteArrayOutputStream);
	      return byteArrayOutputStream.toByteArray();
	    }
	 
	}
	
	private double calcularMediaPrecio(List<HistoricalQuote> precios) {
		double result = 0;
		for (HistoricalQuote hq : precios) {
			result += Double.parseDouble(hq.getClose().toString());
		}
		result = result / precios.size();
		return result;
	}
	
	public static double calcularSD(List<HistoricalQuote> precios){
        double result = 0.0, standardDeviation = 0.0;
        
        for(HistoricalQuote hq : precios) {
            result += Double.parseDouble(hq.getClose().toString());
        }
        double mean = result/precios.size();
        for(HistoricalQuote hq : precios) {
            standardDeviation += Math.pow(Double.parseDouble(hq.getClose().toString()) - mean, 2);
        }

        return Math.sqrt(standardDeviation/precios.size());
    }
	
	
	
	
	
}
