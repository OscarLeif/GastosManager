package com.example.gastosManager;

import java.util.ArrayList;
import java.util.List;

import Database.UsersDataSource;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.gastosManager.logica.GastoIngreso;

public class GastosSectionFragment extends Fragment {
	
	private long user_id;
	private UsersDataSource datasource;
	private ListView lista ;
	private static long idStatico;
	private boolean horizontal ;
	private Bundle savedInstanceState;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_section_gastos,
				container, false);
		
		if(savedInstanceState != null)
		{
		this.savedInstanceState = savedInstanceState;
		savedInstanceState.putInt("userKey",(int) idStatico);
		idStatico = savedInstanceState.getInt("userKey");
		}
		if(savedInstanceState == null)
		{
		idStatico = darUserID(user_id);
		}
		
		horizontal = true;
		
		lista = (ListView) rootView.findViewById(R.id.listaGastos);
		datasource = new UsersDataSource(getActivity());
		datasource.open();
		
		
		// use the SimpleCursorAdapter to show the
		// elements in a ListView
	    List<GastoIngreso> values = datasource.darTodosLosGastoIngreso();
	    ArrayList<GastoIngreso> arregloG = new ArrayList<GastoIngreso>();

	    arregloG = sacarElementosDeUsuario(values);
		ArrayAdapter<GastoIngreso> adapter = new ArrayAdapter<GastoIngreso>(getActivity(),android.R.layout.simple_list_item_1, arregloG);
		lista.setAdapter(adapter);

		// Demonstration of a collection-browsing activity.
		rootView.findViewById(R.id.botonNuevoIngreso).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						Intent intent = new Intent(getActivity(),Registro_NuevoGasto.class);
						intent.putExtra("key", user_id);
						startActivity(intent);
						System.out.println("el boton nuevo ingreso funciona correctamente");
					}
				});

		return rootView;
	}
	
	
      @Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}


	@Override
    public void onResume() 
    {
	// TODO Auto-generated method stub
	super.onResume();
	datasource = new UsersDataSource(getActivity());
	datasource.open();
	
	
	// use the SimpleCursorAdapter to show the
	// elements in a ListView
	List<GastoIngreso> values = datasource.darTodosLosGastoIngreso();
    ArrayList<GastoIngreso> arregloG = new ArrayList<GastoIngreso>();

    arregloG = sacarElementosDeUsuario(values);
	ArrayAdapter<GastoIngreso> adapter = new ArrayAdapter<GastoIngreso>(getActivity(),android.R.layout.simple_list_item_1, arregloG);
	lista.setAdapter(adapter);
    }
      
      

/**
 * Definitivamente este metodo no funciona en los fragements activities.
 * @param v
 */
	public void btnNuevoGasto(View v) {
		System.out.println("El boton nuevo Gasto funciona");

	}

	public long darUserID(long userKey) {
		// TODO Auto-generated method stub
		return user_id = userKey;
	}
	
	
	public ArrayList<GastoIngreso> sacarElementosDeUsuario(List<GastoIngreso> lista)
	{
		ArrayList<GastoIngreso> arregloGastoIngreso = new ArrayList<GastoIngreso>();
		for(int i=0;i<lista.size();i++)
		{
			if(lista.get(i).getId_usuario() == idStatico+1)
			{
				arregloGastoIngreso.add(lista.get(i));
			}
		}
		return arregloGastoIngreso;
		
	}

}
