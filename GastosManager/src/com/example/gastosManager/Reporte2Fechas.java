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

	TextView tv = (TextView) findViewById(R.id.textViewInformes);
	if (gastoIngreso == 0)
	{
	    tv.setText("Reporte ingreso");
	}
	if (gastoIngreso == 1)
	{
	    tv.setText("Reporte gastos");
	}

	Button buttonAceptar = (Button) findViewById(R.id.buttonAceptar);
	Button buttonCancelar = (Button) findViewById(R.id.buttonAceptar);

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
			datePickerInicio.getYear() - 1900, datePickerInicio
				.getMonth(), datePickerInicio.getDayOfMonth());
		fechaInicial.getTime();

		if (verificarFecha(fechaFinal)
			&& verificarFechaInicialFinal(fechaInicial, fechaFinal))
		{
		    // La fecha es correcta y no se pasa del dia de hoy
		    Log.d("Este metodo debe Funcionar", " Bien");
		    Intent i = new Intent(Reporte2Fechas.this,
			    InformesGastosIngresos.class); // Agrega la
							   // informacion.
		    i.putExtra("dateI", fechaInicial.getTime());
		    i.putExtra("dateF", fechaFinal.getTime());
		    i.putExtra("IG", gastoIngreso);
		    startActivity(i);

		}

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
	java.util.Date sqlDate = new java.util.Date(fechaFinal2.getDate());
	System.out.println(dateLocal + "Fecha Celular");
	System.out.println(datePicker + "Fecha DatePicker");

	if (datePicker.after(dateLocal))
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
	    esCorrecto = false; //Si la fecha final es mayor a la inicial es false
	}
	if (fechaInicial2.equals(fechaFinal1))
	{
	    esCorrecto = false; //Si la fecha inicial es igual a la final es false
	}

	return esCorrecto; // Si todo esto es correcto sera true.

    }

}
