
package com.mariolamontagne.happy.activities;

import android.app.Fragment;
import android.content.Intent;

import com.mariolamontagne.happy.AlarmService;
import com.mariolamontagne.happy.fragments.RemindersFragment;
import com.mariolamontagne.happy.fragments.SingleFragmentActivity;

public class EditRemindersActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        // TODO Auto-generated method stub
        return new RemindersFragment();
    }
    
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        startService(new Intent(this, AlarmService.class));
    }
    
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        startService(new Intent(this, AlarmService.class));
    }
}
