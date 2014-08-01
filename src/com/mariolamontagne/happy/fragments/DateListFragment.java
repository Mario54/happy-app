package com.mariolamontagne.happy.fragments;

import java.util.ArrayList;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mariolamontagne.happy.R;
import com.mariolamontagne.happy.activities.EditHappyEntryActivity;
import com.mariolamontagne.happy.activities.EntryListActivity;
import com.mariolamontagne.happy.activities.GraphActivity;
import com.mariolamontagne.happy.model.Day;
import com.mariolamontagne.happy.model.HappyEntry;
import com.mariolamontagne.happy.model.HappyEntryLab;
import com.mariolamontagne.happy.utilities.DateUtility;

public class DateListFragment extends ListFragment {
	
	public static final String EXTRA_MONTH = "com.mariolamontagne.happy.month";
	public static final String EXTRA_YEAR = "com.mariolamontagne.happy.year";


    private ArrayList<Day> mDays;
    private int mMonth;
    private int mYear;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        
        mYear = getArguments().getInt(EXTRA_YEAR);
        mMonth = getArguments().getInt(EXTRA_MONTH);

        mDays = HappyEntryLab.get(getActivity()).getDaysFromMonth(mYear, mMonth);

        DateListAdapter adapter = new DateListAdapter(mDays);
        setListAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((DateListAdapter) getListAdapter()).notifyDataSetChanged();
    }
    
    public void changeMonth(int year, int month) {
    	mDays.clear();
    	mDays.addAll(HappyEntryLab.get(getActivity()).getDaysFromMonth(year, month));
    	((DateListAdapter) getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Day day = ((DateListAdapter) getListAdapter()).getItem(position);

        Intent intent = new Intent(getActivity(), EntryListActivity.class);
        intent.putExtra(EntryListFragment.EXTRA_DAY, day.getDate());
        startActivity(intent);
    }

    private class DateListAdapter extends ArrayAdapter<Day> {
        public DateListAdapter(ArrayList<Day> dates) {
            super(getActivity(), 0, dates);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_day_item, null);
            }

            Day day = getItem(position);

            TextView titleTextView = (TextView) convertView.findViewById(R.id.day_list_item_titleTextView);
            titleTextView.setText(DateUtility.getDateFormatted(day.getDate()));

            TextView numEntriesTextView = (TextView) convertView.findViewById(R.id.day_list_item_numEntriesTextView);
            numEntriesTextView.setText(HappyEntryLab.get(getContext()).getDayEntries(day.getDate()).size() + " "
                    + getResources().getString(R.string.entries));
            HappyEntryLab.get(getContext()).getDayEntries(day.getDate()).size();

            TextView averageTextView = (TextView) convertView.findViewById(R.id.day_list_item_happyLevel);
            averageTextView.setText(String.format("%.1f", HappyEntryLab.get(getContext()).getAverageScoreForDay(day)));

            return convertView;
        }
    }

	public static Fragment newInstance(int year, int month) {
		DateListFragment fragment = new DateListFragment();
		Bundle args = new Bundle();
		args.putInt(EXTRA_MONTH, month);
		args.putInt(EXTRA_YEAR, year);
		
		fragment.setArguments(args);
		return fragment;
	}
}
