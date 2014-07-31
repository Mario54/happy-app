package com.mariolamontagne.happy;

import android.app.Fragment;

import com.mariolamontagne.happy.fragments.SingleFragmentActivity;
import com.mariolamontagne.happy.fragments.StatisticsFragment;

public class GraphActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new StatisticsFragment();
    }
}
