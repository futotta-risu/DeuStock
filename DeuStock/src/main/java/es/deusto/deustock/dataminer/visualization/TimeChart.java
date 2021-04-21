package es.deusto.deustock.dataminer.visualization;

import java.awt.Color;
import java.awt.Font;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.Styler.LegendPosition;
import org.knowm.xchart.style.colors.ChartColor;
import org.knowm.xchart.style.colors.XChartSeriesColors;
import org.knowm.xchart.style.lines.SeriesLines;
import org.knowm.xchart.style.markers.SeriesMarkers;

import es.deusto.deustock.data.DeuStock;
import yahoofinance.histquotes.HistoricalQuote;

public class TimeChart {
	
private static TimeChart INSTANCE;
	
	public static TimeChart getInstance() {
		if (INSTANCE == null) 
			INSTANCE = new TimeChart();
		return INSTANCE;
	}
	
	@SuppressWarnings("deprecation")
	public XYChart getTimeChart(DeuStock deustock, float height, float width) {
		
		// CREATE Chart
	    XYChart chart = new XYChartBuilder().width((int) width).height((int) height).title("Price of " + deustock.getAcronym()).xAxisTitle("Fecha").yAxisTitle("Precio").build();
	 
	    // CUSTOMIZE chart
	    chart.getStyler().setPlotBackgroundColor(ChartColor.getAWTColor(ChartColor.LIGHT_GREY));
	    chart.getStyler().setPlotGridLinesColor(new Color(255, 255, 255));
	    chart.getStyler().setChartBackgroundColor(Color.WHITE);
	    chart.getStyler().setLegendBackgroundColor(Color.CYAN);
	    chart.getStyler().setChartFontColor(Color.BLACK);
	    chart.getStyler().setChartTitleBoxBackgroundColor(Color.LIGHT_GRAY);
	    chart.getStyler().setChartTitleBoxVisible(true);
	    chart.getStyler().setChartTitleBoxBorderColor(Color.BLACK);
	    chart.getStyler().setPlotGridLinesVisible(true);
	 
	    chart.getStyler().setAxisTickPadding(5);
	    chart.getStyler().setMarkerSize(12);
	    chart.getStyler().setAxisTickMarkLength(15);
	    chart.getStyler().setPlotMargin(25);
	 
	    chart.getStyler().setChartTitleFont(new Font(Font.MONOSPACED, Font.BOLD, 24));
	    chart.getStyler().setLegendFont(new Font(Font.SERIF, Font.PLAIN, 18));
	    chart.getStyler().setLegendPosition(LegendPosition.InsideSE);
	    chart.getStyler().setLegendSeriesLineLength(12);
	    chart.getStyler().setAxisTitleFont(new Font(Font.SANS_SERIF, Font.ITALIC, 18));
	    chart.getStyler().setAxisTickLabelsFont(new Font(Font.SERIF, Font.PLAIN, 13));
	    chart.getStyler().setDatePattern("dd-MM-YYYY");
	    chart.getStyler().setDecimalPattern("#0 €");
	    chart.getStyler().setLocale(Locale.CANADA);
	 
	    // GENERATE LINEAR DATA
	    List<Date> xData = new ArrayList<Date>();
	    List<Double> yData = new ArrayList<Double>();
	 
	    DateFormat sdf = new SimpleDateFormat("dd.MM.yy");
	    Date date = null;
	    List<HistoricalQuote> prices = deustock.getHistory();
	    for (HistoricalQuote historicalQuote : prices) {
	    	Calendar calendar = historicalQuote.getDate();
	    	System.out.println(calendar);
	    	System.out.println(historicalQuote.getClose());
			try {
				String year = calendar.getTime().getYear() + 1900 + "";
				System.out.println("El año es" + year);
				date = sdf.parse(calendar.getTime().getDay() + "." + calendar.getTime().getMonth() + "." + year);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		    xData.add(date);
		    yData.add(Double.parseDouble(historicalQuote.getClose().toString()));
		}

	    // SERIES
	    XYSeries series = chart.addSeries("Close Price", xData, yData);
	    series.setLineColor(XChartSeriesColors.BLACK);
	    series.setMarkerColor(Color.ORANGE);
	    series.setMarker(SeriesMarkers.CIRCLE);
	    series.setLineStyle(SeriesLines.SOLID);
	 
	    return chart;
	}

}
