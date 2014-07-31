package com.mariolamontagne.happy.activities;

import com.mariolamontagne.happy.R;
import com.mariolamontagne.happy.R.id;
import com.mariolamontagne.happy.R.layout;
import com.mariolamontagne.happy.fragments.EntryListFragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class CalendarActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar);

		FragmentManager fm = getFragmentManager();
		Fragment calendarFragment = fm.findFragmentById(R.id.dateListFragmentContainer);
		Fragment entryListFragment = fm.findFragmentById(R.id.entryListFragmentContainer);

		if (calendarFragment == null || entryListFragment == null) {
			// calendarFragment = new DateListFragment();
			entryListFragment = new EntryListFragment();
			fm.beginTransaction().add(R.id.entryListFragmentContainer, entryListFragment).commit();
		}
	}

}
