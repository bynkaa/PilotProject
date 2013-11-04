package com.qsoft.pilotproject.handler.impl;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qsoft.pilotproject.authenticator.AccountGeneral;
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
public class FeedHandlerImpl implements FeedHandler
{
    private static final String TAG = "FeedHandlerImpl";

    @Override
    public List<FeedDTO> getFeeds(AccountManager accountManager, Account account)
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
//            try
//            {
//                String token = accountManager.blockingGetAuthToken(account,AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS,false);
//                accountManager.setAuthToken(account,AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS,token);
//                getFeeds(accountManager, account);
//            }
//            catch (OperationCanceledException e1)
//            {
//                e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//            }
//            catch (IOException e1)
//            {
//                e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//            }
//            catch (AuthenticatorException e1)
//            {
//                e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//            }
        }

        return null;
    }
}
