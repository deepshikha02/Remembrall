package com.example.i303390.remembrall;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notifyMe();
//        Intent intent = new Intent(this, TaskMapContainer.class);
//        startActivity(intent);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }
    public void notifyMe(){
        if(UserCreator.getUser(this).getNotificationStatus()){

            String task = getIntent().getStringExtra("task");
            if(task != null) {

                String lat = getIntent().getStringExtra("lat");
                String lon = getIntent().getStringExtra("lon");

                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(this)
                                .setSmallIcon(R.drawable.notify)
                                .setContentTitle("You have a task pending.")
                                .setAutoCancel(true)
                                .setContentText(task);

                // Creates an explicit intent for an Activity in your app
                Intent maps = new Intent(this, TaskMapContainer.class);

                // The stack builder object will contain an artificial back stack for the
                // started Activity.
                // This ensures that navigating backward from the Activity leads out of
                // your application to the Home screen.
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
                // Adds the back stack for the Intent (but not the Intent itself)
                stackBuilder.addParentStack(MapsActivity.class);
                // Adds the Intent that starts the Activity to the top of the stack
                stackBuilder.addNextIntent(maps);
                PendingIntent resultPendingIntent =
                        stackBuilder.getPendingIntent(
                                0,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        );
                mBuilder.setContentIntent(resultPendingIntent);
                NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                // mId allows you to update the notification later on.
                mNotificationManager.notify(0, mBuilder.build());
            }
        }
    }
    public static void openNotification(Context c,String task){
        Intent intent = new Intent(c, TaskMapContainer.class);
        intent.putExtra("task", task);
        c.startActivity(intent);
    }

    public static void openNotification(Context c,String task,String lat,String lon){
        Intent intent = new Intent(c, NotificationActivity.class);
        intent.removeExtra("task");
        intent.removeExtra("lat");
        intent.removeExtra("lon");
        intent.putExtra("task", task);
        intent.putExtra("lat", lat);
        intent.putExtra("lon", lon);
        c.startActivity(intent);
    }

}
