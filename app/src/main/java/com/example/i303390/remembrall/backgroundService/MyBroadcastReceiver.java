package com.example.i303390.remembrall.backgroundService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.i303390.remembrall.GPSTracker;
import com.example.i303390.remembrall.POJO.LocationListJson;
import com.example.i303390.remembrall.ServiceHandler;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by I303320 on 12/14/2016.
 */

public class MyBroadcastReceiver extends BroadcastReceiver {
    MediaPlayer mp;


    public MyBroadcastReceiver() {
        super();
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        //mp=MediaPlayer.create(context, R.raw.alrm   );
        //mp.start();
        //AlarmManager.AlarmClockInfo inf;
        // AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        //inf = alarmManager.getNextAlarmClock();
        GPSTracker gpsTracker = new GPSTracker(context);
        LatLng ltln = new LatLng(gpsTracker.getLatitude(),gpsTracker.getLongitude());
        //LatLng ltln = new LatLng(12.971891,77.641151);
        Toast.makeText(context, "Before notify", Toast.LENGTH_LONG).show();

        ServiceHandler.getLocations(context, ltln, new VolleyCallbackBackground() {
            @Override
            public void onSuccess(String result) {
                List<LocationListJson> locationListJson = new ArrayList<LocationListJson>();
                ArrayList responseList = JsonMethods.getOfromJSON(result,ArrayList.class);
                locationListJson.addAll(JsonMethods.getAllLocation(responseList));
                for (LocationListJson item:locationListJson) {

                    //Toast.makeText(context, item.getName(), Toast.LENGTH_LONG).show();
                    Log.i(item.getName(), (new Date(System.currentTimeMillis())).toString());
                    // Log.i("Next alarm", String.valueOf(System.currentTimeMillis()));
                    String m = item.getID();//int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
                    Notification n = new Notification(context);
                    n.addNotofication(m, 0);

                }
            }

            @Override
            public void onError(String error) {

                Log.i("service call error: " + error, (new Date(System.currentTimeMillis())).toString());
                Toast.makeText(context, error, ).show();
            }
        });

    }

    @Override
    public IBinder peekService(Context myContext, Intent service) {
        return super.peekService(myContext, service);
    }
}
