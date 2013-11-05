package com.qsoft.pilotproject.ui.controller;

import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import com.example.PilotProject.R;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.googlecode.androidannotations.annotations.SystemService;
import com.qsoft.pilotproject.authenticator.AccountGeneral;

/**
 * User: Le
 * Date: 11/5/13
 */
@EBean
public class LoginController
{
    @SystemService
    AccountManager accountManager;

    @RootContext
    Activity activity;

    @Click(R.id.btLogin)
    void doLogin() {
        final AccountManagerFuture<Bundle> future = accountManager.addAccount(AccountGeneral.ACCOUNT_TYPE,
                AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS, null, null, activity, new AccountManagerCallback<Bundle>()
        {
            @Override
            public void run(AccountManagerFuture<Bundle> bundleAccountManagerFuture)
            {
                try
                {
                    Bundle bundle = bundleAccountManagerFuture.getResult();
                    Log.d("", "Account was created");
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        }, null);
    }
}
