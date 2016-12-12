package com.example.i303390.remembrall;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;

/**
 * Created by I320047 on 12/11/2016.
 */

public class Profile extends AppCompatActivity {

    private RoundedImageView imageViewRound;
    private Switch notificationSwitch;
    private static Boolean notificationStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        getDelegate().installViewFactory();
//        getDelegate().onCreate(savedInstanceState);
        notificationStatus = true;
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);

        imageViewRound=(RoundedImageView)findViewById(R.id.user_profile_photo);

        Bitmap icon = BitmapFactory.decodeStream(getResources().openRawResource(+R.drawable.profilepic));
        imageViewRound.setImageBitmap(icon);

        notificationSwitch = (Switch)findViewById(R.id.notification_switch);

        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                          @Override
                                                          public void onCheckedChanged(CompoundButton compoundButton, boolean allowNotification) {
                                                              notificationStatus = allowNotification;
                                                          }
                                                      });

    }

    public static Boolean getNotificationStatus(){
        return notificationStatus;
    }
}
