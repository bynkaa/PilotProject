package com.qsoft.pilotproject.ui.controller;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import com.example.PilotProject.R;
import com.googlecode.androidannotations.annotations.*;
import com.qsoft.pilotproject.authenticator.AccountGeneral;
import com.qsoft.pilotproject.authenticator.ApplicationAccountManager;
import com.qsoft.pilotproject.handler.AuthenticatorHandler;
import com.qsoft.pilotproject.handler.impl.AuthenticatorHandlerImpl;
import com.qsoft.pilotproject.model.dto.SignInDTO;

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

    @Bean(value = AuthenticatorHandlerImpl.class)
    AuthenticatorHandler authenticatorHandler;

    @Bean
    ApplicationAccountManager applicationAccountManager;

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

    public void refreshToken()
    {
        // Refresh token by doing login again
        try
        {
            Account account = applicationAccountManager.getAccount();
            SignInDTO signInDTO = authenticatorHandler.signIn(account.name, accountManager.getPassword(account), AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS);
            if (signInDTO.getAccessToken() != null)
            {
                accountManager.setAuthToken(account, AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS, signInDTO.getAccessToken());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
