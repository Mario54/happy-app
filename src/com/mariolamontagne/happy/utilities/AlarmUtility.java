package com.mariolamontagne.happy.utilities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;

import com.mariolamontagne.happy.model.Reminder;

import android.content.Context;

public class AlarmUtility {
    
    private ArrayList<Reminder> mReminders = getAlarmTimes();
    private Context mContext;
    private static AlarmUtility sAlarmUtility;
    
    private AlarmUtility(Context context) {
        mContext = context;
    }
    
    public static AlarmUtility get(Context c) {
        if (sAlarmUtility == null) {
            sAlarmUtility = new AlarmUtility(c);
        }
        
        return sAlarmUtility;
    }

    public ArrayList<Reminder> getReminders() {
        return mReminders;
    }

    public static ArrayList<Reminder> getAlarmTimes() {
        ArrayList<Reminder> times = new ArrayList<Reminder>();
        times.add(new Reminder(9, 0));
        times.add(new Reminder(13, 0));
        times.add(new Reminder(15, 0));
        times.add(new Reminder(17, 0));
        times.add(new Reminder(19, 0));

        return times;
    }

    public Date getEarliestAlarm() {
        ArrayList<Reminder> times = getAlarmTimes();
        ArrayList<Date> todaysAlarms = new ArrayList<Date>();

        Calendar alarmCal = Calendar.getInstance();

        for (Reminder reminder : times) {
            // Only get the hour and minutes, actual date is irrelevant
            int alarmHour = reminder.getHour();
            int alarmMinute = reminder.getMinute();

            // Get today's date, but change hour and minutes
            Date alarmTime = new Date();
            alarmCal.setTime(alarmTime);
            alarmCal.set(Calendar.HOUR_OF_DAY, alarmHour);
            alarmCal.set(Calendar.MINUTE, alarmMinute);
            alarmTime = alarmCal.getTime();

            // If alarm is in the future, add it to the list
            if (System.currentTimeMillis() < alarmTime.getTime()) {
                todaysAlarms.add(alarmTime);
            }
        }

        // If no alarm exist for today, get tomorrow's earliest alarm
        if (todaysAlarms.size() == 0) {
            Collections.sort(times);

            int alarmHour = times.get(0).getHour();
            int alarmMinute = times.get(0).getMinute();

            alarmCal.setTime(new Date());
            alarmCal.add(Calendar.DAY_OF_YEAR, 1);
            alarmCal.set(Calendar.HOUR_OF_DAY, alarmHour);
            alarmCal.set(Calendar.MINUTE, alarmMinute);

            return alarmCal.getTime();
        }

        Collections.sort(todaysAlarms);

        return (todaysAlarms.size() > 0) ? todaysAlarms.get(0) : null;
    }
}
