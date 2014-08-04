package com.mariolamontagne.happy.activities;

import java.util.Calendar;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mariolamontagne.happy.R;
import com.mariolamontagne.happy.fragments.DateListFragment;
import com.mariolamontagne.happy.fragments.EntryFragment;
import com.mariolamontagne.happy.model.HappyEntry;
import com.mariolamontagne.happy.model.HappyEntryLab;
import com.mariolamontagne.happy.utilities.DateUtility;

public class DayListActivity extends FragmentActivity {
	
	private Button mPrevMonthButton;
	private Button mNextMonthButton;
	private TextView mMonthTextView;
	
	private int curMonth;
	private int curYear;
	
	private Fragment mFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_day_list);
		
		curMonth = Calendar.getInstance().get(Calendar.MONTH);
		curYear = Calendar.getInstance().get(Calendar.YEAR);
		
		mMonthTextView = (TextView) findViewById(R.id.monthTextView);
		
		mPrevMonthButton = (Button) findViewById(R.id.previousMonthButton);
		mPrevMonthButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				changeMonth(-1);
			}
		});
		
		mNextMonthButton = (Button) findViewById(R.id.nextMonthButton);
		mNextMonthButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				changeMonth(1);
			}
		});
		
		FragmentManager fm = getFragmentManager();
		mFragment = fm.findFragmentById(R.id.listContainer);
		
		if (mFragment == null) {
		    mFragment = DateListFragment.newInstance(curYear, curMonth);
			fm.beginTransaction().add(R.id.listContainer, mFragment).commit();
		}
		
		getActionBar().show();
		resetButtons();
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_day_list, menu);
        
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_item_new_entry) {
            HappyEntry entry = new HappyEntry();
            Intent i = new Intent(this, EditHappyEntryActivity.class);
            HappyEntryLab.get(this).addEntry(entry);
            i.putExtra(EntryFragment.EXTRA_ENTRY_ID, entry.getId());
            startActivityForResult(i, 0);
            return true;
        } else if (item.getItemId() == R.id.menu_item_reminders) {
            Intent i = new Intent(this, EditRemindersActivity.class);
            startActivity(i);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        ((DateListFragment) mFragment).updateList();
    }
    
    private void changeMonth(int increment) {
		DateListFragment fragment = (DateListFragment) DayListActivity.this.getFragmentManager().findFragmentById(R.id.listContainer);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, curYear);
		cal.set(Calendar.MONTH, curMonth);
		cal.add(Calendar.MONTH, increment);
		
		curMonth = cal.get(Calendar.MONTH);
		curYear = cal.get(Calendar.YEAR);
		fragment.changeMonth(curYear, curMonth);
		
		mMonthTextView.setText(DateUtility.getMonthFormatted(cal.getTime()));
		resetButtons();
    }
    
    private void resetButtons() {
        if (HappyEntryLab.get(this).isEntry(true, curMonth, curYear)) {
            mNextMonthButton.setEnabled(true);
        } else {
            mNextMonthButton.setEnabled(false);
        }
        
        if (HappyEntryLab.get(this).isEntry(false, curMonth, curYear)) {
            mPrevMonthButton.setEnabled(true);
        } else {
            mPrevMonthButton.setEnabled(false);
        }
    }
}
