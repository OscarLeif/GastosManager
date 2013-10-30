package com.example.gastosManager;

import java.util.ArrayList;

import com.gastosManager.Nuevo_usuario_Activity;

import aChart.Core.AverageTemperatureChart;
import aChart.Core.IDemoChart;
import aChart.Core.XY_PlotActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MiscelaneaSectionFragment extends Fragment
{

    
    public static final String ARG_SECTION_NUMBER = "section_number";
    private ListView listaMiscelanea;
    private long user_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState)
    {
	View rootView = inflater.inflate(R.layout.fragment_section_miscelanea,
		container, false);
	ArrayList<String> opciones = new ArrayList<String>();
	opciones.add("Informe de ingresos");
	opciones.add("Informe de gastos");
	opciones.add("Informe de movimientos");
	
	
	ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, opciones);
	listaMiscelanea = (ListView)rootView.findViewById(R.id.lista_miscelanea);
	listaMiscelanea.setAdapter(adapter);
	//Creamos el primer listener de esta clase Fragment
	//Con esto crearemos el primer reporte de ingresos y gastos, con movimientos.
	listaMiscelanea.setOnItemClickListener(new OnItemClickListener()
	{

	    @Override
	    public void onItemClick(AdapterView<?> arg0, View arg1, int position,
		    long arg3)
	    {
		// TODO Auto-generated method stub
		if(position == 0 )
		{
		 Log.d("Metodo que hace reporte: ingreso = Id= ",Integer.toString((int) user_id) );
		 //IDemoChart p= new AverageTemperatureChart(user_id);
		 Intent i = new Intent(getActivity(), XY_PlotActivity.class);
		 i.putExtra("Key", user_id);
		 //getActivity().startActivity(i);
		 startActivity(i);
		 
		}
		if(position == 1)
		{
		    Log.d("Metodo que hace reporte: ","gastos");   
		}
		if(position == 2)
		{
		    Log.d("Metodo que hace reporte: ","movimientos");
		}
		

	    }
	});

	return rootView;
    }
    
    /**
     * Metodo de que existe en todas las clases Fragment.
     * Recibe el parametro para poder editar, ver toda la informacion 
     * del ID que esta en la base de datos
     * @param userKey
     * @return
     */

    public long darUserID(long userKey)
    {
	// TODO Auto-generated method stub
	return user_id = userKey;
    }
    
    public void generarReporteIngresos()
    {
	
    }
    public void generarReporteGastos()
    {
	
    }
}