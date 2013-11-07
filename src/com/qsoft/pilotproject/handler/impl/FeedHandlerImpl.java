package com.qsoft.pilotproject.handler.impl;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.qsoft.pilotproject.authenticator.AccountGeneral;
import com.qsoft.pilotproject.authenticator.ApplicationAccountManager;
import com.qsoft.pilotproject.authenticator.InvalidTokenException;
import com.qsoft.pilotproject.handler.FeedHandler;
import com.qsoft.pilotproject.model.dto.FeedDTO;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * User: binhtv
 * Date: 10/31/13
 * Time: 5:46 PM
 */
@EBean
public class FeedHandlerImpl implements FeedHandler
{
    private static final String TAG = "FeedHandlerImpl";
    private static final String INVALIDTOKEN_MESSAGE = "cannot access my apis";

    @Bean
    ApplicationAccountManager applicationAccountManager;

    @Override
    public List<FeedDTO> getFeeds(AccountManager accountManager, Account account)
    {
        Boolean retry = true;
        int countLoop = 0;
        while (retry)
        {
            Log.d(TAG, "getFeeds()");
            DefaultHttpClient httpClient = new DefaultHttpClient();
            String url = "http://113.160.50.84:1009/testing/ica467/trunk/public/home-rest";
            String requestQuery = null;
            requestQuery = String.format("limit=%s&offset=%s&time_from=%s&time_to=%s", "", "", "", "");
            url = url + "?" + requestQuery;
            HttpGet httpGet = new HttpGet(url);
            String authToken = accountManager.peekAuthToken(account, AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS);
            httpGet.addHeader("Authorization", "Bearer " + authToken);
            try
            {
                HttpResponse response = httpClient.execute(httpGet);
                String responseString = EntityUtils.toString(response.getEntity());
                if (responseString.equals(INVALIDTOKEN_MESSAGE))
                {
                    throw new InvalidTokenException();
                }
                JSONObject jsonObject = new JSONObject(responseString);
                if (jsonObject.has("code") && (Integer) jsonObject.get("code") == 200)
                {
                    Type listOfFeedType = new TypeToken<List<FeedDTO>>()
                    {
                    }.getType();
                    ArrayList<FeedDTO> feedDTOs = new Gson().fromJson(jsonObject.get("data").toString(), listOfFeedType);
                    Log.d(TAG, "feeds size: " + feedDTOs.size());
                    return feedDTOs;
                }
                Log.d(TAG, responseString);
            }
            catch (ClientProtocolException e)
            {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            catch (IOException e)
            {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            catch (InvalidTokenException e)
            {
                countLoop++;
                if (countLoop <= 1)
                {
                    applicationAccountManager.refreshToken();
                }
                else
                {
                    retry = false;
                }
            }

        }

        return null;
    }
}
