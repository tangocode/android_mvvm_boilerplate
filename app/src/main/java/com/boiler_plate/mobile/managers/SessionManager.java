package com.boiler_plate.mobile.managers;

import com.boiler_plate.mobile.util.Util;

import java.util.Date;

/**
 * Created by juanpablogarcia on 3/29/16.
 */
public class SessionManager {
    public String OAUTH_TOKEN;
    public Date startTime;
    private int SESSION_DURATION = 300000; //Time in miliseconds

    public void initSession(String oAuthToken) {
        this.OAUTH_TOKEN = oAuthToken;
        startTime = new Date();
    }

    public Boolean isSessionValid() {
        //TODO-Here it would be necessary to renew the token in the server
        if (OAUTH_TOKEN != null && startTime!= null) {
            Date currentTime = new Date();
            Date delta = Util.addMinutesToDate(SESSION_DURATION / 600000, startTime);
            if (delta.before(currentTime)) {
                startTime = new Date();
                return true;
            }
        }
        return false;
    }
}
