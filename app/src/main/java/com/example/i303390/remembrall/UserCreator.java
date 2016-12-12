package com.example.i303390.remembrall;

import android.content.Context;
import android.content.SharedPreferences;

import java.sql.Time;

/**
 * Created by I320047 on 12/12/2016.
 */

public class UserCreator
{
    public static UserDetails getUser(Context context)
    {
        SharedPreferences prefs = context.getSharedPreferences("Name", Context.MODE_PRIVATE);

        //Check if the user is already stored, if is, then simply get the data from
        //your SharedPreference object.

        boolean isValid = prefs.getBoolean("valid", false);

        if(isValid)
        {
            Boolean isTimeSet  = Boolean.parseBoolean(prefs.getString("isTimeSet", "false"));
            if(isTimeSet){
                Time stTime = new Time(prefs.getLong("stTime",0));
                Time endTime = new Time(prefs.getLong("endTime",0));
            }
            String user = prefs.getString("user", "");
            String mail = prefs.getString("mail", "");
            return new UserDetails(user,mail);
        }
        //If not, then store data
        else
        {
            //for example show a dialog here, where the user can log in.
            //when you have the data, then:

                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("user", "Abhishek Agrawal");
                editor.putString("mail", "abhishek.agrawal03@sap.com");
                editor.putString("isTimeSet","false");
                editor.putBoolean("valid", true);
                editor.commit();

            return getUser(context);
        }
    }

    public static void setUser(Context context,UserDetails user){
        SharedPreferences prefs = context.getSharedPreferences("Name", Context.MODE_PRIVATE);
        boolean isValid = prefs.getBoolean("valid", false);
        if(isValid) {
            SharedPreferences.Editor editor = prefs.edit();
            if(user.getTimeSet()){
                editor.putString("isTimeSet","true");
                editor.putLong("stTime",user.getQuietStart().getTime());
                editor.putLong("endTime",user.getQuietEnd().getTime());
                editor.commit();
            }
        }
    }
}