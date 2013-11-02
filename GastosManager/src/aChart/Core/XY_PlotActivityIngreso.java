package aChart.Core;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.AbstractChart;
import org.achartengine.chart.PointStyle;
import org.achartengine.chart.TimeChart;
import org.achartengine.model.SeriesSelection;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import Database.GastoIngreso;
import Database.UsersDataSource;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.gastosManager.R;
import com.example.gastosManager.TablayoutActivity;

/**
 * Hay que volver a reescribir toda esta clase para que funcione correctamente
 * basado en el ejemplo de las fechas que se encontro en internet para poder
 * lograr mostrar el rango de fechas.
 * 
 * @author Leif
 * 
 */
public class XY_PlotActivityIngreso extends Activity
{
    private GraphicalView mChart;
    private long userID;
    private UsersDataSource database;
    private TimeSeries timeSeries;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_xy_plot);
	Bundle extras = getIntent().getExtras();
	userID = extras.getLong("Key");
	database = new UsersDataSource(this);

	openChart();
	

    }

    @SuppressLint("SimpleDateFormat")
    private void openChart()
    {
	database.open();
	ArrayList<GastoIngreso> ingresos = (ArrayList<GastoIngreso>) database
		.darIngresos30DiasAntes();
	database.close();
	timeSeries = new TimeSeries("Ingresos");
	TimeSeries visitsSeries = new TimeSeries("Visits");

	for (int i = 0; i < ingresos.size(); i++)
	{
	    int valor = ingresos.get(i).getValor();
	    Date fecha = new Date();
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	    String dateInString = ingresos.get(i).getFecha();
	    try
	    {
		fecha = formatter.parse(dateInString);
	    } catch (ParseException e)
	    {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }

	    visitsSeries.add(fecha, valor);

	}
	
	// Creating a dataset to hold each series
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
 
        // Adding Visits Series to the dataset
        dataset.addSeries(visitsSeries);
 

 
        // Creating XYSeriesRenderer to customize visitsSeries
        XYSeriesRenderer visitsRenderer = new XYSeriesRenderer();
        visitsRenderer.setColor(Color.WHITE);
        visitsRenderer.setPointStyle(PointStyle.CIRCLE);
        visitsRenderer.setFillPoints(true);
        visitsRenderer.setLineWidth(2);
        visitsRenderer.setDisplayChartValues(true);
 
        // Creating XYSeriesRenderer to customize viewsSeries
        XYSeriesRenderer viewsRenderer = new XYSeriesRenderer();
        viewsRenderer.setColor(Color.YELLOW);
        viewsRenderer.setPointStyle(PointStyle.CIRCLE);
        viewsRenderer.setFillPoints(true);
        viewsRenderer.setLineWidth(2);
        viewsRenderer.setDisplayChartValues(true);
 
        // Creating a XYMultipleSeriesRenderer to customize the whole chart
        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
 
        multiRenderer.setChartTitle("Visits vs Views Chart");
        multiRenderer.setXTitle("Days");
        multiRenderer.setYTitle("Ingresos");
        multiRenderer.setZoomButtonsVisible(true);
 
        // Adding visitsRenderer and viewsRenderer to multipleRenderer
        // Note: The order of adding dataseries to dataset and renderers to multipleRenderer
        // should be same
        multiRenderer.addSeriesRenderer(visitsRenderer);
        multiRenderer.addSeriesRenderer(viewsRenderer);
 
        // Getting a reference to LinearLayout of the MainActivity Layout
        LinearLayout chartContainer = (LinearLayout) findViewById(R.id.chart_container);
 
        // Creating a Time Chart
        Context t = getBaseContext();
        
        //mChart = (GraphicalView) ChartFactory.getTimeChartView(TablayoutActivity., dataset, multiRenderer,"dd-MMM-yyyy");
        
 
        multiRenderer.setClickEnabled(true);
        multiRenderer.setSelectableBuffer(10);
 
        // Setting a click event listener for the graph
        mChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Format formatter = new SimpleDateFormat("dd-MMM-yyyy");
 
                SeriesSelection seriesSelection = mChart.getCurrentSeriesAndPoint();
 
                if (seriesSelection != null) {
                    int seriesIndex = seriesSelection.getSeriesIndex();
                    String selectedSeries="Visits";
                    if(seriesIndex==0)
                        selectedSeries = "Visits";
                    else
                        selectedSeries = "Views";
 
                    // Getting the clicked Date ( x value )
                    long clickedDateSeconds = (long) seriesSelection.getXValue();
                    Date clickedDate = new Date(clickedDateSeconds);
                    String strDate = formatter.format(clickedDate);
 
                    // Getting the y value
                    int amount = (int) seriesSelection.getValue();
 
                    // Displaying Toast Message
                    Toast.makeText(
                        getBaseContext(),
                        selectedSeries + " on "  + strDate + " : " + amount ,
                        Toast.LENGTH_SHORT).show();
                    }
                }
            });
 
            // Adding the Line Chart to the LinearLayout
            chartContainer.addView(mChart);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.xy__plot, menu);
        return true;
    }
    

}
