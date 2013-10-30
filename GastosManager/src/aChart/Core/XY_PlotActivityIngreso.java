package aChart.Core;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.SeriesSelection;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import Database.GastoIngreso;
import Database.UsersDataSource;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.gastosManager.R;

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

    /** The main dataset that includes all the series that go into a chart. */
    private XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();
    /** The main renderer that includes all the renderers customizing a chart. */
    private XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
    /** The most recently added series. */
    private TimeSeries mCurrentSeries;
    /** The most recently created renderer, customizing the current series. */
    private XYSeriesRenderer mCurrentRenderer;
    /** Button for creating a new series of data. */
    private Button mNewSeries;
    /** Button for adding entered data to the current series. */
    private Button mAdd;
    /** Edit text field for entering the X value of the data to be added. */
    private EditText mX;
    /** Edit text field for entering the Y value of the data to be added. */
    private EditText mY;
    /** The chart view that displays the data. */
    private GraphicalView mChartView;

    private long user_id;

    private UsersDataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_xy_plot);
	Bundle extras = getIntent().getExtras();
	user_id = extras.getLong("Key");
	newTimeSeries();

    }

    private void oldOnCreate()
    {
	mRenderer.setApplyBackgroundColor(true);
	mRenderer.setBackgroundColor(Color.argb(85, 125, 255, 255));

	mRenderer.setAxisTitleTextSize(16);
	mRenderer.setChartTitleTextSize(20);
	mRenderer.setXTitle("Dia");
	mRenderer.setYTitle("Ingreso");
	mRenderer.setXAxisMin(0);
	mRenderer.setXAxisMax(30);
	mRenderer.setYAxisMin(0);
	mRenderer.setYAxisMax(15);
	// mRenderer.setLabelsTextSize(15);
	// mRenderer.setLegendTextSize(15);
	mRenderer.setMargins(new int[] { 20, 30, 15, 0 });
	mRenderer.setZoomButtonsVisible(true);
	mRenderer.setPointSize(5);
	mRenderer.setXLabels(12);
	mRenderer.setYLabels(10);
	// mRenderer.addXTextLabel(1, "Jan");
	// newSeries();
	// recogerDatos30dias();
	// newTimeSeries();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
	super.onSaveInstanceState(outState);
	// save the current data, for instance when changing screen orientation
	outState.putSerializable("dataset", mDataset);
	outState.putSerializable("renderer", mRenderer);
	outState.putSerializable("current_series", mCurrentSeries);
	outState.putSerializable("current_renderer", mCurrentRenderer);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.xy__plot, menu);
	return true;
    }

    /** Este metodo crea una nueva serie para nuestro chart */
    public void newSeries()
    {
	String seriesTitle = "Series " + (mDataset.getSeriesCount() + 1);
	// create a new series of data
	// XYSeries series = new XYSeries(seriesTitle);
	// mDataset.addSeries(series);
	// mCurrentSeries = series;
	// create a new renderer for the new series
	XYSeriesRenderer renderer = new XYSeriesRenderer();
	mRenderer.addSeriesRenderer(renderer);
	// set some renderer properties
	renderer.setPointStyle(PointStyle.CIRCLE);
	renderer.setFillPoints(true);
	renderer.setDisplayChartValues(true);
	renderer.setDisplayChartValuesDistance(10);
	mCurrentRenderer = renderer;
	// setSeriesWidgetsEnabled(true); do nothing method Only in this class
	// XY_PLOTACTIVITY
	// mChartView.repaint();

    }

    public void newTimeSeries()
    {
	int conteo = 30;
	TimeSeries ingresosSeries = new TimeSeries("Gastos");
	String[] fechas = new String[30];
	Date date = new Date(); // your date
	Calendar cal = Calendar.getInstance();
	cal.setTime(date); // Aqui retornamos el mes actual, y luego. Se debe
			   // restar 30 dias
	cal.add(Calendar.DAY_OF_MONTH, -30);
	int year = cal.get(Calendar.YEAR);
	int month = cal.get(Calendar.MONTH);
	int day = cal.get(Calendar.DAY_OF_MONTH);// Al parecer esto no es
						 // posible => Day of the month
						 // -1

	for (int i = 0; i < conteo; i++)
	{
	    GregorianCalendar gc = new GregorianCalendar();
	    gc.set(year, month, day + i);
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	    sdf.setCalendar(gc);
	    String dateFormatted = sdf.format(gc.getTime());

	    fechas[i] = dateFormatted;
	}
	datasource = new UsersDataSource(this);
	datasource.open();
	ArrayList<GastoIngreso> ingresos = sacarElementosDeUsuarioIngresos((ArrayList<GastoIngreso>) datasource
		.darIngresos30DiasAntes());
	datasource.close();

	for (int i = 0; i < fechas.length-1; i++)
	{
	    for (int j = 0; j < ingresos.size()-1; j++)
	    {
		SimpleDateFormat iso8601Format = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat compareFormat = new SimpleDateFormat(
			"yyyy-MM-dd");
		SimpleDateFormat compareFormatIngresos = new SimpleDateFormat(
			"yyyy-MM-dd");
		

		String fechaIngreso = ingresos.get(j).getFecha();
		try
		{
		    Date dateIngreso = compareFormat.parse(fechaIngreso);
		    // Se necesita dejar las fechas en este formato "yyyy-MM-dd"
		    if (fechas[i].equals(ingresos.get(j).getFecha()) )
		    {
			
			//Convertir fecha a numero ?
			String fecha = fechas[i];
			Date dateTmp = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(fecha);
			agregarValorXY(dateTmp, ingresos.get(j).getValor());

		    }
		} catch (ParseException e)
		{
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}

	    }//Fin del segundo for en teoria el mas corto.
	}//Fin del primer For y el mas grande

	// Creating a dataset to hold each series
	XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();

	// Adding Visits Series to the dataset
	// dataset.addSeries();

	// Adding Visits Series to dataset
	dataset.addSeries(mCurrentSeries);

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
	multiRenderer.setYTitle("Count");
	multiRenderer.setZoomButtonsVisible(true);

	// Adding visitsRenderer and viewsRenderer to multipleRenderer
	// Note: The order of adding dataseries to dataset and renderers to
	// multipleRenderer
	// should be same
	multiRenderer.addSeriesRenderer(visitsRenderer);
	multiRenderer.addSeriesRenderer(viewsRenderer);

	// Getting a reference to LinearLayout of the MainActivity Layout
	LinearLayout chartContainer = (LinearLayout) findViewById(R.id.chart);

	// Creating a Time Chart
	//Here we have the error, now is time to fix it. HERE!!!!!
	mChartView = (GraphicalView) ChartFactory.getTimeChartView(getBaseContext(), dataset, multiRenderer, "yyyy-MM-dd"); //

	multiRenderer.setClickEnabled(true);
	multiRenderer.setSelectableBuffer(10);

    }

    public void agregarValorXY(Date x, long y)
    {

	mCurrentSeries.add(x, y);
    }

    /**
     * Debemos comparar las fechas, desde el dia de hoy y decirle que. Desde la
     * fecha actual o menos tomar los ultimos 30 dias.
     */
    public void recogerDatos30dias()
    {
	datasource = new UsersDataSource(this);
	datasource.open();
	ArrayList<GastoIngreso> list = new ArrayList<GastoIngreso>();
	list = sacarElementosDeUsuarioIngresos((ArrayList<GastoIngreso>) datasource
		.darIngresos30DiasAntes());

	for (int i = 0; i <= list.size() - 1; i++)
	{
	    long y = list.get(i).getValor();
	    String date = list.get(i).getFecha();
	    String string = date;
	    Date date1;
	    long x = 0;
	    try
	    {
		date1 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
			.parse(string);
		x = date1.getDay();
	    } catch (ParseException e)
	    {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }

	    // agregarValorXY(x - (x - 1), y);
	}

    }

    public ArrayList<GastoIngreso> sacarElementosDeUsuarioIngresos(
	    List<GastoIngreso> lista)
    {
	ArrayList<GastoIngreso> arregloGastoIngreso = new ArrayList<GastoIngreso>();
	ArrayList<GastoIngreso> arregloIngresos = new ArrayList<GastoIngreso>();
	for (int i = 0; i < lista.size(); i++)
	{
	    if (lista.get(i).getId_usuario() == user_id
		    && lista.get(i).getIngresoGasto().toString().length() == 7)
	    {
		arregloGastoIngreso.add(lista.get(i));
	    }
	}

	// return arregloGastoIngreso; Este es para devolver todo basado en la
	// base de datos
	return arregloGastoIngreso;

    }

    @Override
    protected void onResume()
    {
	super.onResume();
	if (mChartView == null)
	{
	    LinearLayout layout = (LinearLayout) findViewById(R.id.chart);
	    mChartView = ChartFactory.getLineChartView(this, mDataset,
		    mRenderer);
	    // enable the chart click events
	    mRenderer.setClickEnabled(true);
	    mRenderer.setSelectableBuffer(10);
	    mChartView.setOnClickListener(new View.OnClickListener()
	    {
		public void onClick(View v)
		{
		    // handle the click event on the chart
		    SeriesSelection seriesSelection = mChartView
			    .getCurrentSeriesAndPoint();
		    if (seriesSelection == null)
		    {
			Toast.makeText(XY_PlotActivityIngreso.this,
				"No chart element", Toast.LENGTH_SHORT).show();
		    } else
		    {
			// display information of the clicked point
			Toast.makeText(
				XY_PlotActivityIngreso.this,
				"Chart element in series index "
					+ seriesSelection.getSeriesIndex()
					+ " data point index "
					+ seriesSelection.getPointIndex()
					+ " was clicked"
					+ " closest point value X="
					+ seriesSelection.getXValue() + ", Y="
					+ seriesSelection.getValue(),
				Toast.LENGTH_SHORT).show();
		    }
		}
	    });
	    layout.addView(mChartView, new LayoutParams(
		    LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
	    boolean enabled = mDataset.getSeriesCount() > 0;
	    // setSeriesWidgetsEnabled(enabled);
	} else
	{
	    mChartView.repaint();
	}
    }

}
