package com.qsoft.pilotproject.handler.impl;

import android.util.Log;
import com.google.gson.Gson;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.api.Scope;
import com.qsoft.pilotproject.data.model.dto.SignInDTO;
import com.qsoft.pilotproject.handler.AuthenticatorHandler;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: binhtv
 * Date: 10/28/13
 * Time: 11:32 AM
 */
@EBean(scope = Scope.Singleton)
public class AuthenticatorHandlerImpl implements AuthenticatorHandler
{
    private static final String TAG = "AuthenticatorHandlerImpl";

    @Override
    public SignInDTO signIn(String userName, String pass, String authTokenType) throws Exception
    {
        String url = "http://113.160.50.84:1009/testing/ica467/trunk/public/auth-rest";
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("username", userName));
        urlParameters.add(new BasicNameValuePair("password", pass));
        urlParameters.add(new BasicNameValuePair("grant_type", "password"));
        urlParameters.add(new BasicNameValuePair("client_id", "123456789"));
        urlParameters.add(new BasicNameValuePair("type", "password"));
        urlParameters.add(new BasicNameValuePair("email", "123456789"));
        httpPost.setEntity(new UrlEncodedFormEntity(urlParameters));
        try
        {
            HttpResponse response = httpClient.execute(httpPost);
            String responseStr = EntityUtils.toString(response.getEntity());
            JSONObject jsonObject = new JSONObject(responseStr);
            SignInDTO signInDTO = null;
            if (jsonObject.has("code") && (Integer) jsonObject.get("code") == 400)
            {
                Log.d(TAG, "sign in fail");
            }
            else
            {
                Log.d(TAG, "sign in successful");
                signInDTO = new Gson().fromJson(responseStr.toString(), SignInDTO.class);
            }
            return signInDTO;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
