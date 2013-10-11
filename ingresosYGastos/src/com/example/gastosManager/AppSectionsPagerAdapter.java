package com.example.gastosManager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * En esta clase es donde se asigna el contenido de las Tabs,
 * 
 * @author HammerH11
 * 
 */
public class AppSectionsPagerAdapter extends FragmentPagerAdapter {

	public AppSectionsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	/**
	 * Dentro de este metodo estan las asignaciones de cada actividad. Esto
	 * numero i, depende del numero de tabs creadas anteriormente. Si creamos 3
	 * tabs son 0,1,2 casos. el default case remplazara esos casos que no
	 * existieron, si solo use un case 1, case 2 y 3 seran default. Recordar que
	 * cada retorno del case es una clase Fragment.
	 */
	@Override
	public Fragment getItem(int i) {
		switch (i) {
		case 0:
			// The first section of the app is the most interesting -- it offers
			// a launchpad into the other demonstrations in this example
			// application.
			// Al retornar este fragmento creara el contenido para ubicarlo
			// dentro
			// de la tab.
			return new GastosSectionFragment();// Esta clase hereda de Fragment.

		default:
			// The other sections of the app are dummy placeholders.
			DummySectionFragment fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, i + 1);
			fragment.setArguments(args);
			return fragment;
		}
	}

	@Override
	public int getCount() {
		return 3;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return "Section " + (position + 1);
	}
}
