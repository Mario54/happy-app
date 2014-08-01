package com.mariolamontagne.happy.utilities;

import java.text.SimpleDateFormat;
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

}
