package com.example.gastosManager;

import java.sql.Date;

import Database.UsersDataSource;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.example.gastosManager.R.id;
import com.gastosManager.logica.GastoIngreso;

public class Registro_NuevoGasto extends Activity {
	private long user_id1;
	private UsersDataSource datasource;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registro__nuevo_gasto);
		Bundle extras = getIntent().getExtras();
		long user_id = extras.getLong("key");
		user_id1 = user_id;
		//Open the database, and open it, to chango to the write mode.
		datasource = new UsersDataSource(this);
		datasource.open();
		
		findViewById(R.id.buttonCancel).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						finish();
					}
				});
		
		findViewById(R.id.buttonOK).setOnClickListener(
				new View.OnClickListener() {
					
					private long theDate;

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						GastoIngreso gasto = new GastoIngreso();
						EditText editTConcepto = (EditText) findViewById(id.editTextConcepto);
						String concepto = editTConcepto.getText().toString();
						EditText editTValor = (EditText) findViewById(id.editTextValor);
						int valor = Integer.parseInt(editTValor.getText().toString());
						gasto.setConcepto(concepto);
						gasto.setIngresoGasto("gasto");
						gasto.setValor(valor);
						gasto.setId_usuario(user_id1);
						theDate = 15;
						Date sqlDate = new Date(theDate);
						gasto.setFecha(sqlDate);
						datasource.crearNuevoGastoIngreso(gasto);
						finish();
					}
				});
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registro__nuevo_gasto, menu);
		return true;
	}

}
