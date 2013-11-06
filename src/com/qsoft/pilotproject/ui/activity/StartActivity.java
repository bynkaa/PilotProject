package com.qsoft.pilotproject.ui.activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.util.Log;
import com.example.PilotProject.R;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.SystemService;
import com.qsoft.eip.common.SuperAnnotationActivity;
import com.qsoft.pilotproject.authenticator.AccountGeneral;
import com.qsoft.pilotproject.authenticator.ApplicationAccountManager;

/**
 * User: binhtv
 * Date: 10/30/13
 * Time: 5:32 PM
 */
@EActivity(R.layout.main)
public class StartActivity extends SuperAnnotationActivity
{
    @SystemService
    AccountManager accountManager;

    @Bean
    ApplicationAccountManager applicationAccountManager;

    @AfterViews
    void afterViews()
    {
        Account[] accounts = accountManager.getAccountsByType(AccountGeneral.ACCOUNT_TYPE);

        if (accounts.length == 1)
        {
            Account account = accounts[0];
            Intent intent = new Intent(this, SlideBarActivity_.class);
            applicationAccountManager.setAccount(account);
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
            Intent intent = new Intent(this, LaunchActivity_.class);
            startActivity(intent);
        }
    }
}