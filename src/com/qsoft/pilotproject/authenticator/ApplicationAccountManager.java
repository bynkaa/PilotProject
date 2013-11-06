package com.qsoft.pilotproject.authenticator;

import android.accounts.Account;
import android.accounts.AccountManager;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.SystemService;
import com.googlecode.androidannotations.api.Scope;
import com.qsoft.pilotproject.handler.AuthenticatorHandler;
import com.qsoft.pilotproject.handler.impl.AuthenticatorHandlerImpl;
import com.qsoft.pilotproject.model.dto.SignInDTO;

/**
 * User: binhtv
 * Date: 11/6/13
 * Time: 10:09 AM
 */
@EBean(scope = Scope.Singleton)
public class ApplicationAccountManager
{
    public static final String ACCOUNT_KEY = "account";

    private Account account;

    private String tokenAuth;


    public Account getAccount()
    {
        return account;
    }

    public void setAccount(Account account)
    {
        this.account = account;
    }

    public String getTokenAuth()
    {
        return tokenAuth;
    }

    public void setTokenAuth(String tokenAuth)
    {
        this.tokenAuth = tokenAuth;
        accountManager.setAuthToken(account, AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS, tokenAuth);
    }

    @SystemService
    AccountManager accountManager;

    @Bean
    OnlineDioAuthenticator onlineDioAuthenticator;

    @Bean(value = AuthenticatorHandlerImpl.class)
    AuthenticatorHandler authenticatorHandler;

    public void refreshToken()
    {
        // Refresh token by doing login again
        try
        {
            SignInDTO signInDTO = authenticatorHandler.signIn(account.name, accountManager.getPassword(account), AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS);
            if (signInDTO.getAccessToken() != null)
            {
                setTokenAuth(signInDTO.getAccessToken());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
