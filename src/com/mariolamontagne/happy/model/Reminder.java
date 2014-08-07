package com.mariolamontagne.happy.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Reminder implements Comparable<Reminder> {
    
    private static final String JSON_HOUR_OF_DAY = "hour";
    private static final String JSON_MINUTE = "minute";
    private int mHour;
    private int mMinute;
    
    public Reminder(int hour, int minute) {
        mHour = hour;
        mMinute = minute;
    }

    public Reminder(JSONObject jsonObject) throws JSONException {
        mHour = jsonObject.getInt(JSON_HOUR_OF_DAY);
        mMinute = jsonObject.getInt(JSON_MINUTE);
    }

    public int getHour() {
        return mHour;
    }

    public void setHour(int hour) {
        mHour = hour;
    }

    public int getMinute() {
        return mMinute;
    }

    public void setMinute(int minute) {
        mMinute = minute;
    }

    @Override
    public int compareTo(Reminder another) {
        if (another.getHour() > getHour()) {
            return -1;
        } else if (another.getHour() == getHour()) {
            if (another.getMinute() > getMinute()) {
                return -1;
            } else if (another.getMinute() == getMinute()) {
                return 0;
            } else {
                return 1;
            }
        } else {
            return 1;
        }
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject object = new JSONObject();
        object.put(JSON_HOUR_OF_DAY, getHour());
        object.put(JSON_MINUTE, getMinute());
        
        return object;
    }

}
