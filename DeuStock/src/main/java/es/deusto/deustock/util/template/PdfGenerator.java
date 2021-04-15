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
	 
//	      PDImageXObject chartImage = JPEGFactory.createFromImage(document,
//	          createChart((int) pageHeight, (int) pageWidth));
	      
	      System.out.println("Height" + pageHeight + "Width" + pageWidth);
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
	      contentStream.close();
	 
	      document.addPage(page);
	 
	      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	      document.save(byteArrayOutputStream);
	      return byteArrayOutputStream.toByteArray();
	    }
	 
	  }
	
	
}
