package com.example.gastosManager;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import Database.GastoIngreso;
import Database.UsersDataSource;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gastosManager.R.id;

/**
 * Esta es la clase que registra un nuevo gasto o, nuevo ingreso. Dependiendo de
 * donde se mande la orden esteasignara un gasto o un ingreso. esta clase
 * primero recogera la informacion pasada, que son un ID de la base de datos y
 * la palabra ingreso o gasto, por que esto definira que es lo que hara.
 * */

public class Registro_NuevoGastoIngreso extends Activity {
	private long user_id1;
	private UsersDataSource datasource;
	private TextView tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registro_nuevo_gasto_ingreso);
		Bundle extras = getIntent().getExtras();
		tv = (TextView) findViewById(R.id.textViewGastoIngreso);

		final String gastoIngreso = extras.getString("GastoIngreso");
		String text = "Ingrese la informacion del " + gastoIngreso;
		tv.setText(text);
		long user_id = extras.getLong("key");
		user_id1 = user_id;
		// Open the database, and open it, to change to the write mode.

		datasource = new UsersDataSource(this);
		datasource.open();
		
		Button botonCancelar = (Button)findViewById(R.id.buttonCancel);
		Button botonOK = (Button) findViewById(R.id.buttonOK);

		botonCancelar.setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						finish();
					}
				});

		botonOK.setOnClickListener(
				new View.OnClickListener() {

					@SuppressWarnings("deprecation")
					@SuppressLint("SimpleDateFormat")
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						GastoIngreso gasto = new GastoIngreso();
						EditText editTConcepto = (EditText) findViewById(id.editTextConcepto);
						String concepto = editTConcepto.getText().toString();
						EditText editTValor = (EditText) findViewById(id.editTextValor);
						int valor = 0;
						try {
							valor = Integer.parseInt(editTValor.getText()
									.toString());
						} catch (Exception e) {
							// TODO: handle exception
							Toast.makeText(Registro_NuevoGastoIngreso.this,
									"Este espacio no puede estar vacio.",
									Toast.LENGTH_LONG);
						}

						gasto.setConcepto(concepto);
						gasto.setIngresoGasto(gastoIngreso);
						gasto.setValor(valor);
						gasto.setId_usuario(user_id1);
						DatePicker datePicker = (DatePicker) findViewById(R.id.datePickerIngresoGasto);

						SimpleDateFormat iso8601Format = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");
						Date date = new Date(datePicker.getYear() - 1900,datePicker.getMonth(), datePicker.getDayOfMonth());
						date.getTime();

						iso8601Format.format(date);

						gasto.setFecha(iso8601Format.format(date));

						if (!gasto.getConcepto().equals("")
								&& gasto.getValor() != 0
								&& verificarFecha(date)) {
							datasource.open();
							datasource.crearNuevoGastoIngreso(gasto);
							datasource.close();
							finish();
						}

						if (gasto.getConcepto().equals(""))
							Toast.makeText(Registro_NuevoGastoIngreso.this,
									"Escribe el concepto.", Toast.LENGTH_LONG)
									.show();
						if (gasto.getValor() == 0)
							Toast.makeText(
									Registro_NuevoGastoIngreso.this,
									"No se guarda si el valor es 0, o esta vacio.",
									Toast.LENGTH_LONG).show();
						if (!verificarFecha(date))
							Toast.makeText(
									Registro_NuevoGastoIngreso.this,
									"La fecha no puede ser mayor al dia de hoy.",
									Toast.LENGTH_LONG).show();
					}
					
				});
		datasource.close();
	}

	public boolean verificarFecha(Date date) {// Verifica que la fecha no sea
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

		return fechaHoy;

	}

	private String getDateTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		java.util.Date date = new java.util.Date();
		return dateFormat.format(date);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registro__nuevo_gasto, menu);
		return true;
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
	}

}
