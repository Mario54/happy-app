package com.mariolamontagne.happy.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jjoe64.graphview.GraphViewDataInterface;

public class HappyEntry implements GraphViewDataInterface {

    private UUID mId;
    private Double mHappiness;
    private Date mTime;
    private List<String> mTags;

    private static final String JSON_ID = "id";
    private static final String JSON_HAPPINESS = "happiness";
    private static final String JSON_DATE = "time";
    private static final String JSON_TAGS = "tags";

    public HappyEntry(Double happiness, Date time) {
        mHappiness = happiness;
        mTime = time;
        mId = UUID.randomUUID();
    }

    public HappyEntry() {
        mId = UUID.randomUUID();
        mTime = new Date();
        mHappiness = Double.valueOf(0.);
    }

    public HappyEntry(JSONObject json) throws JSONException {
        mId = UUID.fromString(json.getString(JSON_ID));
        mTime = new Date(json.getLong(JSON_DATE));
        mHappiness = json.getDouble(JSON_HAPPINESS);
        mTags = new ArrayList<String>();

        JSONArray array = json.getJSONArray(JSON_TAGS);

        for (int i = 0; i < array.length(); i++) {
            mTags.add(array.getString(i));
        }
    }

    public Date getTime() {
        return mTime;
    }

    public Double getHappiness() {
        return mHappiness;
    }

    public void setHappiness(Double happiness) {
        mHappiness = happiness;
    }

    public UUID getId() {
        return mId;
    }

    public List<String> getTags() {
        if (mTags == null) {
            mTags = new ArrayList<String>();
        }
        return mTags;
    }

    public static class HappyEntryComparator implements Comparator<HappyEntry> {
        @Override
        public int compare(HappyEntry o1, HappyEntry o2) {
            return o2.getTime().compareTo(o1.getTime());
        }
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_ID, mId.toString());
        json.put(JSON_HAPPINESS, mHappiness);
        json.put(JSON_DATE, mTime.getTime());

        JSONArray array = new JSONArray();

        for (String tag : mTags) {
            array.put(tag);
        }

        json.put(JSON_TAGS, array);

        return json;
    }

    @Override
    public double getX() {
        return mTime.getTime();
    }

    @Override
    public double getY() {
        return mHappiness;
    }
    
    public String getTagsList() {
    	String tagString = "";
    	int size = mTags.size();
    	for (int i = 0; i < size; i++) {
    		if (i == 0) {
    			tagString += mTags.get(i);
    		} else {
    			tagString += ", " + mTags.get(i);
    		}
    	}
    	
    	return tagString.trim();
    }
}
