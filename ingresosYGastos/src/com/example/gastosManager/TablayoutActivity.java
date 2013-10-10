package com.example.gastosManager;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;

import com.example.ingresosygastos.R;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class TablayoutActivity extends FragmentActivity implements
		ActionBar.TabListener {
	private int position;
	ViewPager mViewPager;
	AppSectionsPagerAdapter mAppSectionsPagerAdapter;

	@SuppressLint("NewApi")
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tablayout);
		// Create the adapter that will return a fragment for each of the three
		// primary sections
		// of the app.
		mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(
				getSupportFragmentManager());
		Bundle extras = getIntent().getExtras();
		String stringPos = extras.getString("Position");
		position = Integer.parseInt(stringPos);

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();

		// Specify that the Home/Up button should not be enabled, since there is
		// no hierarchical
		// parent.
		actionBar.setHomeButtonEnabled(false);

		// Specify that we will be displaying tabs in the action bar.
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		// Set up the ViewPager, attaching the adapter and setting up a listener
		// for when the
		// user swipes between sections.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mAppSectionsPagerAdapter);
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						// When swiping between different app sections, select
						// the corresponding tab.
						// We can also use ActionBar.Tab#select() to do this if
						// we have a reference to the
						// Tab.
						actionBar.setSelectedNavigationItem(position);
					}
				});
		CharSequence opciones = "Opciones";
		CharSequence informes = "Informes";
		CharSequence Miscelanea = "Miscelanea";
		char a = 'a';
		actionBar.addTab(actionBar.newTab().setText(opciones)
				.setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText(informes)
				.setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText(Miscelanea)
				.setTabListener(this));
	}

	/**
	 * // For each of the sections in the app, add a tab to the action bar. for
	 * (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) { // Create a
	 * tab with text corresponding to the page title defined by the adapter. //
	 * Also specify this Activity object, which implements the TabListener
	 * interface, as the // listener for when this tab is selected.
	 * actionBar.addTab( actionBar.newTab()
	 * .setText(mAppSectionsPagerAdapter.getPageTitle(i))//Aqui es donde se
	 * coloca el nombre a cada Tab. .setTabListener((TabListener) this));
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tablayout, menu);
		return true;
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

}