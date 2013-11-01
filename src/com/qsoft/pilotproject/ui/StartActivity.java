package com.qsoft.pilotproject.ui;

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
public class StartActivity extends Activity
{
    public static final String TAG = "StartActivity";
    public static final String ACCOUNT_KEY = "account";

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        AccountManager accountManager = AccountManager.get(this);
        Account[] accounts = accountManager.getAccountsByType(AccountGeneral.ACCOUNT_TYPE);

        if (accounts.length == 1)
        {
            Account account = accounts[0];
            Intent intent = new Intent(this, SlideBarActivity.class);
            intent.putExtra(ACCOUNT_KEY, account);
            startActivity(intent);
            finish();
        }
        else
        {
            if (accounts.length > 1)
            {
                Log.d(TAG, "there are more than 2 account of OnlineDio");
                for (int i = 0; i < accounts.length; i++)
                {
                    accountManager.removeAccount(accounts[i], null, null);
                }
            }
            Intent intent = new Intent(this, LaunchActivity.class);
            startActivity(intent);
        }

    }
}