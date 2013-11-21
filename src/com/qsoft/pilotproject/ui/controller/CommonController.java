package com.qsoft.pilotproject.ui.controller;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.os.Bundle;
import android.util.Log;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.SystemService;
import com.qsoft.pilotproject.common.authenticator.AccountGeneral;
import com.qsoft.pilotproject.common.authenticator.ApplicationAccountManager;
import com.qsoft.pilotproject.data.model.dto.SignInDTO;
import com.qsoft.pilotproject.data.provider.CCContract;

/**
 * User: binhtv
 * Date: 11/8/13
 * Time: 11:32 AM
 */
@EBean
public class CommonController
{
    public static final String TAG = "CommonController";
    @Bean
    ApplicationAccountManager applicationAccountManager;
    @SystemService
    AccountManager accountManager;


    public void refreshToken(SignInDTO signInDTO)
    {
        // Refresh token by doing login again
        try
        {
            Account account = applicationAccountManager.getAccount();
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

    public void triggerSync()
    {
        Log.d(TAG, "TriggerSync > account");
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        ContentResolver.requestSync(applicationAccountManager.getAccount(), CCContract.AUTHORITY, bundle);
    }
}
