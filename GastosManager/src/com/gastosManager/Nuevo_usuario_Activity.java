package com.gastosManager;

import java.util.ArrayList;

import Database.UsersDataSource;
import Database.Usuario;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gastosManager.R;


public class Nuevo_usuario_Activity extends Activity
{
    private UsersDataSource datasource;
    private ArrayList<Usuario> usuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.nuevo_usuario);
	datasource = new UsersDataSource(this);
	datasource.open();
	usuarios = (ArrayList<Usuario>) datasource.darTodosLosUsuario();
	datasource.close();
	// Creacion del boton, cancelar
	Button botonCancelar = (Button) findViewById(R.id.botonCancelar);
	botonCancelar.setOnClickListener(new OnClickListener()
	{

	    @Override
	    public void onClick(View arg0)
	    {
		// TODO Auto-generated method stub
		cancelar(arg0);
	    }
	});
	// Creacion de boton Aceptar.
	Button botonAceptar = (Button) findViewById(R.id.buttonOK);
	botonAceptar.setOnClickListener(new OnClickListener()
	{

	    @SuppressLint("ShowToast")
		@Override
	    public void onClick(View v)
	    {
		// TODO Auto-generated method stub
		Log.d("Do:", " Something amazing");
		// Aqui debe haber validacion de datos.
		EditText edt = (EditText) findViewById(R.id.editTextConcepto);
		String nombre = edt.getText().toString();
		if(!nombre.equals("")  )//&& !existeUsuario(nombre)
		{
		datasource.open();
		datasource.agregarUsuario(nombre);
		datasource.close();
		finish();
		}
		if(nombre.equals("") )
			Toast.makeText(Nuevo_usuario_Activity.this," Escribe tu nombre o elige uno diferente ",Toast.LENGTH_LONG).show();
		if(existeUsuario(nombre))
		{
		Toast.makeText(Nuevo_usuario_Activity.this, "Ese nombre ya existe.", Toast.LENGTH_LONG).show();	
		}
	    }

		
	});
	Log.d("Do Something !!", "");
    }

public boolean existeUsuario (String nombre)
{
	boolean existe = false;
	for (int i = 0; i < usuarios.size(); i++) 
	{
		if(nombre.equals(usuarios.get(i).getNombre())){ existe = true;}
			
	}
	return existe;
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.nuevo_usuario, menu);
	return true;
    }

    public void cancelar(View v)
    {
	Log.d("Do something very interesting", "..Bla bla");
	System.out.println("Here buddy now I'm working :D");
	finish();
    }
    
    @Override
    public void onBackPressed() {
    	// TODO Auto-generated method stub
    	super.onBackPressed();
    	finish();
    }
}
