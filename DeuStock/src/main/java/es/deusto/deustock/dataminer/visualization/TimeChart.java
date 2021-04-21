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
import org.knowm.xchart.style.XYStyler;
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

	public XYChart getTimeChart(DeuStock deustock, int height, int width) {
		

	    XYChart chart = new XYChartBuilder()
				.width(width).height(height)
				.title("Precio de " + deustock.getAcronym())
				.build();


		XYStyler styler = chart.getStyler();


	    styler.setPlotGridLinesColor(Color.WHITE);
		styler.setChartBackgroundColor(Color.WHITE);
	    styler.setChartTitleBoxBackgroundColor(Color.WHITE);
	    styler.setChartTitleBoxVisible(true);
		styler.setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Area);

	    styler.setChartTitleBoxBorderColor(Color.WHITE);

	    styler.setPlotGridLinesVisible(false);

		styler.setYAxisTitleVisible(false);
		styler.setXAxisTitleVisible(false);

	    styler.setAxisTickPadding(5);
	    styler.setMarkerSize(12);
	    styler.setAxisTickMarkLength(15);
	    styler.setPlotMargin(25);


		styler.setChartTitleFont(new Font(Font.MONOSPACED, Font.BOLD, 24));
	    styler.setAxisTickLabelsFont(new Font(Font.SERIF, Font.PLAIN, 13));

		styler.setLegendVisible(false);

	    styler.setDatePattern("dd-MM-YYYY");
	    styler.setDecimalPattern("#0 €");

	    styler.setLocale(Locale.CANADA);
	    styler.setAntiAlias(true);

	    List<Date> xData = new ArrayList<>();
	    List<Double> yData = new ArrayList<>();
	 
	    DateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");

	    List<HistoricalQuote> prices = deustock.getHistory();

	    for (HistoricalQuote historicalQuote : prices) {
			Date date = null;
	    	Calendar calendar = historicalQuote.getDate();
			try {
				String year = calendar.getTime().getYear() + 1900 + "";
				date = dateFormat.parse(calendar.getTime().getDay() + "." + calendar.getTime().getMonth() + "." + year);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		    xData.add(date);
		    yData.add(Double.parseDouble(historicalQuote.getClose().toString()));
		}


	    XYSeries series = chart.addSeries("Close Price", xData, yData);

	    series.setLineColor(XChartSeriesColors.BLUE);
		series.setLineStyle(SeriesLines.SOLID);
		series.setMarker(SeriesMarkers.NONE);
		series.setFillColor(new Color(200,200,255));

	 
	    return chart;
	}

}
