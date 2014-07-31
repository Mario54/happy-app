package com.mariolamontagne.happy.activities;

import com.mariolamontagne.happy.fragments.DateListFragment;
import com.mariolamontagne.happy.fragments.SingleFragmentActivity;

import android.app.Fragment;

public class DayListActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		// TODO Auto-generated method stub
		return new DateListFragment();
	}

}
