package com.mariolamontagne.happy.fragments;

import com.mariolamontagne.happy.R;
import com.mariolamontagne.happy.R.id;
import com.mariolamontagne.happy.R.layout;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public abstract class SingleFragmentActivity extends FragmentActivity {

	protected abstract Fragment createFragment();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment);

		FragmentManager fm = getFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);

		if (fragment == null) {
			fragment = createFragment();
			fm.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
		}
	}
}
