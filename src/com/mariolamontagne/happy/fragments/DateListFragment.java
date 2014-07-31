package com.mariolamontagne.happy.fragments;

import java.util.ArrayList;

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

import com.mariolamontagne.happy.EditHappyEntryActivity;
import com.mariolamontagne.happy.EntryListActivity;
import com.mariolamontagne.happy.GraphActivity;
import com.mariolamontagne.happy.R;
import com.mariolamontagne.happy.Utilities.DateUtility;
import com.mariolamontagne.happy.model.Day;
import com.mariolamontagne.happy.model.HappyEntry;
import com.mariolamontagne.happy.model.HappyEntryLab;

public class DateListFragment extends ListFragment {

    private ArrayList<Day> mDays;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);

        mDays = HappyEntryLab.get(getActivity()).getDaysWithEntries();

        DateListAdapter adapter = new DateListAdapter(mDays);
        setListAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_date_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_item_new_entry) {
            HappyEntry entry = new HappyEntry();
            Intent i = new Intent(getActivity(), EditHappyEntryActivity.class);
            HappyEntryLab.get(getActivity()).addEntry(entry);
            i.putExtra(EntryFragment.EXTRA_ENTRY_ID, entry.getId());
            startActivityForResult(i, 0);
            return true;
        } else if (item.getItemId() == R.id.menu_item_see_stats) {
            Intent i = new Intent(getActivity(), GraphActivity.class);
            startActivity(i);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
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
}
