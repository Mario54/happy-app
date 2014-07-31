package com.mariolamontagne.happy.fragments;

import java.util.List;
import java.util.UUID;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.mariolamontagne.happy.R;
import com.mariolamontagne.happy.R.id;
import com.mariolamontagne.happy.R.layout;
import com.mariolamontagne.happy.model.HappyEntry;
import com.mariolamontagne.happy.model.HappyEntryLab;

public class EntryFragment extends Fragment {

    public static final String EXTRA_ENTRY_ID = "com.mariolamontagne.happy.extra_entry_id";

    private HappyEntry mEntry;

    private EditText mTagsEditText;
    private ListView mTagsListView;

    public static EntryFragment newInstance(UUID id) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_ENTRY_ID, id);

        EntryFragment fragment = new EntryFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mEntry = HappyEntryLab.get(getActivity()).getEntry((UUID) getArguments().getSerializable(EXTRA_ENTRY_ID));
    }

    @Override
    public void onPause() {
        super.onPause();
        HappyEntryLab.get(getActivity()).saveEntries();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edithappyentry, container, false);

        TextView timeTextView = (TextView) view.findViewById(R.id.happyentry_timeTextView);
        timeTextView.setText(mEntry.getTime().toString());

        SeekBar happyLevelSeek = (SeekBar) view.findViewById(R.id.happyentry_LevelSeek);
        happyLevelSeek.setProgress(mEntry.getHappiness().intValue() * 10);
        happyLevelSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekbar) {
                mEntry.setHappiness(seekbar.getProgress() / 10.);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekbar) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onProgressChanged(SeekBar seekbar, int progress, boolean fromUser) {
                // TODO Auto-generated method stub
            }
        });

        mTagsEditText = (EditText) view.findViewById(R.id.happyentry_tagsEditText);

        mTagsListView = (ListView) view.findViewById(R.id.happyentry_tagsListView);
        List<String> tagsArray = mEntry.getTags();
        ArrayAdapter<String> tagsAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,
                android.R.id.text1, tagsArray);
        mTagsListView.setAdapter(tagsAdapter);

        Button addTagButton = (Button) view.findViewById(R.id.happyentry_addTagButton);
        addTagButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String tag = mTagsEditText.getText().toString();
                if (!tag.isEmpty() && !mEntry.getTags().contains(tag)) {
                    mTagsEditText.setText("");
                    mEntry.getTags().add(tag);
                }

                ((ArrayAdapter<String>) mTagsListView.getAdapter()).notifyDataSetChanged();
            }
        });

        return view;
    }
}
