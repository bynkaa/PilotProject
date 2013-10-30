package com.qsoft.pilotproject.authenticator;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * User: binhtv
 * Date: 10/30/13
 * Time: 11:43 AM
 */
public class AuthenticatorService extends Service {
    public IBinder onBind(Intent intent) {
        OnlineDioAuthenticator onlineDioAuthenticator = new OnlineDioAuthenticator(this);
        return onlineDioAuthenticator.getIBinder();
    }
}
