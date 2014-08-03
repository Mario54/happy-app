package com.mariolamontagne.happy.utilities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtility {

    public static String getDateFormatted(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("MMMM d, yyyy", java.util.Locale.getDefault());

        return df.format(date);
    }

    public static String getTimeFormatted(Date time) {
        SimpleDateFormat df = new SimpleDateFormat("h:mm a", java.util.Locale.getDefault());

        return df.format(time);
    }
    
    public static String getTimeFormatted(int hour, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        SimpleDateFormat df = new SimpleDateFormat("h:mm a", java.util.Locale.getDefault());

        return df.format(c.getTime());
    }
    
    public static String getMonthFormatted(Date time) {
        SimpleDateFormat df = new SimpleDateFormat("MMMM yyyy", java.util.Locale.getDefault());

        return df.format(time);
    }

}
