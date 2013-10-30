package com.qsoft.pilotproject.activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.qsoft.pilotproject.authenticator.AccountGeneral;

/**
 * User: binhtv
 * Date: 10/30/13
 * Time: 5:32 PM
 */
public class StartActivity extends Activity {
    public static final String TAG = "StartActivity";
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AccountManager accountManager = AccountManager.get(this);
        Account[] accounts = accountManager.getAccountsByType(AccountGeneral.ACCOUNT_TYPE);
        if (accounts.length > 1)
        {
            Log.d(TAG, "there are more than 2 account of OnlineDio");
        }
        else if (accounts.length == 1)
        {
            Account account = accounts[0];
            Intent intent = new Intent(this,SlideBar.class);
            startActivity(intent);
            finish();
        }
        else {
            Intent intent = new Intent(this,LaunchActivity.class);
            startActivity(intent);
        }
    }
}