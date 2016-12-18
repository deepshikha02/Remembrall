package com.example.i303390.remembrall.backgroundService;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

/**
 * Created by I303320 on 12/16/2016.
 */

public class PullService extends Service {
    private final IBinder mBinder = new MyBinder();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Intent i = new Intent(this, MyBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this.getApplicationContext(), 12345, i, FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 200, pendingIntent );
        alarmManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), 200, pendingIntent );
        Toast.makeText(this, "Pull service stated...", Toast.LENGTH_LONG).show();

        return Service.START_NOT_STICKY;
    }
    @Override
    public IBinder onBind(Intent arg0) {
        return mBinder;
    }

    public class MyBinder extends Binder {
        PullService getService() {
            return PullService.this;
        }
    }

}
