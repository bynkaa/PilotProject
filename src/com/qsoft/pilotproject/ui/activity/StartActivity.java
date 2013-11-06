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
import com.qsoft.pilotproject.authenticator.AccountGeneral;
import com.qsoft.pilotproject.authenticator.ApplicationAccountManager;
import com.qsoft.pilotproject.common.CommandExecutor;
import com.qsoft.pilotproject.common.SuperAnnotationActivity;
import com.qsoft.pilotproject.common.commands.GenericStartActivityCommand;

/**
 * User: binhtv
 * Date: 10/30/13
 * Time: 5:32 PM
 */
@EActivity(R.layout.main)
public class StartActivity extends SuperAnnotationActivity
{
    private static final int RC_SLIDE_BAR_ACTIVITY = 1;
    private static final int RC_LAUCH_ACTIVITY = 2;
    @SystemService
    AccountManager accountManager;

    @Bean
    ApplicationAccountManager applicationAccountManager;
    @Bean
    CommandExecutor commandExecutor;

    @AfterViews
    void afterViews()
    {
        Account[] accounts = accountManager.getAccountsByType(AccountGeneral.ACCOUNT_TYPE);

        if (accounts.length == 1)
        {
            Account account = accounts[0];
            applicationAccountManager.setAccount(account);
            commandExecutor.execute(this,
                    new GenericStartActivityCommand(this, SlideBarActivity_.class, RC_SLIDE_BAR_ACTIVITY)
                    {
                        @Override
                        public void overrideExtra(Intent intent)
                        {
                        }
                    }, false);

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
            commandExecutor.execute(this,
                    new GenericStartActivityCommand(this, LaunchActivity_.class, RC_LAUCH_ACTIVITY)
                    {
                        @Override
                        public void overrideExtra(Intent intent)
                        {
                        }
                    }, false);
        }
    }
}