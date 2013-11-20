package com.qsoft.pilotproject.ui.controller;

import android.accounts.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.TextView;
import com.googlecode.androidannotations.annotations.*;
import com.qsoft.pilotproject.R;
import com.qsoft.pilotproject.common.CommandExecutor;
import com.qsoft.pilotproject.common.authenticator.AccountGeneral;
import com.qsoft.pilotproject.common.authenticator.ApplicationAccountManager;
import com.qsoft.pilotproject.common.authenticator.OnlineDioAuthenticator;
import com.qsoft.pilotproject.common.commands.GenericStartActivityCommand;
import com.qsoft.pilotproject.ui.activity.SlideBarActivity_;

/**
 * User: Le
 * Date: 11/5/13
 */
@EBean
public class LoginController
{
    private static final String TAG = "LoginController";
    static final String KEY_USER_PASSWORD = "user_pass";
    public static final String USER_ID_KEY = "user_id";
    private static final int RC_SLIDE_BAR_ACTIVITY = 2;

    public static String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @SystemService
    AccountManager accountManager;

    @RootContext
    Activity activity;


    @Bean
    ApplicationAccountManager applicationAccountManager;
    @Bean
    CommandExecutor commandExecutor;

    @Click(R.id.btLogin)
    void doLogin()
    {
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


    public void finishLogin(Intent intent)
    {
        Log.d(TAG, "finishLogin(intent)");
        String accountName = intent.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
        String accountPassword = intent.getStringExtra(KEY_USER_PASSWORD);
        Account account = new Account(accountName, intent.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE));
        if (activity.getIntent().getBooleanExtra(OnlineDioAuthenticator.IS_ADDED_ACCOUNT_KEY, false))
        {
            Log.d(TAG, "finishLogin > addAccountExplicitly");
            String authToken = intent.getStringExtra(AccountManager.KEY_AUTHTOKEN);
            Bundle bundle = new Bundle();
            Long userId = intent.getLongExtra(USER_ID_KEY, 0);
            bundle.putString(USER_ID_KEY, Long.toString(userId));
            accountManager.addAccountExplicitly(account, accountPassword, bundle);
            applicationAccountManager.setAccount(account);
            applicationAccountManager.setTokenAuth(authToken);
            applicationAccountManager.setUserId(userId);
            accountManager.setAuthToken(account, AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS, authToken);
        }
        else
        {
            Log.d(TAG, "finish Login > set password");
            accountManager.setPassword(account, accountPassword);
        }

        ((AccountAuthenticatorActivity) activity).setAccountAuthenticatorResult(intent.getExtras());
        applicationAccountManager.setAccount(account);
        commandExecutor.execute(activity,
                new GenericStartActivityCommand(activity, SlideBarActivity_.class, RC_SLIDE_BAR_ACTIVITY)
                {
                    @Override
                    public void overrideExtra(Intent intent)
                    {
                    }
                }, false);

        Log.d(TAG, "Login successfully");
    }

    public boolean isOnlineNetwork()
    {
        // checkTimeoutService();
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting())
        {
            Log.d(TAG, "network available");
            return true;
        }
        else
        {
            AlertDialog dialog = showAlertDialog("Error Signing In", "There is no connection to the internet.");
            dialog.show();
            Log.d(TAG, "network no connection");
            return false;
        }
    }

    public AlertDialog showAlertDialog(String txtTitle, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        final TextView title = new TextView(activity);
        title.setText(txtTitle);
        title.setTextSize(20);
        title.setTextColor(Color.WHITE);
        title.setGravity(Gravity.CENTER);
        builder.setCustomTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", null);
        AlertDialog dialog = builder.show();
        TextView messageText = (TextView) dialog.findViewById(android.R.id.message);
        messageText.setGravity(Gravity.CENTER);
        return dialog;
    }

    public boolean validateMailAndPassword(EditText mail, EditText password)
    {
        String _mail = mail.getText().toString();
        String _password = password.getText().toString();
        if (_mail.matches(EMAIL_PATTERN) == false)
        {
            AlertDialog dialog = showAlertDialog("Error Signing In", "Email address is incorrect.");
            dialog.show();
            mail.requestFocus();
            return false;
        }
        else if (_password.length() <= 0)
        {
            AlertDialog dialog = showAlertDialog("Error Signing In", "Password is incorrect.");
            dialog.show();
            password.requestFocus();
            return false;
        }
        else
        {
            return true;
        }
    }
}