package com.example.i303390.remembrall;

import java.sql.Time;

/**
 * Created by I320047 on 12/12/2016.
 */

public class UserDetails {

    private String name;
    private String eMail;
    private Boolean notificationStatus;
    private Boolean isTimeSet;
    private Time quietStart;
    private Time quietEnd;

    UserDetails (String name,String mail){
        this.name = name;
        this.eMail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String geteMail() {
        return eMail;
    }

    public Boolean getTimeSet() {
        return isTimeSet;
    }

    public void setTimeSet(Boolean timeSet) {
        isTimeSet = timeSet;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public Boolean getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(Boolean notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    public Time getQuietStart() {
        return quietStart;
    }

    public void setQuietStart(Time quietStart) {
        this.quietStart = quietStart;
    }

    public Time getQuietEnd() {
        return quietEnd;
    }

    public void setQuietEnd(Time quietEnd) {
        this.quietEnd = quietEnd;
    }
}
