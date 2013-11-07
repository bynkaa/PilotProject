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
import com.qsoft.pilotproject.handler.CommentHandler;
import com.qsoft.pilotproject.model.dto.CommentDTO;
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
 * Date: 11/7/13
 * Time: 4:54 PM
 */
@EBean
public class CommentHandlerImpl implements CommentHandler
{
    @Bean
    ApplicationAccountManager applicationAccountManager;
    public static final String TAG = "CommentHandlerImpl";

    @Override
    public List<CommentDTO> getListComments(AccountManager accountManager, Account account, Long userId)
    {
        Log.d(TAG, "getListComments()");
        DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = "http://113.160.50.84:1009/testing/ica467/trunk/public/comment-rest";
        String requestQuery = null;
        requestQuery = String.format("sound_id=%s&limit=&offset=&updated_at=", userId);
        url = url + "?" + requestQuery;
        HttpGet httpGet = new HttpGet(url);
        String authToken = accountManager.peekAuthToken(account, AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS);
        httpGet.addHeader("Authorization", "Bearer " + authToken);
        try
        {
            HttpResponse response = httpClient.execute(httpGet);
            String responseString = EntityUtils.toString(response.getEntity());
            if (responseString.equals(AccountGeneral.INVALIDTOKEN_MESSAGE))
            {
                throw new InvalidTokenException();
            }
            JSONObject jsonObject = new JSONObject(responseString);
            if (jsonObject.has("code") && (Integer) jsonObject.get("code") == 200)
            {
                Type listOfCommentType = new TypeToken<List<CommentDTO>>()
                {
                }.getType();
                JSONObject jsonObject1 = new JSONObject(jsonObject.get("data").toString());
                ArrayList<CommentDTO> commentDTOs = new Gson().fromJson(jsonObject1.get("comments").toString(), listOfCommentType);
                Log.d(TAG, "feeds size: " + commentDTOs.size());
                return commentDTOs;
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
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }
}
