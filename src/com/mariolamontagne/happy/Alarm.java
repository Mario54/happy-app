package com.mariolamontagne.happy;

import java.util.Date;

import com.mariolamontagne.happy.activities.EditHappyEntryActivity;
import com.mariolamontagne.happy.utilities.AlarmUtility;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class Alarm extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        try {
            wl.acquire();

            PendingIntent contentIntent = PendingIntent.getActivity(context, 0, new Intent(context,
                    EditHappyEntryActivity.class), PendingIntent.FLAG_ONE_SHOT);

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                    .setContentTitle("Time to add an entry to Happy!").setContentText("Hello World!")
                    .setSmallIcon(android.R.drawable.ic_input_add).setContentIntent(contentIntent)
                    .setDefaults(Notification.DEFAULT_SOUND).setAutoCancel(true);
            NotificationManager mNotificationManager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(1, mBuilder.build());

            setNextAlarm(context);
        } finally {
            wl.release();
        }
    }

    public void setNextAlarm(Context context) {
        Log.d("", "Service setAlarm()");
        Date alarm = AlarmUtility.getEarliestAlarm();

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, Alarm.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
        am.set(AlarmManager.RTC_WAKEUP, alarm.getTime(), pi);
    }

    public void cancelAlarm(Context context) {
        Intent intent = new Intent(context, Alarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.cancel(sender);
    }

}
