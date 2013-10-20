package com.example.gastosManager;

import java.sql.Date;

/**
 * Esta es la clase que registra un nuevo gasto o, nuevo ingreso.
 * Dependiendo de donde se mande la orden esteasignara un gasto o un ingreso.
 * esta clase primero recogera la informacion pasada, que son un ID de la base de datos 
 * y la palabra ingreso o gasto, por que esto definira que es lo que hara.
 *  */

import Database.GastoIngreso;
import Database.UsersDataSource;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gastosManager.R.id;

public class Registro_NuevoGastoIngreso extends Activity
{
    private long user_id1;
    private UsersDataSource datasource;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_registro_nuevo_gasto);
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

	findViewById(R.id.buttonCancel).setOnClickListener(
		new View.OnClickListener()
		{
		    @Override
		    public void onClick(View view)
		    {
			finish();
		    }
		});

	findViewById(R.id.buttonOK).setOnClickListener(
		new View.OnClickListener()
		{

		    private int theDate;

		    @Override
		    public void onClick(View v)
		    {
			// TODO Auto-generated method stub
			GastoIngreso gasto = new GastoIngreso();
			EditText editTConcepto = (EditText) findViewById(id.editTextConcepto);
			String concepto = editTConcepto.getText().toString();
			EditText editTValor = (EditText) findViewById(id.editTextValor);
			int valor = Integer.parseInt(editTValor.getText()
				.toString());
			gasto.setConcepto(concepto);
			gasto.setIngresoGasto(gastoIngreso);
			gasto.setValor(valor);
			gasto.setId_usuario(user_id1 + 1);
			theDate = 15;
			Date sqlDate = new Date(theDate);
			gasto.setFecha(theDate);
			datasource.crearNuevoGastoIngreso(gasto);
			finish();
		    }
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.registro__nuevo_gasto, menu);
	return true;
    }

    

}