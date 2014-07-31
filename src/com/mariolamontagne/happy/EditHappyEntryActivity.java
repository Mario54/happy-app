package com.mariolamontagne.happy;

import java.util.UUID;

import com.mariolamontagne.happy.fragments.EntryFragment;
import com.mariolamontagne.happy.fragments.SingleFragmentActivity;
import com.mariolamontagne.happy.model.HappyEntry;
import com.mariolamontagne.happy.model.HappyEntryLab;


import android.app.Fragment;

public class EditHappyEntryActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		UUID entryId = (UUID) getIntent().getSerializableExtra(EntryFragment.EXTRA_ENTRY_ID);

		if (entryId == null) {
			HappyEntry entry = new HappyEntry();
			HappyEntryLab.get(this).addEntry(entry);
			return EntryFragment.newInstance(entry.getId());
		} else {
			return EntryFragment.newInstance(entryId);
		}

	}
}
