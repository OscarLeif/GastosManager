package com.example.gastosManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.example.gastosManager.R;

public class CollectionDemoActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_collection_demo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.collection_demo, menu);
	return true;
    }

}
