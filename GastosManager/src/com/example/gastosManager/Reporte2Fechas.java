package com.example.gastosManager;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import Database.UsersDataSource;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

public class Reporte2Fechas extends Activity
{
    private long id = 0;
    private int gastoIngreso; // Si es 0 es un ingreso, 1 es un gasto.
    private UsersDataSource dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_reporte2_fechas);
	Bundle extras = getIntent().getExtras();
	// Recuperamos el ID.
	id = extras.getLong("Key");
	gastoIngreso = extras.getInt("IG");

	TextView tv = (TextView) findViewById(R.id.textViewReporteAR2F);
	if (gastoIngreso == 0)
	{
	    tv.setText("Informe de ingresos");
	}
	if (gastoIngreso == 1)
	{
	    tv.setText("Informe de gastos");
	}

	Button buttonAceptar = (Button) findViewById(R.id.buttonAceptar);
	Button buttonCancelar = (Button) findViewById(R.id.buttonCancelar);

	DatePicker fechaInicial = (DatePicker) findViewById(R.id.datePickerInicial);
	DatePicker fechaFinal = (DatePicker) findViewById(R.id.datePicker1);

	buttonCancelar.setOnClickListener(new View.OnClickListener()
	{
	    @Override
	    public void onClick(View view)
	    {
		finish();
	    }
	});
	buttonAceptar.setOnClickListener(new View.OnClickListener()
	{

	    @Override
	    public void onClick(View v)
	    {
		// TODO Auto-generated method stub
		DatePicker datePickerFinal = (DatePicker) findViewById(R.id.DatePickerFinal);
		DatePicker datePickerInicio = (DatePicker) findViewById(R.id.datePickerInicial);

		SimpleDateFormat iso8601Format = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

		java.util.Date fechaInicial = new Date(datePickerInicio
			.getYear() - 1900, datePickerInicio.getMonth(),
			datePickerInicio.getDayOfMonth());
		fechaInicial.getTime();

		java.util.Date fechaFinal = new Date(
			datePickerFinal.getYear() - 1900, datePickerFinal
				.getMonth(), datePickerFinal.getDayOfMonth());
		fechaFinal.getTime();

		if (verificarFechaActual((Date) fechaFinal) && verificarFechaActual((Date)fechaInicial) && verificarFechasVariables((Date)fechaInicial, (Date)fechaFinal))
		{
		    // La fecha es correcta y no se pasa del dia de hoy
		    Log.d("Este metodo debe Funcionar", " Bien");
		    Intent i = new Intent(Reporte2Fechas.this,
			    InformesGastosIngresos.class); // Agrega la
							   // informacion.
		    i.putExtra("Key", id);
		    i.putExtra("dateI", fechaInicial);
		    i.putExtra("dateF", fechaFinal);
		    i.putExtra("IG", gastoIngreso);
		    startActivity(i);

		}
		if(verificarFechasVariables((Date)fechaInicial, (Date)fechaFinal)==false)
		    Toast.makeText(Reporte2Fechas.this, "Corrige las fechas", Toast.LENGTH_LONG).show();
		if(verificarFechaActual((Date) fechaFinal) == false)
		    Toast.makeText(Reporte2Fechas.this, "No se pueden hacer reportes que superen el dia de hoy", Toast.LENGTH_LONG).show();

	    }
	});

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.reporte2_fechas, menu);
	return true;
    }

    public boolean verificarFecha(java.util.Date fechaFinal2)
    {// Verifica que la fecha no sea
     // mayor a el dia de hoy.
	DatePicker fechaInicial = (DatePicker) findViewById(R.id.datePickerInicial);
	DatePicker fechaFinal = (DatePicker) findViewById(R.id.datePicker1);

	boolean fechaHoy = true;
	SimpleDateFormat iso8601Format = new SimpleDateFormat(
		"yyyy-MM-dd HH:mm:ss");

	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
	java.util.Date dateLocal = new java.util.Date();

	java.util.Date datePicker = new java.util.Date(fechaFinal2.getTime());

	java.util.Date dateMachine = new java.util.Date();

	if (dateLocal.after(datePicker))
	    fechaHoy = false;

	return fechaHoy;

    }

    public boolean verificarFechaInicialFinal(java.util.Date fechaInicial2,
	    java.util.Date fechaFinal)
    {
	boolean esCorrecto = true;// Iniciamos diciendo que esta correcto
	DatePicker fechaInicial = (DatePicker) findViewById(R.id.datePickerInicial);
	DatePicker fechaFinal1 = (DatePicker) findViewById(R.id.datePicker1);
	SimpleDateFormat iso8601Format = new SimpleDateFormat(
		"yyyy-MM-dd HH-mm-ss");

	if (fechaInicial2.after(fechaFinal))
	{
	    esCorrecto = false; // Si la fecha final es mayor a la inicial es
				// false
	}

	return esCorrecto; // Si todo esto es correcto sera true.

    }

    public boolean verificarFechaActual(Date date)
    {// Verifica que la fecha no sea
	// mayor a el dia de hoy.
	boolean fechaHoy = true;
	SimpleDateFormat iso8601Format = new SimpleDateFormat(
		"yyyy-MM-dd HH:mm:ss");

	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
	java.util.Date dateLocal = new java.util.Date();

	java.util.Date datePicker = new java.util.Date(date.getTime());

	java.util.Date dateMachine = new java.util.Date();
	java.util.Date sqlDate = new java.util.Date(date.getDate());
	System.out.println(dateLocal + "Fecha Celular");
	System.out.println(datePicker + "Fecha DatePicker");

	if (datePicker.after(dateLocal))
	    fechaHoy = false;

	return fechaHoy;//Debe ser verdadero si la fecha, no es menor a hoy

    }
    
    /**
     * La fecha inicial solo debe ser menor o igual a la fecha final
     * @param fechaInicial
     * @param fechaFinal
     * @return
     */
    
    public boolean verificarFechasVariables(Date fechaInicial, Date fechaFinal)
    {
	boolean cierto = true;
	if(fechaFinal.equals(fechaInicial))
	    cierto = true;
	if(fechaInicial.after(fechaFinal))
	    cierto = false;
	return cierto;//SI es cierto todo funciona correcto.
    }
    
    @Override
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        finish();
    }
    

}
