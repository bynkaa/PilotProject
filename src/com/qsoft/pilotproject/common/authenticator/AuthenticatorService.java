package com.qsoft.pilotproject.common.authenticator;

import android.accounts.Account;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * User: binhtv
 * Date: 10/30/13
 * Time: 11:43 AM
 */
public class AuthenticatorService extends Service {
    private static final String TAG = "AuthenticatorService";

    public static Account getAccount() {
        return new Account(AccountGeneral.ACCOUNT_NAME, AccountGeneral.ACCOUNT_TYPE);
    }

    public IBinder onBind(Intent intent) {
        OnlineDioAuthenticator onlineDioAuthenticator = new OnlineDioAuthenticator(this);
        return onlineDioAuthenticator.getIBinder();
    }
}
