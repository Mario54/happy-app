
package com.mariolamontagne.happy.activities;

import android.app.Fragment;

import com.mariolamontagne.happy.fragments.RemindersFragment;
import com.mariolamontagne.happy.fragments.SingleFragmentActivity;

public class EditRemindersActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        // TODO Auto-generated method stub
        return new RemindersFragment();
    }
}
