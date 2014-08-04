package com.mariolamontagne.happy.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

import android.content.Context;
import android.util.Log;

public class HappyEntryLab {

    private static final String TAG = "HappyEntryLab";
    private static final String FILENAME = "happyentries.json";

    private ArrayList<HappyEntry> mEntries;

    private static HappyEntryLab sHappyInputLab;

    private Context mAppContext;
    private HappyJSONSerializer mSerializer;

    public ArrayList<HappyEntry> getEntries() {
        return mEntries;
    }

    public ArrayList<HappyEntry> getDayEntries(Date date) {
        ArrayList<HappyEntry> dayEntries = new ArrayList<HappyEntry>();
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date);
        for (HappyEntry entry : mEntries) {
            cal2.setTime(entry.getTime());
            if (cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)) {
                dayEntries.add(entry);
            }
        }

        return dayEntries;
    }

    public ArrayList<Day> getDaysWithEntries() {
        ArrayList<Day> days = new ArrayList<Day>();

        for (HappyEntry entry : mEntries) {
            Day day = new Day(entry.getTime());
            if (!days.contains(day)) {
                days.add(day);
            }
        }

        return days;
    }

    public Double getAverageScoreForDay(Day day) {
        Double total = Double.valueOf(0);
        int numOfEntries = 0;

        for (HappyEntry entry : mEntries) {
            Day entryDay = new Day(entry.getTime());
            if (entryDay.equals(day)) {
                total += entry.getHappiness();
                numOfEntries++;
            }
        }

        return (numOfEntries == 0) ? 0 : total / numOfEntries;
    }

    private HappyEntryLab(Context appContext) {
        mAppContext = appContext;
        mSerializer = new HappyJSONSerializer(mAppContext, FILENAME);

        try {
            mEntries = mSerializer.loadEntries();
        } catch (Exception e) {
            mEntries = new ArrayList<HappyEntry>();
            Log.e(TAG, "Error loading entrie: ", e);
        }
    }

    public static HappyEntryLab get(Context c) {
        if (sHappyInputLab == null) {
            sHappyInputLab = new HappyEntryLab(c);
        }

        return sHappyInputLab;
    }

    public HappyEntry getEntry(UUID id) {
        for (HappyEntry entry : mEntries) {
            if (entry.getId().equals(id)) {
                return entry;
            }
        }

        return null;
    }

    public void addEntry(HappyEntry entry) {
        mEntries.add(entry);
    }

    public boolean saveEntries() {
        try {
            mSerializer.saveEntries(mEntries);
            Log.d(TAG, "entries saved to file");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "error saving entries", e);
            return false;
        }
    }

    public void deleteEntry(HappyEntry entry) {
        mEntries.remove(entry);
    }

	public ArrayList<Day> getDaysFromMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		ArrayList<Day> days = new ArrayList<Day>();
		
		for (HappyEntry entry : mEntries) {
			cal.setTime(entry.getTime());
			
			if (cal.get(Calendar.MONTH) == month && cal.get(Calendar.YEAR) == year) {
				Day entryDay = new Day(entry.getTime());
				if (!days.contains(entryDay)) {
					days.add(entryDay);
				}
			}
		}
		
		return days;
	}
	
	public ArrayList<String> retrieveAllTags() {
		ArrayList<String> tags = new ArrayList<String>();
		
		for (HappyEntry entry : mEntries) {
			for (String tag : entry.getTags()) {
				if (!tags.contains(tag)) {
					tags.add(tag);
				}
			}
		}
		
		return tags;
	}
	
	/*
	 * checks if there is an entry after or before a the current month of a year
	 */
	public boolean isEntry(boolean after, int month, int year) {
	    Calendar cal = new GregorianCalendar(year, month+1, 1);;
	     
	    if (!after) {
	        cal.add(Calendar.MONTH, -1);
	        cal.add(Calendar.DAY_OF_MONTH, -1);
	    }
	    
	    for (HappyEntry entry : mEntries) {
	        if (after) {
	            if (entry.getTime().after(cal.getTime())) {
	                return true;
	            }
	        } else {
               if (entry.getTime().before(cal.getTime())) {
                    return true;
                }
	        }
	    }
	    
	    return false;
	}
}
