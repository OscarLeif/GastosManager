package com.gastosManager;

import Database.UsersDataSource;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.example.gastosManager.R;

public class Nuevo_usuario_Activity extends Activity {
	private UsersDataSource datasource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nuevo_usuario);
		datasource = new UsersDataSource(this);
		// Creacion del boton, cancelar
		Button botonCancelar = (Button) findViewById(R.id.botonCancelar);
		botonCancelar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				cancelar(arg0);
			}
		});
		// Creacion de boton Aceptar.
		Button botonAceptar = (Button) findViewById(R.id.buttonOK);
		botonAceptar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("Do:", " Something amazing");
				// Aqui debe haber validacion de datos.
				EditText edt = (EditText) findViewById(R.id.editTextConcepto);
				String nombre = edt.getText().toString();
				datasource.open();
				datasource.agregarUsuario(nombre);
				datasource.close();

				finish();

				/**
				 * 
				 */
			}
		});
		Log.d("Do Something !!", "");
	}
	
	public void botonAceptar(View v)
	{
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nuevo_usuario, menu);
		return true;
	}

	public void cancelar(View v) {
		Log.d("Do something very interesting", "..Bla bla");
		System.out.println("Here buddy now I'm working :D");
		finish();
	}

	public void botonExtra(View v) {
		Log.d("Boton extra", " Activado");
	}

}
