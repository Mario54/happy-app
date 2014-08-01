package com.mariolamontagne.happy.activities;

import java.util.Date;

import com.mariolamontagne.happy.AlarmService;
import com.mariolamontagne.happy.fragments.EntryListFragment;
import com.mariolamontagne.happy.fragments.SingleFragmentActivity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class EntryListActivity extends SingleFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("", "Service starting...");
        startService(new Intent(this, AlarmService.class));
    }

    @Override
    protected Fragment createFragment() {
        Date date = (Date) getIntent().getExtras().getSerializable(EntryListFragment.EXTRA_DAY);

        if (date == null) {
            return new EntryListFragment();
        } else {
            return EntryListFragment.newInstance(date);
        }
    }
}
