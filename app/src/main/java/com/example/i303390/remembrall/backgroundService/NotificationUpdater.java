package com.example.i303390.remembrall.backgroundService;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by I303320 on 12/16/2016.
 */

public class NotificationUpdater {

    private Handler mHandler = new Handler(Looper.getMainLooper());

    private Runnable mStatusChecker;
    private int UPDATE_INTERVAL = 5000;

    public NotificationUpdater(final Runnable notificationUpdater) {
        mStatusChecker = new Runnable() {
            @Override
            public void run() {
                // Run the passed runnable
                notificationUpdater.run();
                // Re-run it after the update interval
                mHandler.postDelayed(this, UPDATE_INTERVAL);
            }
        };
    }

    public NotificationUpdater(Runnable notificationUpdater, int interval){
        this(notificationUpdater);
        UPDATE_INTERVAL = interval;
    }

    public synchronized void startUpdates(){
        mStatusChecker.run();
    }

    public synchronized void stopUpdates(){
        mHandler.removeCallbacks(mStatusChecker);
    }
}
