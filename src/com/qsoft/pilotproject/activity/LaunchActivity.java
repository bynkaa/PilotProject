package com.qsoft.pilotproject.activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.example.PilotProject.R;
import com.qsoft.pilotproject.authenticator.AccountGeneral;

/**
 * User: binhtv
 * Date: 10/14/13
 * Time: 11:27 AM
 */
public class LaunchActivity extends Activity
{
    public static final String TAG = "LaunchActivity";
    Button btLoginFB;
    Button btLogin;
    Button btSignOut;
    AccountManager accountManager;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laucher);
        btLogin = (Button) findViewById(R.id.btLogin);
        btLogin.setOnClickListener(btLoginClickListener);
        accountManager =  AccountManager.get(this);
    }

    private View.OnClickListener btLoginClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            addNewAccount();
        }
    };

    private void addNewAccount() {
        final AccountManagerFuture<Bundle> future = accountManager.addAccount(AccountGeneral.ACCOUNT_TYPE,AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS,null,null,this,new AccountManagerCallback<Bundle>() {
            @Override
            public void run(AccountManagerFuture<Bundle> bundleAccountManagerFuture) {
                try{
                    Bundle bundle = bundleAccountManagerFuture.getResult();
                    Log.d(TAG, "Account was created");
                }catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        },null);
    }
}