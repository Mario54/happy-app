    package com.mariolamontagne.happy.fragments;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

import com.mariolamontagne.happy.R;

public class TimePickerFragment extends DialogFragment {
    
    public static final String EXTRA_HOUR = "happy.hour";
    public static final String EXTRA_MIN = "happy.minute";
    
    private int mHour;
    private int mMinute;
    
    public static TimePickerFragment newInstance(int hour, int minute) {
        Bundle args = new Bundle();
        args.putInt(EXTRA_HOUR, hour);
        args.putInt(EXTRA_MIN, minute);
        
        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        
        return fragment;
    }
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_time, null);
        
        TimePicker timePicker = (TimePicker) v.findViewById(R.id.dialog_time_timePicker);
        timePicker.setOnTimeChangedListener(new OnTimeChangedListener() {
            
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                mHour = hourOfDay;
                mMinute = minute;

                getArguments().putInt(EXTRA_HOUR, mHour);
                getArguments().putInt(EXTRA_MIN, mMinute);
            }
        });
        timePicker.setCurrentHour(mHour);
        timePicker.setCurrentMinute(mMinute);
        //return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
        
        return new AlertDialog.Builder(getActivity())
        .setView(v)
        .setTitle(R.string.time_picker_title)
        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                sendResult(Activity.RESULT_OK);
            }


        })
        .create();
    }
    
    private void sendResult(int resultCode) {
        if (getTargetFragment() == null) return;
        
        Intent i = new Intent();
        i.putExtra(EXTRA_HOUR, mHour);
        i.putExtra(EXTRA_MIN, mMinute);

        getTargetFragment()
            .onActivityResult(getTargetRequestCode(), resultCode, i);
        
    }
}
