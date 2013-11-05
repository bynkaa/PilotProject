package com.qsoft.pilotproject.ui.activity;

import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.example.PilotProject.R;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.SystemService;
import com.googlecode.androidannotations.annotations.ViewById;
import com.qsoft.eip.common.SuperAnnotationActivity;
import com.qsoft.pilotproject.authenticator.AccountGeneral;

/**
 * User: binhtv
 * Date: 10/14/13
 * Time: 11:27 AM
 */
@EActivity(R.layout.activity_launcher)
public class LaunchActivity extends SuperAnnotationActivity
{
    public static final String TAG = "LaunchActivity";

    @ViewById(R.id.btLogin)
    Button btLogin;

    Button btSignOut;

    @SystemService
    AccountManager accountManager;

    @Click(R.id.btLogin)
    void doLogin() {
        final AccountManagerFuture<Bundle> future = accountManager.addAccount(AccountGeneral.ACCOUNT_TYPE,
                AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS, null, null, this, new AccountManagerCallback<Bundle>()
        {
            @Override
            public void run(AccountManagerFuture<Bundle> bundleAccountManagerFuture)
            {
                try
                {
                    Bundle bundle = bundleAccountManagerFuture.getResult();
                    Log.d(TAG, "Account was created");
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        }, null);
    }
}