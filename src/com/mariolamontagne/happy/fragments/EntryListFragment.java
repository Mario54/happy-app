package com.mariolamontagne.happy.fragments;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import android.app.ListFragment;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.ListView;
import android.widget.TextView;

import com.mariolamontagne.happy.R;
import com.mariolamontagne.happy.R.id;
import com.mariolamontagne.happy.R.layout;
import com.mariolamontagne.happy.R.menu;
import com.mariolamontagne.happy.activities.EditHappyEntryActivity;
import com.mariolamontagne.happy.model.Day;
import com.mariolamontagne.happy.model.HappyEntry;
import com.mariolamontagne.happy.model.HappyEntryLab;
import com.mariolamontagne.happy.utilities.DateUtility;

public class EntryListFragment extends ListFragment {

    public static final String EXTRA_DAY = "com.mariolamontagne.happy.day";

    private ArrayList<HappyEntry> mEntries;
    private Day mDay;

    public static EntryListFragment newInstance(Date day) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_DAY, day);

        EntryListFragment fragment = new EntryListFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        Date date = (Date) getArguments().getSerializable(EXTRA_DAY);
        if (date != null) {
            mDay = new Day(date);
            mEntries = HappyEntryLab.get(getActivity()).getDayEntries(mDay.getDate());
        } else {
            mEntries = HappyEntryLab.get(getActivity()).getEntries();
        }

        HappyEntryAdapter adapter = new HappyEntryAdapter(mEntries);
        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_entry_list, container, false);

        ListView listView = (ListView) v.findViewById(android.R.id.list);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        listView.setMultiChoiceModeListener(new MultiChoiceModeListener() {

            @Override
            public boolean onPrepareActionMode(ActionMode arg0, Menu arg1) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.entry_list_item_context, menu);

                return true;

            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_item_delete_entry:
                        HappyEntryAdapter adapter = (HappyEntryAdapter) getListAdapter();
                        HappyEntryLab entryLab = HappyEntryLab.get(getActivity());
                        for (int i = adapter.getCount() - 1; i >= 0; i--) {
                            if (getListView().isItemChecked(i)) {
                                entryLab.deleteEntry(adapter.getItem(i));
                            }
                        }
                        mode.finish();
                        mEntries.clear();
                        mEntries.addAll(HappyEntryLab.get(getActivity()).getDayEntries(mDay.getDate()));
                        Collections.sort(mEntries, new HappyEntry.HappyEntryComparator());
                        ((HappyEntryAdapter) getListAdapter()).notifyDataSetChanged();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
            }
        });

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_happyentry_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_item_new_entry) {
            HappyEntry newEntry = new HappyEntry();
            Intent i = new Intent(getActivity(), EditHappyEntryActivity.class);
            HappyEntryLab.get(getActivity()).addEntry(newEntry);
            i.putExtra(EntryFragment.EXTRA_ENTRY_ID, newEntry.getId());
            startActivityForResult(i, 0);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mEntries.clear();
        mEntries.addAll(HappyEntryLab.get(getActivity()).getDayEntries(mDay.getDate()));
        Collections.sort(mEntries, new HappyEntry.HappyEntryComparator());
        ((HappyEntryAdapter) getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        HappyEntry entry = (HappyEntry) ((HappyEntryAdapter) getListAdapter()).getItem(position);

        Intent i = new Intent(getActivity(), EditHappyEntryActivity.class);
        i.putExtra(EntryFragment.EXTRA_ENTRY_ID, entry.getId());
        startActivity(i);
    }

    private class HappyEntryAdapter extends ArrayAdapter<HappyEntry> {
        public HappyEntryAdapter(ArrayList<HappyEntry> happyInputs) {
            super(getActivity(), 0, happyInputs);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_happyentry_item, null);
            }

            HappyEntry h = getItem(position);

            TextView titleTextView = (TextView) convertView.findViewById(R.id.happyinput_list_item_titleTextView);
            titleTextView.setText(DateUtility.getTimeFormatted(h.getTime()));

            TextView happyLevelTextView = (TextView) convertView.findViewById(R.id.happyinput_list_item_happyLevel);
            happyLevelTextView.setText(h.getHappiness().toString());
            happyLevelTextView.setBackgroundColor(getColor(h.getHappiness()));

            TextView tagsTextView = (TextView) convertView.findViewById(R.id.happyinput_list_item_tagsTextView);
            tagsTextView.setText(h.getTagsList());

            return convertView;
        }

        private int getColor(Double happinessLevel) {
            if (happinessLevel >= 7.0) {
                return Color.GREEN;
            } else if (happinessLevel >= 4.0) {
                return Color.YELLOW;
            } else {
                return Color.RED;
            }
        }
    }
}
