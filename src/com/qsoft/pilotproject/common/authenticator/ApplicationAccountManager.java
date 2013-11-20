package com.qsoft.pilotproject.common.authenticator;

import android.accounts.Account;
import android.accounts.AccountManager;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.SystemService;
import com.googlecode.androidannotations.api.Scope;

/**
 * User: binhtv
 * Date: 11/6/13
 * Time: 10:09 AM
 */
@EBean(scope = Scope.Singleton)
public class ApplicationAccountManager {
    public static final String ACCOUNT_KEY = "account";

    private Account account;

    private String tokenAuth;
    private Long userId;


    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getTokenAuth() {
        return tokenAuth;
    }

    public void setTokenAuth(String tokenAuth) {
        this.tokenAuth = tokenAuth;
        accountManager.setAuthToken(account, AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS, tokenAuth);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @SystemService
    AccountManager accountManager;

}
