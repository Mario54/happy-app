package com.mariolamontagne.happy;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class AlarmService extends Service {

    Alarm alarm = new Alarm();

    public void onCreate() {
        super.onCreate();
        Log.d("", "Service onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("", "Service startCommand()");
        alarm.setNextAlarm(this);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

}
