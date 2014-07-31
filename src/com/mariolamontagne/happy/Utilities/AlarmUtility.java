package com.mariolamontagne.happy.Utilities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;

public class AlarmUtility {

    public static ArrayList<Date> getAlarmTimes() {
        // TODO Make this a setting?
        ArrayList<Date> times = new ArrayList<Date>();
        times.add(new GregorianCalendar(2014, 02, 22, 9, 0).getTime());
        times.add(new GregorianCalendar(2014, 02, 22, 13, 0).getTime());
        times.add(new GregorianCalendar(2014, 02, 22, 15, 0).getTime());
        times.add(new GregorianCalendar(2014, 02, 22, 17, 0).getTime());
        times.add(new GregorianCalendar(2014, 02, 22, 19, 0).getTime());

        return times;
    }

    public static Date getEarliestAlarm() {
        ArrayList<Date> times = getAlarmTimes();
        ArrayList<Date> todaysAlarms = new ArrayList<Date>();

        Calendar alarmCal = Calendar.getInstance();

        for (Date time : times) {
            alarmCal.setTime(time);

            // Only get the hour and minutes, actual date is irrelevant
            int alarmHour = alarmCal.get(Calendar.HOUR_OF_DAY);
            int alarmMinute = alarmCal.get(Calendar.MINUTE);

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

            alarmCal.setTime(times.get(0)); // get earliest alarm

            int alarmHour = alarmCal.get(Calendar.HOUR_OF_DAY);
            int alarmMinute = alarmCal.get(Calendar.MINUTE);

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
