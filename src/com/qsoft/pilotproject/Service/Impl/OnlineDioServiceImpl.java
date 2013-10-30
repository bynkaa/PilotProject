package com.qsoft.pilotproject.Service.Impl;

import android.content.Entity;
import android.preference.PreferenceActivity;
import android.util.Log;
import com.google.gson.Gson;
import com.qsoft.pilotproject.Service.OnlineDioService;
import com.qsoft.pilotproject.model.SignInDTO;
import com.qsoft.pilotproject.model.User;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: binhtv
 * Date: 10/28/13
 * Time: 11:32 AM
 */
public class OnlineDioServiceImpl implements OnlineDioService {
    private static final String TAG = "OnlineDioServiceImpl";
    @Override
    public SignInDTO signIn(String userName, String pass, String authTokenType) throws Exception {
        String url = "http://192.168.1.222/testing/ica467/trunk/public/auth-rest";
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("username",userName));
        urlParameters.add(new BasicNameValuePair("password",pass));
        urlParameters.add(new BasicNameValuePair("grant_type","password"));
        urlParameters.add(new BasicNameValuePair("client_id","123456789"));
        urlParameters.add(new BasicNameValuePair("type","password"));
        urlParameters.add(new BasicNameValuePair("email","123456789"));
        httpPost.setEntity(new UrlEncodedFormEntity(urlParameters));
        try {
            HttpResponse response = httpClient.execute(httpPost);
            String responseStr = EntityUtils.toString(response.getEntity());
            if (response.getStatusLine().getStatusCode() != 200)
            {
                throw new Exception("Error signing-in");
            }
            Log.d(TAG,"sign in successful");
            SignInDTO signInDTO = new Gson().fromJson(responseStr.toString(),SignInDTO.class);
            return signInDTO;
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
