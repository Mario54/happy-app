package com.mariolamontagne.happy.fragments;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import org.json.JSONStringer;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.mariolamontagne.happy.AlarmService;
import com.mariolamontagne.happy.R;
import com.mariolamontagne.happy.model.Reminder;
import com.mariolamontagne.happy.model.RemindersJSONSerializer;
import com.mariolamontagne.happy.utilities.AlarmUtility;
import com.mariolamontagne.happy.utilities.DateUtility;

public class RemindersFragment extends Fragment {
    
    protected static final int REQUEST_TIME = 0;
    protected static final String DIALOG_TIME = "time";
    private ListView mRemindersList;
    private Button mAddReminderButton;
    
    private ArrayList<Reminder> mReminders;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reminders, container, false);

        mReminders = AlarmUtility.get(getActivity()).getReminders();
        
        mRemindersList = (ListView) view.findViewById(R.id.reminders_listView);
        mRemindersList.setAdapter(new RemindersAdapter(mReminders));
        mRemindersList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        mRemindersList.setMultiChoiceModeListener(new MultiChoiceModeListener() {
            
            @Override
            public boolean onPrepareActionMode(ActionMode arg0, Menu arg1) {
                return false;
            }
            
            @Override
            public void onDestroyActionMode(ActionMode arg0) { }
            
            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                MenuInflater inflater = actionMode.getMenuInflater();
                inflater.inflate(R.menu.reminders_context, menu);
                
                return true;
            }
            
            @Override
            public boolean onActionItemClicked(ActionMode am, MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_item_delete_reminders:
                        RemindersAdapter adapter = (RemindersAdapter) mRemindersList.getAdapter();
                        for (int i = adapter.getCount() - 1; i >= 0; i--) {
                            if (mRemindersList.isItemChecked(i)) {
                                mReminders.remove(adapter.getItem(i));
                            }
                        }
                        am.finish();
                        adapter.notifyDataSetChanged();
                        return true;
                    default:
                        return false;
                }
            }
            
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) { }
        });
        
        mAddReminderButton = (Button) view.findViewById(R.id.reminders_addButton);
        mAddReminderButton.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                FragmentManager fm = getFragmentManager();
                TimePickerFragment timePicker = TimePickerFragment.newInstance(c.get(Calendar.HOUR), c.get(Calendar.MINUTE));
                timePicker.setTargetFragment(RemindersFragment.this, REQUEST_TIME);
                timePicker.show(fm, DIALOG_TIME);
            }
        });
        
        return view;
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == REQUEST_TIME) {
            int hour = data.getIntExtra(TimePickerFragment.EXTRA_HOUR, 0);
            int minute = data.getIntExtra(TimePickerFragment.EXTRA_MIN, 0);
            
            mReminders.add(new Reminder(hour, minute));
            Collections.sort(mReminders);
            ((ArrayAdapter<Reminder>) mRemindersList.getAdapter()).notifyDataSetChanged();
        }
    }
    
    @Override
    public void onPause() {
        super.onPause();
        AlarmUtility.get(getActivity()).saveReminders();
    }
    
    private class RemindersAdapter extends ArrayAdapter<Reminder> {
        public RemindersAdapter(ArrayList<Reminder> reminders) {
            super(getActivity(), 0, reminders);
        }
        
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_reminder_item, null);
            }
            
            TextView timeTextView = (TextView) convertView.findViewById(R.id.reminder_item_timeTextView);
            timeTextView.setText(DateUtility.getTimeFormatted(getItem(position).getHour(), getItem(position).getMinute()));
            
            return convertView;
        }
    }

}
