package com.mariolamontagne.happy.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mariolamontagne.happy.R;

public class TagsAdapter extends ArrayAdapter<String> {

	public TagsAdapter(Context context, List<String> tags) {
		super(context, 0, tags);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.list_tag_item, null);
		}
		
		String tag = getItem(position);
		
		TextView titleTextView = (TextView) convertView.findViewById(R.id.tag_item_textView);
		titleTextView.setText(tag);
		
		return convertView;
	}

}
