package com.qsoft.pilotproject.ui.controller;

import android.accounts.Account;
import android.accounts.AccountManager;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.SystemService;
import com.qsoft.pilotproject.authenticator.AccountGeneral;
import com.qsoft.pilotproject.authenticator.ApplicationAccountManager;
import com.qsoft.pilotproject.handler.AuthenticatorHandler;
import com.qsoft.pilotproject.handler.impl.AuthenticatorHandlerImpl;
import com.qsoft.pilotproject.model.dto.SignInDTO;

/**
 * User: binhtv
 * Date: 11/8/13
 * Time: 11:32 AM
 */
@EBean
public class AuthenticatorController
{
    @Bean
    ApplicationAccountManager applicationAccountManager;
    @Bean(value = AuthenticatorHandlerImpl.class)
    AuthenticatorHandler authenticatorHandler;
    @SystemService
    AccountManager accountManager;

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
