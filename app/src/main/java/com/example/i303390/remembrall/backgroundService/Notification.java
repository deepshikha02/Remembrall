package com.example.i303390.remembrall.backgroundService;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.i303390.remembrall.R;
import com.example.i303390.remembrall.TaskMapContainer;


/**
 * Created by I303320 on 12/15/2016.
 */

public class Notification {

    private Context context;

    public Notification(Context context){
        this.context = context;
    }

    public void addNotofication(String tag, int id){
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.cast_ic_notification_small_icon)
                        .setContentTitle("Notifications Example")
                        .setContentText("This is a test notification");

        Intent notificationIntent = new Intent(context, TaskMapContainer.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);


        // Add as notification
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(tag, id, builder.build());



    }

    public void addPendingTaskNotofication(String tag, int id){
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.cast_ic_notification_small_icon)
                        .setContentTitle("")
                        .setContentText("This is a test notification");

        Intent notificationIntent = new Intent(context, TaskMapContainer.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);


        // Add as notification
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(tag, id, builder.build());



    }

}
