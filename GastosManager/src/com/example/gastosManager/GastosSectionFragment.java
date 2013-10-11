package com.example.gastosManager;

import java.util.List;

import Database.UsersDataSource;
import Database.Usuario;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.sax.RootElement;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.gastosManager.R;
import com.gastosManager.MainActivity;
import com.gastosManager.logica.GastoIngreso;

public class GastosSectionFragment extends Fragment {
	
	private long user_id;
	private UsersDataSource datasource;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_section_gastos,
				container, false);
		darUserID(user_id);
		
		datasource = new UsersDataSource(getActivity());
		datasource.open();
		
		ListView lista = (ListView) rootView.findViewById(R.id.listaGastos);
		// use the SimpleCursorAdapter to show the
		// elements in a ListView
	    List<GastoIngreso> values = datasource.darTodosLosGastoIngreso();
		ArrayAdapter<GastoIngreso> adapter = new ArrayAdapter<GastoIngreso>(getActivity(),android.R.layout.simple_list_item_1, values);
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
	




/**
 * Definitivamente este metodo no funciona en los fragements activities.
 * @param v
 */
	public void btnNuevoGasto(View v) {
		System.out.println("El boton nuevo Gasto funciona");

	}

	public void darUserID(long userKey) {
		// TODO Auto-generated method stub
		user_id = userKey;
	}
	
	public void llenarLista()
	{
		
		
	}

}
