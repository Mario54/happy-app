package com.mariolamontagne.happy.fragments;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.app.Fragment;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.mariolamontagne.happy.R;
import com.mariolamontagne.happy.adapters.TagsAdapter;
import com.mariolamontagne.happy.model.HappyEntry;
import com.mariolamontagne.happy.model.HappyEntryLab;
import com.mariolamontagne.happy.utilities.DateUtility;

public class EntryFragment extends Fragment {

    public static final String EXTRA_ENTRY_ID = "com.mariolamontagne.happy.extra_entry_id";

    private HappyEntry mEntry;

    private AutoCompleteTextView mTagsAutoTextView;
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
        setHasOptionsMenu(true);

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
        timeTextView.setText(DateUtility.getDateFormatted(mEntry.getTime()) + " at " + 
        													DateUtility.getTimeFormatted(mEntry.getTime()));
        
        final TextView happyLevelTextView = (TextView) view.findViewById(R.id.happyentry_levelTextView);
        happyLevelTextView.setText(String.valueOf(mEntry.getHappiness()));

        SeekBar happyLevelSeek = (SeekBar) view.findViewById(R.id.happyentry_LevelSeek);
        happyLevelSeek.setProgress(mEntry.getHappiness().intValue() * 10);
        happyLevelSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekbar) {
                mEntry.setHappiness(seekbar.getProgress() / 10.);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekbar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekbar, int progress, boolean fromUser) {
            	happyLevelTextView.setText(String.valueOf(seekbar.getProgress() / 10.));
            }
        });

        mTagsAutoTextView = (AutoCompleteTextView) view.findViewById(R.id.happyentry_tagsEditText);
        ArrayList<String> tagsSuggestions = HappyEntryLab.get(getActivity()).retrieveAllTags();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, tagsSuggestions);
        mTagsAutoTextView.setAdapter(adapter);
        mTagsAutoTextView.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				boolean handled = false;
				if (actionId == EditorInfo.IME_ACTION_SEND) {
					addTag();
					handled = true;
				}
				
				return handled;
			}

		});

        mTagsListView = (ListView) view.findViewById(R.id.happyentry_tagsListView);
        List<String> tagsArray = mEntry.getTags();
        TagsAdapter tagsAdapter = new TagsAdapter(getActivity(), tagsArray);
        mTagsListView.setAdapter(tagsAdapter);
        mTagsListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        mTagsListView.setMultiChoiceModeListener(new MultiChoiceModeListener() {
            
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
            public boolean onCreateActionMode(ActionMode am, Menu menu) {
                MenuInflater inflater = am.getMenuInflater();
                inflater.inflate(R.menu.tags_list_item_context, menu);
                
                return true;
            }
            
            @Override
            public boolean onActionItemClicked(ActionMode am, MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.menu_item_delete_tag) {
                    TagsAdapter adapter = (TagsAdapter) mTagsListView.getAdapter();
                    for (int i = adapter.getCount() - 1; i >= 0; i--) {
                        if (mTagsListView.isItemChecked(i)) {
                            mEntry.getTags().remove(adapter.getItem(i));
                        }
                    }
                    am.finish();
                    adapter.notifyDataSetChanged();
                    return true;
                } else {
                    return false;
                }
            }
            
            @Override
            public void onItemCheckedStateChanged(ActionMode arg0, int arg1, long arg2, boolean arg3) {
                // TODO Auto-generated method stub
                
            }
        });

        Button addTagButton = (Button) view.findViewById(R.id.happyentry_addTagButton);
        addTagButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                addTag();
            }
        });

        return view;
    }
    
	@SuppressWarnings("unchecked")
    private void addTag() {
		String tag = mTagsAutoTextView.getText().toString().trim();
        if (!tag.isEmpty() && !mEntry.getTags().contains(tag)) {
            mTagsAutoTextView.setText("");
            mEntry.getTags().add(tag);
        }

        ((ArrayAdapter<String>) mTagsListView.getAdapter()).notifyDataSetChanged();
	}
}
