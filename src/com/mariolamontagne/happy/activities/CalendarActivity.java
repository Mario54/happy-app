package com.mariolamontagne.happy.activities;

import java.util.Calendar;
import java.util.Date;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.mariolamontagne.happy.R;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

public class CalendarActivity extends FragmentActivity {

	private boolean undo = false;
	private CaldroidFragment caldroidFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar);


		// Setup caldroid fragment
		// **** If you want normal CaldroidFragment, use below line ****
		caldroidFragment = new CaldroidFragment();

		// //////////////////////////////////////////////////////////////////////
		// **** This is to show customized fragment. If you want customized
		// version, uncomment below line ****
//		 caldroidFragment = new CaldroidSampleCustomFragment();

		// Setup arguments

		// If Activity is created after rotation
		if (savedInstanceState != null) {
			caldroidFragment.restoreStatesFromKey(savedInstanceState,
					"CALDROID_SAVED_STATE");
		}
		// If activity is created from fresh
		else {
			Bundle args = new Bundle();
			Calendar cal = Calendar.getInstance();
			args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
			args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
			args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
			args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, false);

			// Uncomment this to customize startDayOfWeek
			// args.putInt(CaldroidFragment.START_DAY_OF_WEEK,
			// CaldroidFragment.TUESDAY); // Tuesday
			caldroidFragment.setArguments(args);
		}

		// Attach to the activity
		FragmentTransaction t = getSupportFragmentManager().beginTransaction();
		t.replace(R.id.calendar1, caldroidFragment);
		t.commit();

		// Setup listener
		final CaldroidListener listener = new CaldroidListener() {

			@Override
			public void onSelectDate(Date date, View view) {
				view.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
			}

			@Override
			public void onChangeMonth(int month, int year) {
			}

			@Override
			public void onLongClickDate(Date date, View view) {
				// Do nothing
			}

			@Override
			public void onCaldroidViewCreated() {
				// Do nothing
			}
		};

		// Setup Caldroid
		caldroidFragment.setCaldroidListener(listener);
	}

	/**
	 * Save current states of the Caldroid here
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);

		if (caldroidFragment != null) {
			caldroidFragment.saveStatesToKey(outState, "CALDROID_SAVED_STATE");
		}
	}


}
