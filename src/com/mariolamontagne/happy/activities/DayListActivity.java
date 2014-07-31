package com.mariolamontagne.happy.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.mariolamontagne.happy.R;
import com.mariolamontagne.happy.fragments.DateListFragment;

public class DayListActivity extends ActionBarActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_day_list);
		
		FragmentManager fm = getFragmentManager();
		
		Fragment fragment = fm.findFragmentById(R.id.listContainer);
		
		if (fragment == null) {
			fragment = new DateListFragment();
			fm.beginTransaction().add(R.id.listContainer, fragment).commit();
		}
	}

}
