package com.qsoft.pilotproject.common.authenticator;

import android.accounts.*;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.qsoft.pilotproject.data.model.dto.SignInDTO;
import com.qsoft.pilotproject.data.rest.OnlineDioClientProxy;
import com.qsoft.pilotproject.ui.activity.LoginActivity_;

/**
 * User: binhtv
 * Date: 10/30/13
 * Time: 8:45 AM
 */
@EBean
public class OnlineDioAuthenticator extends AbstractAccountAuthenticator {
    public static final String ACCOUNT_TYPE_KEY = "account_type";
    public static final String AUTH_TYPE_KEY = "auth_type";
    public static final String IS_ADDED_ACCOUNT_KEY = "is_added_account";
    public static final String AUTHENTICATOR_RESPONSE = "authenticator_response";
    public static final String ACCOUNT_NAME_KEY = "account_name";
    public static final String KEY_BOOLEAN_RESULT = "boolean_result";
    private String TAG = "Online Dio";
    private final Context context;

    public OnlineDioAuthenticator(Context context) {
        super(context);
        this.context = context;
    }

    @Bean
    OnlineDioClientProxy onlineDioClientProxy;

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse accountAuthenticatorResponse, String s) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Bundle addAccount(AccountAuthenticatorResponse accountAuthenticatorResponse, String accountType, String authTokenType, String[] requireFeatures, Bundle options) throws NetworkErrorException {
        Log.d(TAG, "add Account(response)");
        final Intent intent = new Intent(context, LoginActivity_.class);
        intent.putExtra(ACCOUNT_TYPE_KEY, accountType);
        intent.putExtra(AUTH_TYPE_KEY, authTokenType);
        intent.putExtra(IS_ADDED_ACCOUNT_KEY, true);
        intent.putExtra(AUTHENTICATOR_RESPONSE, accountAuthenticatorResponse);
        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, Bundle bundle) throws NetworkErrorException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Bundle getAuthToken(Account account, String authTokenType) throws NetworkErrorException {
        Log.d(TAG, "getAuthToken(accountResponse)");

        final AccountManager accountManager = AccountManager.get(context);
        String authToken = accountManager.peekAuthToken(account, authTokenType);
        if (TextUtils.isEmpty(authToken)) {
            final String password = accountManager.getPassword(account);
            if (password != null) {
                try {
                    Log.d(TAG, "authenticating with existing password");
                    SignInDTO signInDTO = AccountGeneral.onlineDioService.signIn(account.name, password, authTokenType);
//                    SignInDTO signInDTO = onlineDioClientProxy.signIn(account.name,password);
                    authToken = signInDTO.getAccessToken();
                } catch (Exception e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
            if (!TextUtils.isEmpty(authToken)) {
                final Bundle result = new Bundle();
                result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
                result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
                result.putString(AccountManager.KEY_AUTHTOKEN, authToken);
                return result;
            }
        }
        final Bundle bundle = new Bundle();
        bundle.putString(ACCOUNT_TYPE_KEY, account.type);
        bundle.putString(AUTH_TYPE_KEY, authTokenType);
        bundle.putString(ACCOUNT_NAME_KEY, account.name);
        return bundle;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
        Log.d(TAG, "getAuthToken(accountResponse)");

        final AccountManager accountManager = AccountManager.get(context);
        String authToken = accountManager.peekAuthToken(account, authTokenType);
        if (TextUtils.isEmpty(authToken)) {
            final String password = accountManager.getPassword(account);
            if (password != null) {
                try {
                    Log.d(TAG, "authenticating with existing password");
                    SignInDTO signInDTO = AccountGeneral.onlineDioService.signIn(account.name, password, authTokenType);
                    authToken = signInDTO.getAccessToken();
                } catch (Exception e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
            if (!TextUtils.isEmpty(authToken)) {
                final Bundle result = new Bundle();
                result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
                result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
                result.putString(AccountManager.KEY_AUTHTOKEN, authToken);
                return result;
            }
        }
        final Intent intent = new Intent(context, LoginActivity_.class);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, accountAuthenticatorResponse);
        intent.putExtra(ACCOUNT_TYPE_KEY, account.type);
        intent.putExtra(AUTH_TYPE_KEY, authTokenType);
        intent.putExtra(ACCOUNT_NAME_KEY, account.name);
        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getAuthTokenLabel(String authTokenType) {
        if (AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS.equals(authTokenType)) {
            return AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS;
        } else {
            return authTokenType + "(x)";
        }
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String s, Bundle bundle) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String[] strings) throws NetworkErrorException {
        final Bundle result = new Bundle();
        result.putBoolean(KEY_BOOLEAN_RESULT, false);
        return result;
    }
}
