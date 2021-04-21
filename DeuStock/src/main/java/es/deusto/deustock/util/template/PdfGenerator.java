package es.deusto.deustock.util.template;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.util.Matrix;

import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.dataminer.features.SentimentExtractor;
import es.deusto.deustock.dataminer.gateway.socialnetworks.SocialNetworkQueryData;
import es.deusto.deustock.util.chart.TimeChart;




public class PdfGenerator {
	
	private static PdfGenerator INSTANCE;
	
	public static PdfGenerator getInstance() {
		if (INSTANCE == null) 
			INSTANCE = new PdfGenerator();
		return INSTANCE;
	}
	
	public byte[] createPdfStockReport(DeuStock deustock) throws IOException, InterruptedException {
	    try (PDDocument document = new PDDocument()) {
	    	
	    	PDPage page1 = new PDPage(PDRectangle.A4);
	        this.contentStream = new PDPageContentStream(document, page1);
	       	        
	        // TWITTER ANALISIS
		    SentimentExtractor ext = new SentimentExtractor(Twitter);
		    String sentiment = String.valueOf(ext.getSentimentTendency(new SocialNetworkQueryData(deustock.getAcronym())));
	        	
		    // ADD TEXT & IMAGE TO PDF
	        addTextAtOfssets(225, 775, TIMES_ROMAN, 70, deustock.getAcronym());
	        addTextAtOfssets(25, 730, TIMES_ROMAN, 14, "Este reporte se ha generado mediante DeuStock, la informacion se ha obtenido de Yahoo Finance.");
	        addTextAtOfssets(25, 680, TIMES_ROMAN, 12, "Precio [ " + Calendar.getInstance().getTime().toString() + " ] = " + deustock.getPrice() + " €");
	        addTextAtOfssets(25, 655, TIMES_ROMAN, 12, "Media del precio = " + calcularMediaPrecio(deustock.getHistory())  + " €");
	        addTextAtOfssets(25, 630, TIMES_ROMAN, 12, "Desviacion Estandar del precio = " + calcularSD(deustock.getHistory())  + " €");
	        addTextAtOfssets(25, 605, TIMES_ROMAN, 12, "Sentimiento de twitter = " + sentiment);
	        addTextAtOfssets(25, 575, TIMES_ROMAN, 14, "En la siguiente pagina se puede observar el precio historico de " + deustock.getAcronym() + ".");
	        
	        contentStream.close();	 
	        document.addPage(page1);
	        
	    	PDPage page2 = new PDPage(PDRectangle.A3);
	    	page2.setRotation(90);
	    	float pageWidth = page2.getMediaBox().getWidth();
	        float pageHeight = page2.getMediaBox().getHeight();
	        System.out.println("Height" + pageHeight + "Width" + pageWidth);

	        this.contentStream = new PDPageContentStream(document, page2);
	        
	        // STOCK ANALISIS
	        PDImageXObject chartImage = JPEGFactory.createFromImage(document,
	    		  BitmapEncoder.getBufferedImage(TimeChart.getInstance().getTimeChart(deustock, pageWidth, pageHeight)));
	        
	        contentStream.transform(new Matrix(0, 1, -1, 0, pageWidth, 0));
	        contentStream.drawImage(chartImage, 0, 0);
	        contentStream.close();
	        document.addPage(page2);

	        
	        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	        document.save(byteArrayOutputStream);
	        
	        return byteArrayOutputStream.toByteArray();
	    }
	 
	  }
	
	
}
