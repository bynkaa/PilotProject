package com.qsoft.pilotproject.handler.impl;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.util.Log;
import com.google.gson.Gson;
import com.qsoft.pilotproject.authenticator.AccountGeneral;
import com.qsoft.pilotproject.handler.ProfileHandler;
import com.qsoft.pilotproject.model.dto.ProfileDTO;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * User: BinkaA
 * Date: 11/4/13
 * Time: 10:41 PM
 */
public class ProfileHandlerImpl implements ProfileHandler
{
    private static final String TAG = "ProfileHandlerImpl";
    AccountManager accountManager;
    Account account;

    public ProfileHandlerImpl(AccountManager accountManager, Account account)
    {
        this.accountManager = accountManager;
        this.account = account;
    }

    @Override
    public ProfileDTO getProfile(long userId)
    {
        Log.d(TAG, "get profile:" + userId);
        DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = "http://113.160.50.84:1009/testing/ica467/trunk/public/user-rest/";
        url = url + userId;
        HttpGet httpGet = new HttpGet(url);
        String authToken = accountManager.peekAuthToken(account, AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS);
        httpGet.addHeader("Authorization", "Bearer " + authToken);
        try
        {
            HttpResponse response = httpClient.execute(httpGet);
            String responseString = EntityUtils.toString(response.getEntity());
            JSONObject jsonObject = new JSONObject(responseString);
            if (jsonObject.has("code") && (Integer) jsonObject.get("code") == 200)
            {
                ProfileDTO profileDTO = new Gson().fromJson(jsonObject.get("data").toString(), ProfileDTO.class);
                return profileDTO;
            }
            Log.d(TAG, responseString);
            return null;
        }
        catch (IOException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        catch (JSONException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;

    }
}
